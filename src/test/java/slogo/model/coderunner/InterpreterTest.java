package slogo.model.coderunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.coderunner.Expression.Binary;
import slogo.model.coderunner.Expression.Number;
import slogo.model.coderunner.Expression.Unary;
import slogo.model.command.Forward;
import slogo.model.command.Right;
import slogo.model.turtleutil.Turtle;

class InterpreterTest {

  private Interpreter interpreter;

  @BeforeEach
  void setup() {
    Environment global = new Environment(null);
    global.defineCommand("forward", new Forward());
    global.defineCommand("right", new Right());

    interpreter = new Interpreter(global, List.of(new Turtle()));
  }

  @ParameterizedTest
  @CsvSource({"2,2.0", "-2,-2.0", "2.2,2.2", "-2.2,-2.2", "-2.200,-2.2"})
  void evaluate_numbers(double input, double expected) {
    double actual = interpreter.evaluate(new Number(input));
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({"0,0.0", "1,-1.0", "-2.2,2.2", "-2.200,2.2"})
  void evaluate_unaryOperators(double input, double expected) {
    double actual = interpreter.evaluate(
        new Unary(new Token(TokenType.TILDA, null, 0, null), new Number(input)));
    assertEquals(expected, actual);
  }

  private void assertBinaryOperatorEquals(double expected, TokenType binaryOpStr, double left,
      double right) {
    double actual = interpreter.evaluate(
        new Binary(new Token(binaryOpStr, null, 0, null), new Number(left), new Number(right)));
    assertEquals(expected, actual, Math.abs(expected) / 1e3);
  }

  private void assertBinaryOperatorThrows(String expectedMessageKey, TokenType binaryOpStr,
      double left, double right) {
    RunCodeError actual = assertThrows(RunCodeError.class, () -> interpreter.evaluate(
        new Binary(new Token(binaryOpStr, null, 0, null), new Number(left), new Number(right))));
    assertEquals(expectedMessageKey, actual.getErrorMessageKey());
  }

  @Nested
  class evaluateBinaryOperators {

    @ParameterizedTest
    @CsvSource({"0,0,0", "1,0,1", "1,-2,-1", "0,2.5,2.5", "-1.7,3.8,2.1"})
    void plus(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.PLUS, left, right);
    }

    @ParameterizedTest
    @CsvSource({"0,0,0", "1,0,1", "1,-2,3", "0,2.5,-2.5", "-1.7,3.8,-5.5"})
    void minus(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.MINUS, left, right);
    }

    @ParameterizedTest
    @CsvSource({"0,0,0", "1,0,0", "1,-2,-2", "2,2.5,5", "-1.7,3.8,-6.46"})
    void multiply(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.STAR, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,-0.5", "2,2.5,0.8", "-1.7,3.8,-0.4473684210526316"})
    void divide(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.FORWARD_SLASH, left, right);
    }

    @ParameterizedTest
    @CsvSource({"0,0", "1,0"})
    void divideZero(double left, double right) {
      assertBinaryOperatorThrows("divideByZero", TokenType.FORWARD_SLASH, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,1", "2,2.5,2", "15,3.8,3.6"})
    void modulo(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.PERCENT, left, right);
    }

    @ParameterizedTest
    @CsvSource({"0,0", "1,0"})
    void moduloZero(double left, double right) {
      assertBinaryOperatorThrows("moduloByZero", TokenType.PERCENT, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,0", "2,2,1"})
    void equalTo(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.EQUAL_TO, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,1", "2,2,0"})
    void notEqualTo(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.NOT_EQUAL_TO, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,1", "2,2,0", "3,5,0"})
    void greaterThan(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.GREATER_THAN, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,1", "2,2,1", "3,5,0"})
    void greaterEqualTo(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.GREATER_EQUAL_TO, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,0", "2,2,0", "3,5,1"})
    void lessThan(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.LESS_THAN, left, right);
    }

    @ParameterizedTest
    @CsvSource({"1,-2,0", "2,2,1", "3,5,1"})
    void lessEqualTo(double left, double right, double expected) {
      assertBinaryOperatorEquals(expected, TokenType.LESS_EQUAL_TO, left, right);
    }

    @Test
    void invalidBinaryOperator() {
      assertBinaryOperatorThrows("invalidBinaryOperator", TokenType.EOF, 0, 1);
    }
  }
}