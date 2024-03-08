package slogo.model.coderunner;

import java.util.List;
import java.util.Map;
import slogo.model.turtleutil.Turtle;

/**
 * Runs Slogo code, possibly modifying turtles.
 *
 * @author Jason Qiu
 */
public class SlogoCodeRunner implements CodeRunner {

  /**
   * Must pass in reference to the original list of turtles, which may be modified in-place.
   *
   * @param turtles reference to the original list of turtles, which may be modified in-place.
   */
  public SlogoCodeRunner(List<Turtle> turtles) {
    LibraryEnvironment libraryEnvironment = new LibraryEnvironment();
    interpreter = new Interpreter(libraryEnvironment, turtles);
  }

  /**
   * Executes a string of commands.
   *
   * @param commands the string of commands to execute.
   */
  @Override
  public int run(String commands) {
    return interpreter.interpret(new ParserStream(new Lexer(commands)));
  }

  @Override
  public Map<String, Double> getVariables() {
    return interpreter.getGlobalEnvironment().getLocalVariables();
  }

  @Override
  public Map<String, Map<String, String>> getCommands() {
    return interpreter.getGlobalEnvironment().getLocalCommands();
  }

  private final Interpreter interpreter;
}