package slogo.model.coderunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Expression.Binary;
import slogo.model.coderunner.Expression.Block;
import slogo.model.coderunner.Expression.Call;
import slogo.model.coderunner.Expression.DefineCommand;
import slogo.model.coderunner.Expression.Number;
import slogo.model.coderunner.Expression.SetVariable;
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

  Interpreter(Environment globalEnvironment, List<Turtle> turtles) {
    this.globalEnvironment = globalEnvironment;
    this.currentEnvironment = this.globalEnvironment;
    this.turtles = turtles;
    this.selectedTurtles = new ArrayList<Turtle>();
    selectedTurtles.addAll(turtles);
  }

  public void interpret(Parser parser) {
    currentParser = parser;
    Expression expression;
    while ((expression = parser.parseNext()) != null) {
      evaluate(expression);
    }
  }

  public double evaluate(Expression expression) throws RunCodeError {
    if (expression == null) {
      throw new RunCodeError(ErrorType.RUNTIME, "nullExpression", -1, null);
    }
    return expression.accept(this);
  }

  public void spawnTurtle() {
    turtles.add(new Turtle());
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
    List<Double> arguments = new ArrayList<Double>();
    for (int i = 0; i < arity; i++) {
      arguments.add(evaluate(currentParser.parseNext()));
    }

    List<String> parameters = command.getParameters();
    currentEnvironment = new Environment(currentEnvironment);
    for (int i = 0; i < arity; i++) {
      currentEnvironment.setVariable(parameters.get(i), arguments.get(i));
    }

    return command.applyAll(selectedTurtles, this, arguments);
  }

  @Override
  public double visitSetVariable(SetVariable expression) {
    double value = evaluate(expression.getValue());
    currentEnvironment.setVariable((String) expression.getName().literal(), value);
    return value;
  }

  @Override
  public double visitDefineCommand(DefineCommand expression) {
    List<String> parameters = new ArrayList<String>();
    Expression next = null;
    while ((next = currentParser.parseNext()) instanceof Variable) {
      Variable variable = (Variable) next;
      parameters.add((String) variable.getName().literal());
    }
    if (!(next instanceof Block)) {
      throw Util.createError("expectedCommandBody", expression.getName());
    }
    Command command = new UserCommand(expression.getName(), parameters, (Block) next);
    currentEnvironment.defineCommand((String) expression.getName().literal(), command);
    return 1;
  }

  @Override
  public double visitBlock(Block block) {
    double ret = Double.NEGATIVE_INFINITY;
    for (Expression expression : block.getBody()) {
      ret = evaluate(expression);
    }
    return ret;
  }

  private final Environment globalEnvironment;
  private Environment currentEnvironment;
  private final List<Turtle> turtles;
  private final List<Turtle> selectedTurtles;
  private Parser currentParser;

  private double booleanToDouble(boolean bool) {
    return bool ? 1 : 0;
  }
}
