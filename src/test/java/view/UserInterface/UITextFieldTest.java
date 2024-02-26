package view.UserInterface;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import View.UserInterface.UITextField;
import javafx.scene.control.TextField;
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
}