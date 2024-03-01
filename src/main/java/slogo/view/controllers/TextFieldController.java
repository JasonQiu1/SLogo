package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UIElement;

public class TextFieldController extends UIController {

  @Override
  public void notifyController(UIElement element) {
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    if (element.getType().equalsIgnoreCase("textfield")) {

    }
  }
}
