package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Interpreter;

/**
 * A command that only sends messages to the interpreter.
 *
 * @author Jason Qiu
 */
public abstract class InterpreterCommand extends Command {

  @Override
  public abstract double apply(CodeTurtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError;

  protected InterpreterCommand(List<String> parameters, boolean createsTurtleStep) {
    super(parameters, createsTurtleStep);
  }

  protected InterpreterCommand(List<String> parameters) {
    super(parameters);
  }
}
