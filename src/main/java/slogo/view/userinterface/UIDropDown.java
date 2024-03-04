package slogo.view.userinterface;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Represents a Drop-Down Menu in the Slogo user interface. Extends the UIElement class.
 *
 * @author Jordan Haytaian
 */
public class UIDropDown extends UIElement {

  private final ComboBox<String> myComboBox;

  /**
   * Constructor for UIDropDown
   *
   * @param text    display text when no option has been selected
   * @param options menu options
   * @param x       X coordinate of position
   * @param y       Y coordinate of position
   */
  public UIDropDown(String text, ObservableList<String> options, double x, double y) {
    super(new ComboBox<>(options), text);
    myComboBox = (ComboBox<String>) getElement();
    setDropDown();
    myComboBox.setPromptText(text);
    myComboBox.toFront();
    setPosition(x, y);
  }

  /**
   * Method to get option selected from menu
   *
   * @return option selected from drop down menu
   */
  public String getValue() {
    return myComboBox.getValue();
  }

  private void setDropDown() {
    myComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        sendSignal();
      }
    });
  }

}
