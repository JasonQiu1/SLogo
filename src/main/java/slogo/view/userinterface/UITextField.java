package slogo.view.userinterface;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UITextField extends UIElement {

  private final TextField myTextBox;
  private boolean controlPressed = false;
  private int indexTracker = 0;

  public UITextField(String text, double x, double y) {
    super(new TextField(text), text);
    myTextBox = (TextField) getElement();
    myTextBox.setAlignment(Pos.BASELINE_LEFT);
    myTextBox.toFront();
    setPosition(x, y);
  }

  public void setupTextBox() {
    keyboardInputHandler();
    controlHandler();
  }

  private void controlHandler() {
    myTextBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.CONTROL) || e.getCode().equals(KeyCode.COMMAND)) {
          controlPressed = false;
        }
      }
    });
  }

  private void keyboardInputHandler() {
    ArrayList<String> textCollector = new ArrayList<>();

    myTextBox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        switch (e.getCode()) {
          case CONTROL, COMMAND -> controlPressed = true;
          case R -> {
            if (controlPressed) {
              System.out.println(textCollector);
              textCollector.clear();
              myTextBox.clear();
              indexTracker = 0;
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
}
