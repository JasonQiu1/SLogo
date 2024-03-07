package slogo.model.coderunner;

import java.util.ArrayList;
import java.util.List;
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

  Interpreter(Environment libraryEnvironment, List<Turtle> turtles) {
    this.globalEnvironment = new Environment(libraryEnvironment);
    this.currentEnvironment = this.globalEnvironment;
    this.turtles = turtles;
    this.selectedTurtles = new ArrayList<>();
    for (Turtle turtle : turtles) {
      selectedTurtles.add(new CodeTurtle(turtle));
    }
  }

  public double interpret(Parser parser) {
    currentParser = parser;
    Expression expression;
    double ret = 0.0;
    while ((expression = parser.parseNext()) != null) {
      currentEnvironment = globalEnvironment;
      ret = evaluate(expression);
    }
    return ret;
  }

  private double interpretBlock(Parser parser) {
    currentParser = parser;
    Expression expression;
    double ret = 0.0;
    while ((expression = parser.parseNext()) != null) {
      ret = evaluate(expression);
    }
    return ret;
  }

  public double evaluate(Expression expression) throws RunCodeError {
    if (expression == null) {
      throw new RunCodeError(ErrorType.RUNTIME, "nullExpression", -1, null);
    }
    return expression.accept(this);
  }

  public void killTurtle(int id) {
    turtles.removeIf(turtle -> turtle.getId() == id);
  }

  @Override
  public double visitNumber(Number expression) {
    return expression.getValue();
  }

  private BiFunction<Double, Double, Double> getBinaryOperator(Token operatorToken) {
    return switch (operatorToken.type()) {
      case PLUS -> Double::sum;
      case MINUS -> (a, b) -> a - b;
      case STAR -> (a, b) -> a * b;
      case FORWARD_SLASH -> (a, b) -> {
        if (b == 0) {
          throw Util.createError("divideByZero", operatorToken);
        }
        return (a / b);
      };
      case PERCENT -> (a, b) -> {
        if (b == 0) {
          throw Util.createError("moduloByZero", operatorToken);
        }
        return (a % b);
      };
      case EQUAL_TO -> (a, b) -> booleanToDouble(a.equals(b));
      case NOT_EQUAL_TO -> (a, b) -> booleanToDouble(!a.equals(b));
      case GREATER_THAN -> (a, b) -> booleanToDouble(a > b);
      case LESS_THAN -> (a, b) -> booleanToDouble(a < b);
      case GREATER_EQUAL_TO -> (a, b) -> booleanToDouble(a >= b);
      case LESS_EQUAL_TO -> (a, b) -> booleanToDouble(a <= b);
      default -> throw Util.createError("invalidBinaryOperator", operatorToken);
    };
  }

  @Override
  public double visitBinary(Binary expression) {
    BiFunction<Double, Double, Double> binaryOperator = getBinaryOperator(expression.getOperator());
    return binaryOperator.apply(evaluate(expression.getLeft()), evaluate(expression.getRight()));
  }

  private Function<Double, Double> getUnaryOperator(Token operatorToken) {
    return switch (operatorToken.type()) {
      case TILDA -> (x) -> (x == 0) ? 0 : -x;
      default -> throw new RunCodeError(ErrorType.PARSE, "invalidBinaryOperator",
          operatorToken.lineNumber(), operatorToken.line());
    };
  }

  @Override
  public double visitUnary(Unary expression) {
    Function<Double, Double> unaryOperator = getUnaryOperator(expression.getOperator());
    return unaryOperator.apply(evaluate(expression.getRight()));
  }

  @Override
  public double visitVariable(Variable expression) {
    return currentEnvironment.lookupVariable(expression.getName());
  }

  @Override
  public double visitCall(Call expression) {
    Command command = currentEnvironment.lookupCommand(expression.getCommandName());

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
    String iteratorName = (String) expression.getIterator().literal();
    double iteratorStart = evaluate(expression.getStart());
    double iteratorEnd = evaluate(expression.getEnd());
    double iteratorIncrement = evaluate(expression.getIncrement());

    Environment previousEnvironment = currentEnvironment;
    currentEnvironment = new Environment(currentEnvironment);
    currentEnvironment.setVariable(iteratorName, iteratorStart);

    double ret = 0;
    while (currentEnvironment.lookupVariable(expression.getIterator()) < iteratorEnd) {
      ret = evaluate(expression.getBody());
      currentEnvironment.setVariable(iteratorName,
          currentEnvironment.lookupVariable(expression.getIterator()) + iteratorIncrement);
    }

    currentEnvironment = previousEnvironment;
    return ret;
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
    Expression next;
    for (Token parameter : expression.getParameters()) {
      parameters.add((String) parameter.literal());
    }
    Command command =
        new UserCommand(parameters, expression.getBody());
    currentEnvironment.defineCommand((String) expression.getCommandName().literal(), command);
    return 1;
  }

  @Override
  public double visitTurtles(Turtles expression) {
    return turtles.size();
  }

  @Override
  public double visitTell(Tell expression) {
    int id = 1;
    for (Expression idExpression : expression.getIds()) {
      id = (int) Math.round(evaluate(idExpression));
      Turtle newTurtle = new Turtle(id);
      turtles.add(newTurtle);
      selectedTurtles.add(new CodeTurtle(newTurtle));
    }
    return id;
  }

  @Override
  public double visitAsk(Ask expression) {
    List<CodeTurtle> previouslySelectedTurtles = selectedTurtles;
    selectedTurtles = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    for (Expression idExpression : expression.getIds()) {
      ids.add((int) Math.round(evaluate(idExpression)));
    }

    for (Turtle turtle : turtles) {
      if (!ids.contains(turtle.getId())) {
        continue;
      }
      selectedTurtles.add(new CodeTurtle(turtle));
    }

    double result = evaluate(expression.getBody());
    selectedTurtles = previouslySelectedTurtles;
    return result;
  }

  @Override
  public double visitAskWith(AskWith expression) {
    List<CodeTurtle> previouslySelectedTurtles = selectedTurtles;
    selectedTurtles = new ArrayList<>();
    for (Turtle turtle : turtles) {
      CodeTurtle candidate = new CodeTurtle(turtle);
      selectedTurtles.add(candidate);
      double predicateResult = evaluate(expression.getPredicate());
      if (predicateResult == 0) {
        selectedTurtles.remove(candidate);
      }
    }

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

  private double booleanToDouble(boolean bool) {
    return bool ? 1 : 0;
  }
}
