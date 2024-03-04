package slogo.model.command;

import java.util.List;
import java.util.function.Function;
import slogo.model.coderunner.Interpreter;
import slogo.model.coderunner.Token;
import slogo.model.turtleutil.Turtle;

/**
 * A command that is evaluated using only java code.
 *
 * @author Jason Qiu
 */
public abstract class JavaCommand extends Command {

  @Override
  public double apply(Turtle turtle, Interpreter interpreter, List<Double> arguments) {
    return function.apply(arguments);
  }

  protected JavaCommand(Token name, List<String> parameters,
      Function<List<Double>, Double> function) {
    super(name, parameters);
    this.function = function;
  }

  private final Function<List<Double>, Double> function;
}
