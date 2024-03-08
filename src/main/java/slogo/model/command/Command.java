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


  public boolean getCreatesTurtleStep() {
    return createsTurtleStep;
  }

  /**
   * Runs a command with the given arguments, which may modify target or use the interpreter to
   * evaluate expressions
   *
   * @param target the target turtle
   * @param interpreter the target interpreter environment
   * @param arguments the arguments to the command
   * @return the result of the command
   * @throws RunCodeError if there were any runtime errors
   */
  public abstract double apply(CodeTurtle target, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError;

  /**
   * Runs a command with the given arguments, which may modify each target or use the interpreter to
   * evaluate expressions, or a list of turtles
   *
   * @param targets the list of target turtles
   * @param interpreter the target interpreter environment
   * @param arguments the arguments to the command
   * @return the result of the command
   * @throws RunCodeError if there were any runtime errors
   */
  public double applyAll(List<CodeTurtle> targets, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    double ret = 0;
    for (CodeTurtle target : targets) {
      ret = apply(target, interpreter, arguments);
    }
    return ret;
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
