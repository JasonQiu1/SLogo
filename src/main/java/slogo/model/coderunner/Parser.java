package slogo.model.coderunner;

import slogo.model.api.exception.coderunner.RunCodeError;

/**
 * Parses a stream of tokens into a stream of interpretable expressions.
 *
 * @author Jason Qiu
 */
class Parser {

  Parser(Lexer lexer) {
    this.lexer = lexer;
    previousToken = null;
    currentToken = null;
    nextToken();
  }

  Expression parseNext() throws RunCodeError {
    return expression();
  }

  private Expression expression() {
    return term();
  }

  private Expression term() {
    Expression expression = factor();
    if (match(TokenType.PLUS, TokenType.MINUS)) {
      Token operator = previousToken;
      Expression right = factor();
      return new Expression.Binary(operator, expression, right);
    }
    return expression;
  }

  private Expression factor() {
    Expression expression = comparison();
    if (match(TokenType.STAR, TokenType.FORWARD_SLASH, TokenType.PERCENT)) {
      Token operator = previousToken;
      Expression right = comparison();
      return new Expression.Binary(operator, expression, right);
    }
    return expression;
  }

  private Expression comparison() {
    Expression expression = unary();
    if (match(TokenType.EQUAL_TO, TokenType.NOT_EQUAL_TO, TokenType.GREATER_EQUAL_TO,
        TokenType.LESS_EQUAL_TO, TokenType.GREATER_THAN, TokenType.LESS_THAN)) {
      Token operator = previousToken;
      Expression right = unary();
      return new Expression.Binary(operator, expression, right);
    }
    return expression;
  }

  private Expression unary() {
    Expression expression = literal();
    if (match(TokenType.TILDA)) {
      return new Expression.Unary(previousToken, expression);
    }
    return expression;
  }

  private Expression literal() {
    Expression expression = primary();
    if (match(TokenType.COMMAND)) {
      return new Expression.Call(previousToken);
    }
    if (match(TokenType.VARIABLE)) {
      return new Expression.Variable(previousToken);
    }
    if (match(TokenType.NUMBER)) {
      return new Expression.Number((double) previousToken.literal());
    }
    return expression;
  }

  // keywords and block expressions
  private Expression primary() {
    Expression expression = null;
    return expression;
//    if (expression != null) {
//      return expression;
//    }
//    throw Util.createError("unexpectedToken", currentToken);
  }

  private boolean match(TokenType... types) {
    for (TokenType type : types) {
      if (currentToken.type() == type) {
        nextToken();
        return true;
      }
    }
    return false;
  }

  private boolean isAtEnd() {
    return previousToken != null && previousToken.type() == TokenType.EOF;
  }

  private Token nextToken() {
    previousToken = currentToken;
    currentToken = lexer.nextToken();
    if (isAtEnd()) {
      throw Util.createError("expectedTokens", currentToken);
    }
    return currentToken;
  }

  private final Lexer lexer;
  private Token previousToken;
  private Token currentToken;
}
