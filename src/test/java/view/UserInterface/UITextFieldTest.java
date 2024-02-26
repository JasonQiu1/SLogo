package view.UserInterface;

import static org.junit.jupiter.api.Assertions.*;

import View.UserInterface.UIButton;
import View.UserInterface.UITextField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class UITextFieldTest extends DukeApplicationTest {
  private UITextField testTextField;
  private String random;
  private double x;
  private double y;

  @BeforeEach
  void setUp() {
    random = "Random";
    x = 100;
    y = 100;
    testTextField = new UITextField(random, x, y);
  }

  @Test
  void setupTextBoxTest() {
    // Given: a UI textfield object
    TextField actual = (TextField) testTextField.getElement();
    // When: a user calls the method setupTextBox on the UIText
    testTextField.setupTextBox();

    // Then: the textfield should act as a functioning command line
    // AND there should be event handlers attached on key pressed
    // AND key released
    assertNotNull(actual.getOnKeyPressed());
    assertNotNull(actual.getOnKeyReleased());
  }
}