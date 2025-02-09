package slogo.model.coderunner;

import java.util.Map;
import java.util.Stack;
import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;

/**
 * Transforms strings into a stream of Slogo tokens.
 *
 * @author Jason Qiu
 */
class Lexer {

  /**
   * All of the keywords' strings mapped to their enum types.
   */

  private static final Map<String, TokenType> keywordMap =
      Map.ofEntries(Map.entry("make", TokenType.MAKE), Map.entry("repeat", TokenType.REPEAT),
          Map.entry("dotimes", TokenType.DOTIMES), Map.entry("for", TokenType.FOR),
          Map.entry("if", TokenType.IF), Map.entry("ifelse", TokenType.IFELSE),
          Map.entry("to", TokenType.TO), Map.entry("turtles", TokenType.TURTLES),
          Map.entry("tell", TokenType.TELL), Map.entry("ask", TokenType.ASK),
          Map.entry("askwith", TokenType.ASKWITH));

  /**
   * Initializes the lexer for a string of commands.
   *
   * @param commands the commands to tokenize
   */
  Lexer(String commands) {
    if (commands == null) {
      commands = "";
    }
    input = commands;
    cursorIdx = 0;
    lineNumber = 0;
    lines = input.split("\n");
    blockStartCursors = new Stack<>();
    consumeWhiteSpace();
  }

  /**
   * Tokenizes the next token and returns it.
   *
   * @return the next token if successful
   * @throws RunCodeError if there was an error encountered while tokenizing
   */
  Token nextToken() throws RunCodeError {
    consumeWhiteSpace();
    char currentChar = consume();
    return switch (currentChar) {
      case '~' -> createToken(TokenType.TILDA);
      case '+' -> createToken(TokenType.PLUS);
      case '*' -> createToken(TokenType.STAR);
      case '/' -> createToken(TokenType.FORWARD_SLASH);
      case '%' -> createToken(TokenType.PERCENT);
      case '[' -> {
        blockStartCursors.push(cursorIdx);
        yield createToken(TokenType.LEFT_SQUARE_BRACKET);
      }
      case ']' -> new Token(TokenType.RIGHT_SQUARE_BRACKET, "]", lineNumber,
          input.substring(blockStartCursors.pop(), cursorIdx - 1).trim());
      case '\0' -> createToken(TokenType.EOF);
      case '\n' -> {
        lineNumber++;
        yield nextToken();
      }
      case '#' -> {
        lineComment();
        yield nextToken();
      }
      case '-' -> {
        if (isNumeric()) {
          // negative number
          yield number();
        } else {
          // minus operator
          yield createToken(TokenType.MINUS);
        }
      }
      default -> {
        Token logicOperator = matchLogicOperator(currentChar);
        if (logicOperator != null) {
          yield logicOperator;
        }
        Token aggregateToken = matchAggregateToken(currentChar);
        if (aggregateToken != null) {
          yield aggregateToken;
        }
        throw new RunCodeError(ErrorType.TOKENIZE, "invalidToken", lineNumber, currentLine());
      }
    };
  }

  private Token matchAggregateToken(char currentChar) {
    if (currentChar == ':' && isAlpha()) {
      consume();
      return createToken(TokenType.VARIABLE, name());
    } else if (isNumeric(currentChar)) {
      return number();
    } else if (isAlpha(currentChar)) {
      return command();
    }
    return null;
  }

  private Token matchLogicOperator(char currentChar) {
    if (currentChar == '=' && match('=')) {
      return createToken(TokenType.EQUAL_TO);
    } else if (currentChar == '!' && match('=')) {
      return createToken(TokenType.NOT_EQUAL_TO);
    } else if (currentChar == '>') {
      if (match('=')) {
        return createToken(TokenType.GREATER_EQUAL_TO);
      } else {
        return createToken(TokenType.GREATER_THAN);
      }
    } else if (currentChar == '<') {
      if (match('=')) {
        return createToken(TokenType.LESS_EQUAL_TO);
      } else {
        return createToken(TokenType.LESS_THAN);
      }
    }
    return null;
  }

  private int aggregateCursorIdx;
  private int cursorIdx;
  private final String input;
  private final String[] lines;
  private int lineNumber;
  private final Stack<Integer> blockStartCursors;

  private boolean isWhiteSpace() {
    return switch (peek()) {
      case ' ', '\t' -> true;
      default -> false;
    };
  }

  private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean isAlpha() {
    return isAlpha(peek());
  }

  private boolean isNumeric(char c) {
    return c >= '0' && c <= '9';
  }

  private boolean isNumeric() {
    return isNumeric(peek());
  }

  private Token number() {
    int sign = 1;
    setAggregateCursorIdxBefore();
    if (match('-')) {
      sign = -1;
    }
    while (isNumeric()) {
      consume();
    }
    if (match('.')) {
      while (isNumeric()) {
        consume();
      }
    }
    return createToken(TokenType.NUMBER, Double.parseDouble(getAggregateString()) * sign);
  }

  private String name() {
    setAggregateCursorIdxBefore();
    while (isAlpha()) {
      consume();
    }
    return getAggregateString();
  }

  private Token command() {
    String name = name() + (match('?') ? "?" : "");
    return createToken(keywordMap.getOrDefault(name, TokenType.COMMAND), name);
  }

  private String getAggregateString() {
    return input.substring(aggregateCursorIdx, cursorIdx);
  }

  private void setAggregateCursorIdxBefore() {
    aggregateCursorIdx = cursorIdx - 1;
  }

  private void lineComment() {
    while (!isDone() && !match('\n')) {
      consume();
    }
  }

  private Token createToken(TokenType type) {
    return new Token(type, null, lineNumber + 1, currentLine());
  }

  private Token createToken(TokenType type, Object literal) {
    return new Token(type, literal, lineNumber + 1, currentLine());
  }

  private boolean isDone() {
    return cursorIdx >= input.length();
  }

  private String currentLine() {
    return lines[lineNumber];
  }

  private char peek() {
    if (isDone()) {
      return '\0';
    }
    return input.charAt(cursorIdx);
  }

  private void consumeWhiteSpace() {
    while (isWhiteSpace()) {
      cursorIdx++;
    }
  }

  private char consume() {
    if (isDone()) {
      return '\0';
    }
    char ret = input.charAt(cursorIdx++);
    return ret;
  }

  // if matched, then advance at the same time
  private boolean match(char target) {
    if (peek() == target) {
      consume();
      return true;
    }
    return false;
  }
}
