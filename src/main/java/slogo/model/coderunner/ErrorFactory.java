package slogo.model.coderunner;

import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;

/**
 * Factory for creating RunCodeErrors with tokens.
 *
 * @author Jason Qiu
 */
public class ErrorFactory {

  public static RunCodeError createError(ErrorType errorType, String messageKey, Token token) {
    return new RunCodeError(errorType, messageKey, token.lineNumber(), token.line());
  }
}
