package slogo.model.command.library;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.CodeTurtle;
import slogo.model.coderunner.Interpreter;
import slogo.model.command.InterpreterCommand;

public class Home extends InterpreterCommand {

  @Override
  public double apply(CodeTurtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    return turtle.setPosition(0, 0);
  }

  public Home() {
    super(List.of(), true);
  }
}