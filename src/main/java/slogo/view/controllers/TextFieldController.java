package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;

public class TextFieldController extends UIController {
  private String[] preparedText;
  @Override
  public void notifyController(UIElement element) {
    UITextField textField = (UITextField) element;
    Collection<String> newestText =  textField.getText();
  }

}
