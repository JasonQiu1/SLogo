package slogo.model.api.exception.turtle;

/**
 * The InvalidPositionException extends RuntimeException to handle any errors related to setting the
 * position of the turtle
 *
 * @author Judy He
 */
public class InvalidPositionException extends RuntimeException {

  /**
   * Initialize a new InvalidPositionException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InvalidPositionException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidPositionException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidPositionException
   */
  public InvalidPositionException(String message, Throwable cause) {
    super(message, cause);
  }
}
