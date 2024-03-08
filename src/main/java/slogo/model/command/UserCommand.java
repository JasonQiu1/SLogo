package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Expression;
import slogo.model.coderunner.Expression.Block;
import slogo.model.coderunner.Interpreter;

/**
 * A command that uses Slogo code (expressions).
 *
 * @author Jason Qiu
 */
public class UserCommand extends Command {

  @Override
  public double apply(CodeTurtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    return interpreter.evaluate(body);
  }

  public String getBodySource() {
    return body.getBodySource();
  }

  public UserCommand(List<String> parameters, Block body) {
    super(parameters);
    this.body = body;
  }

  private final Block body;
}
