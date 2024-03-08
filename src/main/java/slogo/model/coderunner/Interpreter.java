package slogo.model.coderunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Expression.Ask;
import slogo.model.coderunner.Expression.AskWith;
import slogo.model.coderunner.Expression.Binary;
import slogo.model.coderunner.Expression.Block;
import slogo.model.coderunner.Expression.Call;
import slogo.model.coderunner.Expression.For;
import slogo.model.coderunner.Expression.IfElse;
import slogo.model.coderunner.Expression.Make;
import slogo.model.coderunner.Expression.Number;
import slogo.model.coderunner.Expression.Tell;
import slogo.model.coderunner.Expression.To;
import slogo.model.coderunner.Expression.Turtles;
import slogo.model.coderunner.Expression.Unary;
import slogo.model.coderunner.Expression.Variable;
import slogo.model.coderunner.Expression.Visitor;
import slogo.model.command.Command;
import slogo.model.command.UserCommand;
import slogo.model.turtleutil.Turtle;

/**
 * Executes abstract syntax trees (statements and expressions).
 *
 * @author Jason Qiu
 */
public class Interpreter implements Visitor {

  /**
   * Adds/removes turtles in-place of the given list, and wraps a new environment around the given
   * one.
   *
   * @param libraryEnvironment the given environment to enclose
   * @param turtles            the list reference to directly modify with new turtles
   */
  Interpreter(Environment libraryEnvironment, List<Turtle> turtles) {
    this.globalEnvironment = new Environment(libraryEnvironment);
    this.currentEnvironment = this.globalEnvironment;
    this.turtles = turtles;
    this.selectedTurtles = new ArrayList<>();
    for (Turtle turtle : turtles) {
      selectedTurtles.add(new CodeTurtle(turtle));
    }
    this.stepsCreated = 0;
  }

  /**
   * Main entry for interpreting a program.
   *
   * @param parser the parser stream with the program loaded
   * @return the number of steps created for each turtle
   */
  public int interpret(Parser parser) {
    currentParser = parser;
    Expression expression;
    stepsCreated = 0;
    while ((expression = parser.parseNext()) != null) {
      currentEnvironment = globalEnvironment;
      evaluate(expression);
    }
    return stepsCreated;
  }

  /**
   * Interprets block expressions.
   *
   * @param parser a parser loaded with the body of the block
   * @return the return value of the last expression in the block, or 0 if no commands run
   */
  private double interpretBlock(Parser parser) {
    currentParser = parser;
    Expression expression;
    double ret = 0.0;
    while ((expression = parser.parseNext()) != null) {
      ret = evaluate(expression);
    }
    return ret;
  }

  /**
   * Evaluates an expression (AST) and returns the final double result
   *
   * @param expression the input expression
   * @return the result of evaluating the expression
   * @throws RunCodeError if the input is null
   */
  public double evaluate(Expression expression) throws RunCodeError {
    if (expression == null) {
      throw new RunCodeError(ErrorType.RUNTIME, "nullExpression", -1, null);
    }
    return expression.accept(this);
  }

  @Override
  public double visitNumber(Number expression) {
    return expression.getValue();
  }

  @Override
  public double visitBinary(Binary expression) {
    BiFunction<Double, Double, Double> binaryOperator = getBinaryOperator(expression.getOperator());
    return binaryOperator.apply(evaluate(expression.getLeft()), evaluate(expression.getRight()));
  }

  @Override
  public double visitUnary(Unary expression) {
    Function<Double, Double> unaryOperator = getUnaryOperator(expression.getOperator());
    return unaryOperator.apply(evaluate(expression.getRight()));
  }

  @Override
  public double visitVariable(Variable expression) {
    try {
      return currentEnvironment.lookupVariable(expression.getName());
    } catch (RunCodeError error) {
      return 0.0;
    }
  }

  @Override
  public double visitCall(Call expression) {
    Command command = currentEnvironment.lookupCommand(expression.getCommandName());
    if (command.getCreatesTurtleStep()) {
      stepsCreated += 1;
    }

    int arity = command.getArity();
    List<Double> arguments = new ArrayList<>();
    for (int i = 0; i < arity; i++) {
      arguments.add(evaluate(currentParser.parseNext()));
    }

    List<String> parameters = command.getParameters();
    Environment previousEnvironment = currentEnvironment;
    currentEnvironment = new Environment(currentEnvironment);
    for (int i = 0; i < arity; i++) {
      currentEnvironment.setVariable(parameters.get(i), arguments.get(i));
    }

    double result = command.applyAll(selectedTurtles, this, arguments);
    currentEnvironment = previousEnvironment;
    return result;
  }

  @Override
  public double visitMake(Make expression) {
    double value = evaluate(expression.getValue());
    currentEnvironment.setVariable((String) expression.getVariable().literal(), value);
    return value;
  }

  @Override
  public double visitFor(For expression) {
    double iteratorStart = evaluate(expression.getStart());
    double iteratorEnd = evaluate(expression.getEnd());
    double iteratorIncrement = evaluate(expression.getIncrement());

    return runForLoop(expression.getIterator(), iteratorStart, iteratorEnd, iteratorIncrement,
        expression.getBody());
  }

  @Override
  public double visitIfElse(IfElse expression) {
    double predicate = evaluate(expression.getPredicate());
    if (predicate != 0) {
      return evaluate(expression.getTrueBranch());
    }
    if (expression.getFalseBranch() != null) {
      return evaluate(expression.getFalseBranch());
    }
    return 0;
  }

  @Override
  public double visitTo(To expression) {
    List<String> parameters = new ArrayList<>();
    for (Token parameter : expression.getParameters()) {
      parameters.add((String) parameter.literal());
    }
    Command command = new UserCommand(parameters, expression.getBody());
    currentEnvironment.defineCommand((String) expression.getCommandName().literal(), command);
    return 1;
  }

  @Override
  public double visitTurtles(Turtles expression) {
    return turtles.size();
  }

  @Override
  public double visitTell(Tell expression) {
    return selectAndCreateTurtles(expression.getIds());
  }

  @Override
  public double visitAsk(Ask expression) {
    List<CodeTurtle> previouslySelectedTurtles = selectedTurtles;
    selectedTurtles = new ArrayList<>();
    selectAndCreateTurtles(expression.getIds());

    double result = evaluate(expression.getBody());
    selectedTurtles = previouslySelectedTurtles;
    return result;
  }

  @Override
  public double visitAskWith(AskWith expression) {
    List<CodeTurtle> previouslySelectedTurtles = selectedTurtles;
    selectTurtlesIf(expression.getPredicate());

    double result = evaluate(expression.getBody());
    selectedTurtles = previouslySelectedTurtles;
    return result;
  }

  @Override
  public double visitBlock(Block block) {
    Parser previousParser = currentParser;
    Environment previousEnvironment = currentEnvironment;
    currentEnvironment = new Environment(currentEnvironment);
    double ret = interpretBlock(new ListParser(block.getBody()));
    currentParser = previousParser;
    currentEnvironment = previousEnvironment;
    return ret;
  }

  Environment getGlobalEnvironment() {
    return globalEnvironment;
  }

  private final Environment globalEnvironment;
  private Environment currentEnvironment;
  private final List<Turtle> turtles;
  private List<CodeTurtle> selectedTurtles;
  private Parser currentParser;
  private int stepsCreated;

  private double booleanToDouble(boolean bool) {
    return bool ? 1 : 0;
  }

  private Optional<Turtle> getTurtle(int id) {
    for (Turtle turtle : turtles) {
      if (turtle.getId() == id) {
        return Optional.of(turtle);
      }
    }
    return Optional.empty();
  }

  private Function<Double, Double> getUnaryOperator(Token operatorToken) {
    return switch (operatorToken.type()) {
      case TILDA -> (x) -> (x == 0) ? 0 : x * -1;
      default -> throw new RunCodeError(ErrorType.PARSE, "invalidBinaryOperator",
          operatorToken.lineNumber(), operatorToken.line());
    };
  }

  private BiFunction<Double, Double, Double> getBinaryOperator(Token operatorToken) {
    return switch (operatorToken.type()) {
      case PLUS -> Double::sum;
      case MINUS -> (a, b) -> a - b;
      case STAR -> (a, b) -> a * b;
      case FORWARD_SLASH -> (a, b) -> {
        if (b == 0) {
          throw ErrorFactory.createError(ErrorType.INTERPRET, "divideByZero", operatorToken);
        }
        return (a / b);
      };
      case PERCENT -> (a, b) -> {
        if (b == 0) {
          throw ErrorFactory.createError(ErrorType.INTERPRET, "moduloByZero", operatorToken);
        }
        return (a % b);
      };
      case EQUAL_TO -> (a, b) -> booleanToDouble(a.equals(b));
      case NOT_EQUAL_TO -> (a, b) -> booleanToDouble(!a.equals(b));
      case GREATER_THAN -> (a, b) -> booleanToDouble(a > b);
      case LESS_THAN -> (a, b) -> booleanToDouble(a < b);
      case GREATER_EQUAL_TO -> (a, b) -> booleanToDouble(a >= b);
      case LESS_EQUAL_TO -> (a, b) -> booleanToDouble(a <= b);
      default -> throw ErrorFactory.createError(ErrorType.INTERPRET, "invalidBinaryOperator",
          operatorToken);
    };
  }

  private double runForLoop(Token iteratorToken, double iteratorStart, double iteratorEnd,
      double iteratorIncrement, Expression body) {
    String iteratorName = (String) iteratorToken.literal();

    Environment previousEnvironment = currentEnvironment;
    currentEnvironment = new Environment(currentEnvironment);
    currentEnvironment.setVariable(iteratorName, iteratorStart);

    double ret = 0;
    while (currentEnvironment.lookupVariable(iteratorToken) < iteratorEnd) {
      ret = evaluate(body);
      currentEnvironment.setVariable(iteratorName,
          currentEnvironment.lookupVariable(iteratorToken) + iteratorIncrement);
    }

    currentEnvironment = previousEnvironment;
    return ret;
  }

  private double selectAndCreateTurtles(List<Expression> idExpressions) {
    int id = 1;
    selectedTurtles.clear();
    for (Expression idExpression : idExpressions) {
      id = (int) Math.round(evaluate(idExpression));
      int finalId = id;
      Turtle turtle = getTurtle(id).orElseGet(() -> {
        Turtle newTurtle = new Turtle(finalId);
        turtles.add(newTurtle);
        return newTurtle;
      });
      selectedTurtles.add(new CodeTurtle(turtle));
    }
    return id;
  }

  private void selectTurtlesIf(Expression predicate) {
    List<CodeTurtle> turtlesToSelect = new ArrayList<>();
    selectedTurtles = new ArrayList<>();
    for (Turtle turtle : turtles) {
      CodeTurtle candidate = new CodeTurtle(turtle);
      selectedTurtles.clear();
      selectedTurtles.add(candidate);
      double predicateResult = evaluate(predicate);
      if (predicateResult != 0) {
        turtlesToSelect.add(candidate);
      }
    }
    selectedTurtles = turtlesToSelect;
  }
}
