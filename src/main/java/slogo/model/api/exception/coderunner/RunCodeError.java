package slogo.model.api.exception.coderunner;

/**
 * Represents any error that could be thrown when running Slogo code and includes debugging info.
 *
 * @author Jason Qiu
 */
public class RunCodeError extends RuntimeException {

  private final ErrorType _errorType;
  private final int _lineNumber;
  private final String _line;

  RunCodeError(ErrorType errorType, String errorMessageKey, int lineNumber, String line) {
    super(errorMessageKey);
    _errorType = errorType;
    _lineNumber = lineNumber;
    _line = line;
  }

  /**
   * Returns the string of the error type in lowercase.
   *
   * @return the string of the error type.
   */
  public String getErrorType() {
    return _errorType.toString().toLowerCase();
  }

  /**
   * Returns the key in the properties file corresponding to the error message.
   *
   * @return the key in the properties file corresponding to the error message.
   */
  public String getErrorMessageKey() {
    return getMessage();
  }

  /**
   * Returns the line number where the error occurred.
   *
   * @return the line number where the error occurred.
   */
  public int getLineNumber() {
    return _lineNumber;
  }

  /**
   * Returns the line where the error occurred.
   *
   * @return the line where the error occurred.
   */
  public String getLine() {
    return _line;
  }
  enum ErrorType {
    TOKENIZE, PARSE, INTERPRET, RUNTIME
  }
}