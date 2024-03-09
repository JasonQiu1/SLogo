package slogo.view.userinterface;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import slogo.view.LanguageManager;

/**
 * Represents a text field element in the user interface. This class provides methods to handle
 * keyboard input and setup of the text field. It includes methods to set up event handlers for
 * keyboard input and control key handling.
 *
 * @author Jeremyah Flowers, Judy He
 */
public class UITextField extends UIElement {

  // Instance Variables
  private final TextField myTextBox;
  private final List<String> textCollector;
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
    myTextBox.setText(LanguageManager.translate(LanguageManager.getCurrentLanguage(), text));
    setPosition(x, y);
  }

  /**
   * Sets up event handlers for keyboard input and control key handling. Handles the following keys:
   * - Control or Command: Sets the controlPressed flag to true when pressed and false when
   * released. - R (with Control or Command): Sends a signal when pressed in combination with the
   * Control or Command key. - Enter: Adds the text in the text field to the textCollector list and
   * clears the text field. - Up arrow: Displays the previous text from the textCollector list in
   * the text field. - Down arrow: Displays the next text from the textCollector list in the text
   * field, or clears the text field if at the end.
   */
  public void setupTextBox() {
    keyboardInputHandler();
    controlHandler();
  }

  /**
   * Gets the text stored in the textCollector list.
   *
   * @return The raw string input.
   */
  public String getTextCommands() {
    indexTracker = 0;
    return myTextBox.getText();
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

    myTextBox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        switch (e.getCode()) {
          case CONTROL, COMMAND -> controlPressed = true;
          case R -> {
            if (controlPressed) {
              textCollector.add(myTextBox.getText());
              indexTracker++;
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
              textCollector.add(myTextBox.getText());
              myTextBox.clear();
            }
          }
        }
      }
    });
  }
}
