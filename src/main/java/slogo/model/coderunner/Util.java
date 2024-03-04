package slogo.model.coderunner;

import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;

/**
 * Utility methods for throwing RunCodeErrors with tokens.
 *
 * @author Jason Qiu
 */
public class Util {

  public static RunCodeError createError(String messageKey, Token token) {
    return new RunCodeError(ErrorType.INTERPRET, messageKey, token.lineNumber(), token.line());
  }
}
