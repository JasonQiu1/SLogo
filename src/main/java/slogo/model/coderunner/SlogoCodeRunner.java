package slogo.model.coderunner;

import java.util.List;
import slogo.model.command.Forward;
import slogo.model.command.Right;
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
    interpreter = new Interpreter(loadLibraryEnvironment(), turtles);
  }

  /**
   * Executes a string of commands.
   *
   * @param commands the string of commands to execute.
   */
  public void run(String commands) {
    interpreter.interpret(new Parser(new Lexer(commands)));
  }

  private final Interpreter interpreter;

  private Environment loadLibraryEnvironment() {
    Environment libraryEnvironment = new Environment(null);

    libraryEnvironment.defineCommand("forward", new Forward());
    libraryEnvironment.defineCommand("fd", new Forward());
    libraryEnvironment.defineCommand("right", new Right());
    libraryEnvironment.defineCommand("rt", new Right());

    return libraryEnvironment;
  }
}