# Model
## RunProgramException
```Java 
/**
 * Returns the error message as a resource bundle key.
 * 
 * @return the resource bundle key corresponding to the error message.
 */
public String getErrorMessage();
```
```Java 
/**
 * Returns the error type as a resource bundle key.
 *
 * @return the resource bundle key corresponding to the error type.
 */
public String getErrorType();
```
```Java 
/**
 * Returns the line number of the original code where the error occurred.
 * 
 * @return the line number of the original code where the error occurred.
 */
public int getLineNumber();
```
```Java 
/**
 * Returns the number of characters from the start of the line where the error occurred.
 * 
 * @return the number of characters from the start of the line where the error occurred.
 */
public String getLinePosition();
```

```Java 
/**
 * Returns the original line of code where the error occurred.
 * 
 * @return the original line of code where the error occurred.
 */
public String getLine();
```

## Session
```Java 
/**
 * Runs lines of code, affecting command history and step history for each turtle.
 * 
 * @param linesOfCommands the raw code, with each line of code on a different element
 */
public void run(String[] linesOfCommands) throws RunProgramException;
```
```Java 
/**
 * Returns the current command history of a certain length.
 * If length=-1, then returns entire command history.
 * 
 * @param length the max length of command history to return. 
 *               If -1, then return entire command history.
 * @return an immutable map where the key is the command and 
 *               the value is another map of metadata such as wasSuccessful.
 */
public ImmutableMap<String, ImmutableMap<String, String>> getCommandHistory(int length);
```
```Java 
/**
 * Returns the current step history for all turtles.
 * If length=-1, then returns entire command history.
 * 
 * @param length the max length of each turtle's step history to return. 
 *               If -1, then return entire step history of each turtle.
 * @return an immutable map where the key is the id of the turtle and 
 *               a list of its step history

 */
public ImmutableMap<String, List<TurtleStep>> getTurtlesStepHistory(int length);
```

```Java 
/**
 * Returns all user defined variable names and values.
 * 
 * @return an immutable map where the key is the variable name and 
 *               the value is the integer stored in the variable.
 */
public ImmutableMap<String, Integer> getUserDefinedVariables();
```
```Java 
/**
 * Returns all user defined command names and metadata like parameter names and command definition.
 *
 * @return an immutable map where the key is the command name and 
 *               the value is the map of metadata, including parameter names and command definition.
 */
public ImmutableMap<String, ImmutableMap<String, String>> getUserDefinedCommands();
```
```Java 
/**
 * Returns all library command names, and parameter names.
 *
 * @return an immutable map where the key is the command name and 
 *               the value is the map of metadata, including arity and command definition.
 */

public ImmutableMap getLibraryCommands();
```
```Java 
/**
 * Steps command history and each turtle's step history back a number of steps. 
 * 
 * @param numSteps how many steps to go back. If -1, then go back to initial state.
 * @return true if there was enough history to go back numSteps, false otherwise.
 */
public boolean undo(int numSteps);
```
```Java 
/**
 * Steps command history and each turtle's step history forward a number of steps. 
 *
 * @param numSteps how many steps to go forward. If -1, then go all the way to furthest state.
 * @return true if there was enough forward history to go forward numSteps, false otherwise.
 */
public boolean redo(int numSteps);
```