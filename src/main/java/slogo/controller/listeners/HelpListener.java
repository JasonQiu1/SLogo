package slogo.controller.listeners;

import java.util.Collection;
import slogo.controller.controllers.HelpController;
import slogo.controller.controllers.XmlController;
import slogo.view.userinterface.UIElement;

/**
 * HelpListener class implements UIListener interface to handle UI events in help windows.
 *
 * @author Jordan Haytaian
 */
public class HelpListener implements UIListener {

  HelpController myHelpController = new HelpController();
  XmlController myXmlController = new XmlController();

  @Override
  public void sendSignal(UIElement element) {
    switch (element.getID().toLowerCase()) {
      case "library commands" -> myXmlController.notifyController(element);
      case "user-defined commands", "user-defined variables", "history", "command history", "variable list" ->
          myHelpController.notifyController(element);
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
