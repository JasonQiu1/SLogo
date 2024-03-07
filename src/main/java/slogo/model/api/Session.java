package slogo.model.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.coderunner.SlogoCodeRunner;
import slogo.model.turtleutil.Turtle;

/**
 * External API for the frontend to interact with the model. Responsible for running Slogo code and
 * maintaining command history and turtle step history.
 *
 * @author Jason Qiu
 */
public class Session {

  public Session() {
    commandHistory = new ArrayList<>();
    turtles = new ArrayList<>();
    reset();
    codeRunner = new SlogoCodeRunner(turtles);
  }

  /**
   * Runs lines of code, affecting command history and step history for each turtle.
   *
   * @param commands the raw code, with each line of code on a different element
   * @throws RunCodeError when any error occurs when running the code.
   */
  public void run(String commands) throws RunCodeError {
    try {
      codeRunner.run(commands);
      commandHistory.add(Map.of(commands, Map.of("successful", "true")));
    } catch (RunCodeError error) {
      commandHistory.add(Map.of(commands, Map.of("successful", "false")));
    }
  }

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
  public List<Map<String, Map<String, String>>> getCommandHistory(int maxLength) {
    return commandHistory;
  }

  /**
   * Returns the current step history of a certain length for all turtles. May return more steps
   * than maxLength. maxLength limits the number of commands, not number of steps.
   *
   * @param maxLength the max length of step history to return. If 0, then return entire step
   *                  history (including forward history). If positive, then return the backwards
   *                  history up to length.
   * @return an immutable map where the key is the id of the turtle and a list of its step history
   */
  public Map<Integer, List<TurtleStep>> getTurtlesStepHistories(int maxLength) {
    Map<Integer, List<TurtleStep>> turtlesStepHistories = new HashMap<>();
    for (Turtle turtle : turtles) {
      turtlesStepHistories.put(turtle.getId(), turtle.getStepHistory(maxLength));
    }
    return turtlesStepHistories;
  }

  /**
   * Returns the current state for each turtle.
   *
   * @return an immutable map where the key is the id of the turtle and the value is its state
   */
  public Map<Integer, TurtleState> getTurtlesCurrentStates() {
    Map<Integer, TurtleState> turtleStates = new HashMap<>();
    for (Turtle turtle : turtles) {
      turtleStates.put(turtle.getId(), turtle.getCurrentState());
    }
    return turtleStates;
  }

  /**
   * Returns all user defined variable names and values.
   *
   * @return an immutable map where the key is the variable name and the value is the integer stored
   * in the variable.
   */
  public Map<String, Double> getVariables() {
    return codeRunner.getVariables();
  }

  /**
   * Returns all user defined command names and metadata like parameter names and command
   * definition.
   *
   * @return an immutable map where the key is the command name and the value is the map of
   * metadata, including parameter names and command definition.
   */
  public Map<String, Map<String, String>> getUserDefinedCommands() {
    return codeRunner.getCommands();
  }

  /**
   * Returns all library command names, and parameter names.
   *
   * @return an immutable map where the key is the command name and the value is the map of
   * metadata, including arity and command definition.
   */

  public Map<String, Map<String, String>> getLibraryCommands() {
    return Map.of();
  }

  /**
   * Steps (successful) command history and each turtle's step history back a number of steps.
   *
   * @param maxSteps the maximum number of steps to take. If negative, then will redo instead.
   * @return true if there was enough history to go back numSteps, false otherwise.
   */
  public boolean undo(int maxSteps) {
    return false;
  }

  /**
   * Steps (successful) command history and each turtle's step history forward a number of steps.
   *
   * @param maxSteps the maximum number of steps to take. If negative, then will undo instead.
   * @return true if there was enough forwardhistory to go forward numSteps, false otherwise.
   */
  public boolean redo(int maxSteps) {
    return false;
  }

  /**
   * Reinitializes the session to no history and only one turtle in the initial position.
   */
  public void reset() {
    commandHistory.clear();
    turtles.clear();
    turtles.add(new Turtle(1));
  }

  private final List<Map<String, Map<String, String>>> commandHistory;
  private final SlogoCodeRunner codeRunner;
  private final List<Turtle> turtles;
}
