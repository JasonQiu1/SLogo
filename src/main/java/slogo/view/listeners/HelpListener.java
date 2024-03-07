package slogo.view.listeners;

import java.util.Collection;
import slogo.model.api.Session;
import slogo.view.controllers.HelpController;
import slogo.view.userinterface.UIElement;

public class HelpListener implements UIListener{

  HelpController myHelpController = new HelpController();

  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "textfield" -> myHelpController.notifyController(element);
    }

  }

  /**
   * Passes UI elements to their respective controllers for processing.
   *
   * @param elements the collection of UI elements to pass
   */
  @Override
  public void passElementsToController(Collection<UIElement> elements) {
  }
}
