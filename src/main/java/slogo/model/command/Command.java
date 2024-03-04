package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Interpreter;
import slogo.model.coderunner.Token;
import slogo.model.turtleutil.Turtle;

/**
 * An abstract command.
 *
 * @author Jason Qiu
 */
public abstract class Command {

  public List<String> getParameters() {
    return parameters;
  }

  public int getArity() {
    return parameters.size();
  }

  public abstract double apply(Turtle target, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError;

  public double applyAll(List<Turtle> targets, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    double ret = 0;
    for (Turtle target : targets) {
      ret = apply(target, interpreter, arguments);
    }
    return ret;
  }

  protected Command(Token name, List<String> parameters) {
    this.name = name;
    this.parameters = parameters;
  }

  private final Token name;
  private final List<String> parameters;
}
