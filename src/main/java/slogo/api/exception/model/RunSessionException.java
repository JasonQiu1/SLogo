package slogo.exception.model;

public class RunSessionException extends RuntimeException{
  /**
   * Initialize a new RunSessionException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public RunSessionException(String message) {
    super(message);
  }

  /**
   * Initialize a new RunSessionException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the RunSessionException
   */
  public RunSessionException(String message, Throwable cause) {
    super(message, cause);
  }

}
