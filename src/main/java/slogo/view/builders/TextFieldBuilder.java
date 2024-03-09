package slogo.view.builders;

import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;

/**
 * Constructs UI text fields.
 *
 * @author Jeremyah Flowers
 */
public class TextFieldBuilder implements UIBuilder {

  /**
   * Constructs a UI text field.
   *
   * @param element The UI element to be constructed.
   */
  @Override
  public void build(UIElement element) {
    UITextField textField = (UITextField) element;
    textField.setupTextBox();
  }
}
