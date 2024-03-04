package slogo.model.coderunner;

public record Token(TokenType type, Object literal, int lineNumber, String line) {

}
