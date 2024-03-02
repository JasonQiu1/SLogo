package slogo.model.coderunner;

import java.util.function.BiFunction;
import java.util.function.Function;
import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Expression.Binary;
import slogo.model.coderunner.Expression.Call;
import slogo.model.coderunner.Expression.DefineCommand;
import slogo.model.coderunner.Expression.Number;
import slogo.model.coderunner.Expression.SetVariable;
import slogo.model.coderunner.Expression.Unary;
import slogo.model.coderunner.Expression.Variable;
import slogo.model.coderunner.Expression.Visitor;

/**
 * Executes abstract syntax trees (statements and expressions).
 *
 * @author Jason Qiu
 */
class Interpreter implements Visitor {

  public double evaluate(Expression expression) {
    return expression.accept(this);
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
          throw createError("divideByZero", operatorToken);
        }
        return (a / b);
      };
      case PERCENT -> (a, b) -> {
        if (b == 0) {
          throw createError("moduloByZero", operatorToken);
        }
        return (a % b);
      };
      case EQUAL_TO -> (a, b) -> booleanToDouble(a.equals(b));
      case NOT_EQUAL_TO -> (a, b) -> booleanToDouble(!a.equals(b));
      case GREATER_THAN -> (a, b) -> booleanToDouble(a > b);
      case LESS_THAN -> (a, b) -> booleanToDouble(a < b);
      case GREATER_EQUAL_TO -> (a, b) -> booleanToDouble(a >= b);
      case LESS_EQUAL_TO -> (a, b) -> booleanToDouble(a <= b);
      default -> throw createError("invalidBinaryOperator", operatorToken);
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
    return 0;
  }

  @Override
  public double visitSetVariable(SetVariable expression) {
    return 0;
  }

  @Override
  public double visitDefineCommand(DefineCommand expression) {
    return 0;
  }

  @Override
  public double visitCall(Call expression) {
    return 0;
  }


  private Environment _globalEnvironment;
  private Environment _currentEnvironment;

  private double booleanToDouble(boolean bool) {
    return bool ? 1 : 0;
  }

  private RunCodeError createError(String messageKey, Token token) {
    return new RunCodeError(ErrorType.INTERPRET, messageKey, token.lineNumber(), token.line());
  }
}
