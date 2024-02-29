package view.userinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Node;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.view.userinterface.UIElement;
import util.DukeApplicationTest;

class UIElementTest extends DukeApplicationTest {

  private UIElement testUIElement;
  private Button testButton;
  private String ID;

  @BeforeEach
  void setUp() {
    ID = "Random";
    testButton = new Button();
    testUIElement = new UIElement(testButton, ID);
  }

  @Test
  void getElementTest() {
    // Given: a UI Element object with a button node
    // When: a user calls the method getElement on the UIElement
    Node actual = testUIElement.getElement();
    Node expected = testButton;
    // Then: the returned value should be the previously declared button node
    assertEquals(expected, actual);
  }

  @Test
  void getIDTest() {
    // Given: a UI Element object with a button node
    // When: a user calls the method getID on the UIElement
    String actual = testUIElement.getID();
    String expected = ID;
    // Then: the returned value should be the previously declared ID
    assertEquals(expected, actual);
  }

  @Test
  void getTypeTest() {
    // Given: a UI Element object with a button node
    // When: a user calls the method getType on the UIElement
    String actual = testUIElement.getType();
    String expected = "Button";
    // Then: the returned value should be the previously declared ID
    assertEquals(expected, actual);
  }
}