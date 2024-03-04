package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Expression;
import slogo.model.coderunner.Interpreter;

/**
 * A command that only uses Slogo code.
 *
 * @author Jason Qiu
 */
public class UserCommand extends Command {

  @Override
  public double apply(CodeTurtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    return interpreter.evaluate(body);
  }

  public UserCommand(List<String> parameters, Expression.Block body) {
    super(parameters);
    this.body = body;
  }

  private final Expression.Block body;
}
