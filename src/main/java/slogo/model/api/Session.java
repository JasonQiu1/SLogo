package slogo.model.api;

import java.util.List;
import java.util.Map;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;

/**
 * External API for the frontend to interact with the model. Responsible for running Slogo code and
 * maintaining command history and turtle step history.
 *
 * @author Jason Qiu, Judy He
 */
public interface Session {

  /**
   * Runs lines of code, affecting command history and step history for each turtle.
   *
   * @param commands the raw code, with each line of code on a different element
   * @return the number of steps created for each turtle
   * @throws RunCodeError when any error occurs when running the code.
   */
  int run(String commands) throws RunCodeError;

  /**
   * Returns the current command history of a certain length.
   *
   * @param maxLength the max length of command history to return. If 0, then return entire command
   *                  history (including forward history). If positive, then return the backwards
   *                  history up to length. If negative, then return the forwards history up to
   *                  -length.
   * @return an immutable list of maps where the key is the command and the value is another map of
   * metadata such as wasSuccessful.
   */
  List<Map<String, Map<String, String>>> getCommandHistory(int maxLength);

  /**
   * Returns the current step history of a certain length for all turtles. May return more steps
   * than maxLength. maxLength limits the number of commands, not number of steps.
   *
   * @param maxLength the max length of step history to return. If 0, then return entire step
   *                  history (including forward history). If positive, then return the backwards
   *                  history up to length.
   * @return an immutable map where the key is the id of the turtle and a list of its step history
   */
  Map<Integer, List<TurtleStep>> getTurtlesStepHistories(int maxLength);

  /**
   * Returns the current state for each turtle.
   *
   * @return an immutable map where the key is the id of the turtle and the value is its state
   */
  Map<Integer, TurtleState> getTurtlesCurrentStates();

  /**
   * Returns all user defined variable names and values.
   *
   * @return an immutable map where the key is the variable name and the value is the integer stored
   * in the variable.
   */
  Map<String, Double> getVariables();

  /**
   * Returns all user defined command names and metadata like parameter names and command
   * definition.
   *
   * @return an immutable map where the key is the command name and the value is the map of
   * metadata, including parameter names and command definition.
   */
  Map<String, Map<String, String>> getUserDefinedCommands();

  /**
   * Not used. Get library commands from XML config instead.
   *
   * @return empty list.
   */

  Map<String, Map<String, String>> getLibraryCommands();

  /**
   * Steps (successful) command history and each turtle's step history back a number of steps.
   *
   * @param maxSteps the maximum number of steps to take. If negative, then will redo instead.
   * @return true if there was enough history to go back numSteps, false otherwise.
   */
  boolean undo(int maxSteps);

  /**
   * Steps (successful) command history and each turtle's step history forward a number of steps.
   *
   * @param maxSteps the maximum number of steps to take. If negative, then will undo instead.
   * @return true if there was enough forwardhistory to go forward numSteps, false otherwise.
   */
  boolean redo(int maxSteps);

  /**
   * Reinitializes the session to no history and only one turtle in the initial position.
   */
  void reset();
}
