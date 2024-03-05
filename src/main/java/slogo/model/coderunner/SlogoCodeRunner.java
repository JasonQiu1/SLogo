package slogo.model.coderunner;

import java.util.List;
import slogo.model.command.library.*;
import slogo.model.command.library.YCor;
import slogo.model.turtleutil.Turtle;

/**
 * Runs Slogo code, possibly modifying turtles.
 *
 * @author Jason Qiu
 */
public class SlogoCodeRunner {

  /**
   * Must pass in reference to the original list of turtles, which may be modified in-place.
   *
   * @param turtles reference to the original list of turtles, which may be modified in-place.
   */
  public SlogoCodeRunner(List<Turtle> turtles) {
    interpreter = new Interpreter(new LibraryEnvironment(), turtles);
  }

  /**
   * Executes a string of commands.
   *
   * @param commands the string of commands to execute.
   */
  public void run(String commands) {
    interpreter.interpret(new ParserStream(new Lexer(commands)));
  }

  private final Interpreter interpreter;
}