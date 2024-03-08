package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Interpreter;

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

  public abstract double apply(CodeTurtle target, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError;

  public double applyAll(List<CodeTurtle> targets, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    double ret = 0;
    for (CodeTurtle target : targets) {
      ret = apply(target, interpreter, arguments);
    }
    return ret;
  }

  public boolean getCreatesTurtleStep() {
    return createsTurtleStep;
  }

  private final boolean createsTurtleStep;

  protected Command(List<String> parameters, boolean createsTurtleStep) {
    this.parameters = parameters;
    this.createsTurtleStep = createsTurtleStep;
  }

  protected Command(List<String> parameters) {
    this(parameters, false);
  }

  private final List<String> parameters;
}
