import java.util.List;

/**
 * SLOGO-02: Tokenize an invalid variable name, throwing an exception
 */
public class TokenizeNegativeUseCase {

  public static void main(String[] args) {
    Tokenizer tokenizer = new Tokenizer();
    try {
      tokenizer.tokenize("invalidVariable");
    } catch (TokenizeException exception) {
      System.out.println("Handle exception: " + exception.getMessage());
    }
  }

  private enum TokenType {
    VARIABLE
  }

  private record Token(TokenType type, int lineNumber, int startPosition) {

  }

  private static class Tokenizer {

    boolean isVariable(String string) {
      return false;
    }

    // uses regex in specification to identify each token (terminal)
    List<Token> tokenize(String string) throws TokenizeException {
      if (!isVariable(string)) {
        throw new TokenizeException();
      }
      return List.of(new Token(TokenType.VARIABLE, 0, 0));
    }
  }

  private static abstract class RunProgramException extends RuntimeException {

  }

  private static class TokenizeException extends RunProgramException {

  }
}
