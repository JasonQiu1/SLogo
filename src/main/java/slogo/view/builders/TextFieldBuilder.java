package slogo.view.builders;

import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;

public class TextFieldBuilder implements UIBuilder {

  @Override
  public void build(UIElement element) {
    UITextField textField = (UITextField) element;
    textField.setupTextBox();
  }
}
