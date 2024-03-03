package view.userinterface;

import org.junit.jupiter.api.BeforeEach;
import slogo.view.userinterface.UITextField;
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