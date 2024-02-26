package view.UserInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import View.UserInterface.UICheckBox;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class UICheckBoxTest extends DukeApplicationTest {

  private UICheckBox testCheckBox;
  private String random;
  private double x;
  private double y;

  @BeforeEach
  void setUp() {
    random = "Random";
    x = 100;
    y = 100;
    testCheckBox = new UICheckBox(random, x, y);
  }

  @Test
  void setThemeCheckBoxTest() {
    // Given: a UI button object
    CheckBox actual = (CheckBox) testCheckBox.getElement();
    // When: a user calls the method setMenuClassic on the UIButton
    testCheckBox.setThemeCheckBox();

    // Then: the checkbox should have the theme font
    Font expected = Font.font("Verdana", FontWeight.LIGHT, 15);
    assertEquals(expected, actual.getFont());
  }

  @Test
  void setBackgroundCheckBoxTest() {
    // Given: a UI button object
    CheckBox actual = (CheckBox) testCheckBox.getElement();
    // When: a user calls the method setBackgroundCheckBox on the UIButton
    testCheckBox.setBackgroundCheckBox();

    // Then: the checkbox should have the theme font
    Font expected = Font.font("Verdana", FontWeight.LIGHT, 12);
    assertEquals(expected, actual.getFont());
  }
}