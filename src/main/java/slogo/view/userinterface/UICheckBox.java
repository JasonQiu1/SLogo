package slogo.view.userinterface;

import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import slogo.view.LanguageManager;

/**
 * Represents a checkbox in the Slogo user interface. Extends the UIElement class. It encapsulates
 * methods to set up and customize the appearance and behavior of checkboxes in the Slogo user
 * interface.
 *
 * @author Jeremyah Flowers
 */
public class UICheckBox extends UIElement {

  // Constants
  private static final Font THEME_TXT = Font.font("Verdana", FontWeight.LIGHT, 15);
  private static final Font BACKGROUND_TXT = Font.font("Verdana", FontWeight.LIGHT, 12);

  // Instance Variables
  private final CheckBox myBox;

  /**
   * Constructor for UICheckBox.
   *
   * @param text The text to be displayed next to the checkbox.
   * @param x    The x-coordinate of the checkbox's position.
   * @param y    The y-coordinate of the checkbox's position.
   */
  public UICheckBox(String text, double x, double y) {
    super(new CheckBox(text), text);
    myBox = (CheckBox) getElement();
    myBox.setTextFill(Color.GREEN);
    myBox.toFront();
    setText(LanguageManager.translate(LanguageManager.getCurrentLanguage(), text));
    setCheckbox();
    setPosition(x, y);
  }

  /**
   * Sets the font style for the checkbox to match the theme.
   */
  public void setThemeCheckBox() {
    myBox.setFont(THEME_TXT);
  }

  /**
   * Sets the font style for the checkbox to match the background.
   */
  public void setBackgroundCheckBox() {
    myBox.setFont(BACKGROUND_TXT);
  }

  private void setCheckbox() {
    myBox.addEventHandler(MouseEvent.MOUSE_CLICKED, c -> sendSignal());
  }

  /**
   * Updates the selection state of the checkbox.
   *
   * @param check True to set the checkbox as selected, false otherwise.
   */
  public void updateSelect(Boolean check) {
    myBox.setSelected(check);
  }

  /**
   * Sets text of checkbox
   *
   * @param text displayed text of checkbox
   */
  public void setText(String text) {
    myBox.setText(text);
  }
}
