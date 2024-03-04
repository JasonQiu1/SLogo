package slogo.model.command;

import java.util.List;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Interpreter;
import slogo.model.coderunner.Token;
import slogo.model.turtleutil.Turtle;

public class Right extends InterpreterCommand {

  @Override
  public double apply(Turtle turtle, Interpreter interpreter, List<Double> arguments)
      throws RunCodeError {
    // TODO: return degrees rotated
    turtle.rotate(arguments.get(0));
    return 0;
  }

  public Right(Token name) {
    super(name, List.of("pixels"));
  }
}