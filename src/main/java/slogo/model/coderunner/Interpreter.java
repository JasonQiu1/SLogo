package slogo.model.coderunner;

import slogo.model.coderunner.Expression.Binary;
import slogo.model.coderunner.Expression.Call;
import slogo.model.coderunner.Expression.DefineCommand;
import slogo.model.coderunner.Expression.Logical;
import slogo.model.coderunner.Expression.Number;
import slogo.model.coderunner.Expression.SetVariable;
import slogo.model.coderunner.Expression.Unary;
import slogo.model.coderunner.Expression.Variable;

/**
 * Executes abstract syntax trees (statements and expressions).
 *
 * @author Jason Qiu
 */
class Interpreter implements Expression.Visitor {

  private Environment _globalEnvironment;
  private Environment _currentEnvironment;

  void evaluate(Expression expression) {
    expression.accept(this);
  }

  @Override
  public double visitNumber(Number expression) {
    return 0;
  }

  @Override
  public double visitBinary(Binary expression) {
    return 0;
  }

  @Override
  public double visitLogical(Logical expression) {
    return 0;
  }

  @Override
  public double visitUnary(Unary expression) {
    return 0;
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
}
