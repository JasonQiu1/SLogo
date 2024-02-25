package slogo.model;

import java.util.*;
import slogo.exception.RunSessionException;
import slogo.model.turtle.TurtleStep;

public class Session {

  /**
   * Runs lines of code, affecting command history and step history for each turtle.
   *
   * @param linesOfCommands the raw code, with each line of code on a different element
   * @throws RunSessionException when XXXXXX
   */
  public void run(String[] linesOfCommands) throws RunSessionException {
  }

  /**
   * Returns the current command history of a certain length. If length=-1, then returns entire
   * command history.
   *
   * @param length the max length of command history to return. If -1, then return entire command
   *               history.
   * @return an immutable map where the key is the command and the value is another map of metadata
   * such as wasSuccessful.
   */
  public Map<String, Map<String, String>> getCommandHistory(int length) {

    return null;
  }

  /**
   * Returns the current step history for all turtles. If length=-1, then returns entire command
   * history.
   *
   * @param length the max length of each turtle's step history to return. If -1, then return entire
   *               step history of each turtle.
   * @return an immutable map where the key is the id of the turtle and a list of its step history
   */
  public Map<String, List<TurtleStep>> getTurtlesStepHistory(int length) {
    return null;
  }

  /**
   * Returns all user defined variable names and values.
   *
   * @return an immutable map where the key is the variable name and the value is the integer stored
   * in the variable.
   */
  public Map<String, Integer> getUserDefinedVariables() {
    return null;
  }

  /**
   * Returns all user defined command names and metadata like parameter names and command
   * definition.
   *
   * @return an immutable map where the key is the command name and the value is the map of
   * metadata, including parameter names and command definition.
   */
  public Map<String, Map<String, String>> getUserDefinedCommands() {
    return null;
  }

  /**
   * Returns all library command names, and parameter names.
   *
   * @return an immutable map where the key is the command name and the value is the map of
   * metadata, including arity and command definition.
   */

  public Map<String, String> getLibraryCommands() {
    return null;
  }

  /**
   * Steps command history and each turtle's step history back a number of steps.
   *
   * @param numSteps how many steps to go back. If -1, then go back to initial state.
   * @return true if there was enough history to go back numSteps, false otherwise.
   */
  public boolean undo(int numSteps) {
    return false;
  }

  /**
   * Steps command history and each turtle's step history forward a number of steps.
   *
   * @param numSteps how many steps to go forward. If -1, then go all the way to furthest state.
   * @return true if there was enough forward history to go forward numSteps, false otherwise.
   */
  public boolean redo(int numSteps) {
    return false;
  }


}
