package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Expression;
import slogo.model.coderunner.Interpreter;
import slogo.model.coderunner.Token;
import slogo.model.turtleutil.Turtle;

/**
 * A command that only uses Slogo code.
 *
 * @author Jason Qiu
 */
public class UserCommand extends Command {

  @Override
  public double apply(Turtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    return interpreter.evaluate(body);
  }

  public UserCommand(Token name, List<String> parameters, Expression.Block body) {
    super(name, parameters);
    this.body = body;
  }

  private final Expression.Block body;
}
