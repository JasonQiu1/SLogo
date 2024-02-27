package slogo.model.api.exception;

/**
 * The UnsupportedTurtleCommandException extends RuntimeException to handle any errors related to
 * unsupported command related to turtle motion
 *
 * @author Judy He
 */
public class UnsupportedTurtleCommandException extends RuntimeException {
  /**
   * Initialize a new UnsupportedTurtleCommandException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public UnsupportedTurtleCommandException(String message) {
    super(message);
  }

  /**
   * Initialize a new UnsupportedTurtleCommandException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the UnsupportedTurtleCommandException
   */
  public UnsupportedTurtleCommandException(String message, Throwable cause) {
    super(message, cause);
  }
}
