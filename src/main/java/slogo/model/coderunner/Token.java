package slogo.model.coderunner;

/**
 * Slogo tokens which hold extra debugging info.
 *
 * @author Jason Qiu
 */
public record Token(TokenType type, Object literal, int lineNumber, String line) {

}
