package slogo.model.coderunner;

import java.util.Map;

/**
 * Any object that runs code and modifies turtles.
 *
 * @author Jason Qiu
 */
public interface CodeRunner {

  /**
   * Executes a string of commands.
   *
   * @param commands the string of commands to execute.
   */
  int run(String commands);

  /**
   * Gets all variables defined globally.
   * @return the variables defined globally in name-value pairs
   */
  Map<String, Double> getVariables();

  /**
   * Gets all user commands defined globally
   * @return the globally defined commands in name-metadata pairs,
   * where the metadata has the arity, parameter names, and source code body of the command
   */
  Map<String, Map<String, String>> getCommands();
}
