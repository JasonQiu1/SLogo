package slogo.model.command.library;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Interpreter;
import slogo.model.command.InterpreterCommand;

public class Back extends InterpreterCommand {

  @Override
  public double apply(CodeTurtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    return turtle.move(-arguments.get(0));
  }

  public Back() {
    super(List.of("pixels"), true);
  }
}
