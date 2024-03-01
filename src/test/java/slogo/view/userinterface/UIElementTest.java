package view.userinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.Node;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import util.DukeApplicationTest;

class UIElementTest extends DukeApplicationTest {

  private Node testUIElement;
  private UIElement testButton;
  private String ID;

  @BeforeEach
  void setUp() {
    ID = "Random";
    testButton = new UIButton(ID, 1, 1);
  }

  @Test
  void getElementTest() {
    // Given: a UI Element object with a button node
    // When: a user calls the method getElement on the UIElement
    Node actual = testButton.getElement();

    // Then: the returned value should be the previously declared button node
    assertNotNull(actual);
  }

  @Test
  void getIDTest() {
    // Given: a UI Element object with a button node
    // When: a user calls the method getID on the UIElement
    String actual = testButton.getID();
    String expected = ID;
    // Then: the returned value should be the previously declared ID
    assertEquals(expected, actual);
  }

  @Test
  void getTypeTest() {
    // Given: a UI Element object with a button node
    // When: a user calls the method getType on the UIElement
    String actual = testButton.getType();
    String expected = "Button";
    // Then: the returned value should be the previously declared ID
    assertEquals(expected, actual);
  }
}