package slogo.model.coderunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LexerTest {

  void assertTokenType(String input, String expectedString) {
    Lexer lexer = new Lexer(input);
    TokenType actual = lexer.nextToken().type();
    TokenType expected = Enum.valueOf(TokenType.class, expectedString);
    assertEquals(expected, actual);
  }

  void assertTokenLiteral(String input, String expected) {
    Lexer lexer = new Lexer(input);
    String actual = lexer.nextToken().literal().toString();
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({"+,PLUS", "-,MINUS", "*,STAR", "/,FORWARD_SLASH", "~,TILDA", "%,PERCENT",
      ">," + "GREATER_THAN", "<,LESS_THAN", ">=,GREATER_EQUAL_TO", "<=,LESS_EQUAL_TO",
      "==,EQUAL_TO", "!=," + "NOT_EQUAL_TO", "[," + "LEFT_SQUARE_BRACKET",
      "], RIGHT_SQUARE_BRACKET", " ,EOF", "#kasdj921ijd +,EOF"})
  void nextToken_simpleTokens(String input, String expectedString) {
    assertTokenType(input, expectedString);
  }

  @Test
  void nextToken_skipNextToken() {
    assertTokenType("#kaasdkj\n +", "PLUS");
  }
  @ParameterizedTest
  @CsvSource({"fd,fd","forward,forward",":myVariable,myVariable","98,98.0","-98,-98.0","-98.5,-98"
      + ".5"})
  void nextToken_aggregateTokens(String input, String expectedString) {
    assertTokenLiteral(input, expectedString);
  }

  @ParameterizedTest
  @CsvSource({"+ - :variable command, 'PLUS,MINUS,VARIABLE,COMMAND'"})
  void nextToken_multipleTokens(String input, String expectedStringsCombined) {
    String[] expectedString = expectedStringsCombined.split(",");
    Lexer lexer = new Lexer(input);
    for (int i = 0; i < expectedString.length; i++) {
      String actual = lexer.nextToken().type().toString();
      assertEquals(expectedString[i], actual);
    }
  }
}