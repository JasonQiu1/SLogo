package view.userinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import slogo.view.userinterface.UIText;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class UITextTest extends DukeApplicationTest {

  private UIText testText;

  @BeforeEach
  void setUp() {
    String random = "Random";
    double x = 100;
    double y = 100;
    testText = new UIText(random, x, y);
  }

  @Test
  void setSlogoClassicTest() {
    // Given: a UI text object
    Text actual = (Text) testText.getElement();
    // When: a user calls the method setSlogoClassic on the UIText
    testText.setSlogoClassic();

    // Then: the text should have the SLOGO font
    Font expected = Font.font("Verdana", FontWeight.BOLD, 35);
    assertEquals(expected, actual.getFont());
  }

  @Test
  void setRegularClassicTest() {
    // Given: a UI text object
    Text actual = (Text) testText.getElement();
    // When: a user calls the method setRegularClassic on the UIText
    testText.setRegularClassic();

    // Then: the text should have the MEDIUM font
    Font expected = Font.font("Verdana", FontWeight.MEDIUM, 15);
    assertEquals(expected, actual.getFont());
  }

  @Test
  void setSmallerClassicTest() {
    // Given: a UI text object
    Text actual = (Text) testText.getElement();
    // When: a user calls the method setSmallerClassic on the UIText
    testText.setSmallerClassic();

    // Then: the text should have the SMALLER font
    Font expected = Font.font("Verdana", FontWeight.MEDIUM, 12);
    assertEquals(expected, actual.getFont());
  }
}