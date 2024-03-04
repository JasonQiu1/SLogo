package slogo.model.command;

import java.util.List;
import java.util.function.Function;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Interpreter;

/**
 * A command that is evaluated using only java code.
 *
 * @author Jason Qiu
 */
public abstract class JavaCommand extends Command {

  @Override
  public double apply(CodeTurtle turtle, Interpreter interpreter, List<Double> arguments) {
    return function.apply(arguments);
  }

  protected JavaCommand(List<String> parameters, Function<List<Double>, Double> function) {
    super(parameters);
    this.function = function;
  }

  private final Function<List<Double>, Double> function;
}
