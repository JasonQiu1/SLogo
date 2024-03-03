package slogo.view.userinterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents a text field element in the user interface. This class provides methods to handle
 * keyboard input and setup of the text field.
 *
 * @author Jeremyah Flowers
 */
public class UITextField extends UIElement {

  // Instance Variables
  private final TextField myTextBox;
  private List<String> textCollector;
  private boolean controlPressed = false;
  private int indexTracker = 0;

  /**
   * Constructs a UITextField object with the specified initial text, x, and y coordinates.
   *
   * @param text The initial text to display in the text field.
   * @param x    The x-coordinate of the text field's position.
   * @param y    The y-coordinate of the text field's position.
   */
  public UITextField(String text, double x, double y) {
    super(new TextField(text), text);
    textCollector = new ArrayList<>();
    myTextBox = (TextField) getElement();
    myTextBox.setAlignment(Pos.BASELINE_LEFT);
    myTextBox.toFront();
    setPosition(x, y);
  }

  /**
   * Sets up event handlers for keyboard input and control key handling.
   */
  public void setupTextBox() {
    keyboardInputHandler();
    controlHandler();
  }

  private void controlHandler() {
    myTextBox.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
      if (isModifier(e.getCode())) {
        controlPressed = false;
      }
    });

  }

  private boolean isModifier(KeyCode code) {
    return code.equals(KeyCode.CONTROL) || code.equals(KeyCode.COMMAND);
  }


  private void keyboardInputHandler() {
    textCollector = new ArrayList<>();

    myTextBox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        switch (e.getCode()) {
          case CONTROL, COMMAND -> controlPressed = true;
          case R -> {
            if (controlPressed) {
              sendSignal();
            }
          }
          case ENTER -> {
            textCollector.add(myTextBox.getText());
            indexTracker++;
            myTextBox.clear();
          }
          case UP -> {
            if (!textCollector.isEmpty() && indexTracker > 0) {
              --indexTracker;
              myTextBox.setText(textCollector.get(indexTracker));
            }
          }
          case DOWN -> {
            if (indexTracker < textCollector.size() - 1) {
              ++indexTracker;
              myTextBox.setText(textCollector.get(indexTracker));
            } else if (!myTextBox.getText().isEmpty()) {
              ++indexTracker;
              myTextBox.setText("");
            }
          }
        }
      }
    });
  }

  public Collection<String> getText() {
    List<String> text = new ArrayList<>(textCollector);
    textCollector.clear();
    myTextBox.clear();
    indexTracker = 0;
    return text;
  }

}
