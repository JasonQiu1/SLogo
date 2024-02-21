import java.util.List;

/**
 * SLOGO-02: Tokenize a valid variable name
 */
public class TokenizeUseCase {

  public static void main(String[] args) {
    Tokenizer tokenizer = new Tokenizer();
    tokenizer.tokenize(":myVariable");
  }

  private enum TokenType {
    VARIABLE
  }

  private record Token(TokenType type, int lineNumber, int startPosition) {

  }

  private static class Tokenizer {

    // uses regex in specification to identify each token (terminal)
    List<Token> tokenize(String string) {
      return List.of(new Token(TokenType.VARIABLE, 0, 0));
    }
  }
}
