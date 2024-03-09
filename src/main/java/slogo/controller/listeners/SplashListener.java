package slogo.controller.listeners;

import java.util.Collection;

import slogo.controller.controllers.LanguageController;
import slogo.controller.controllers.ThemeController;
import slogo.controller.controllers.TurtleController;
import slogo.controller.controllers.HelpController;
import slogo.controller.controllers.XmlController;
import slogo.view.userinterface.UIElement;

/**
 * SplashListener class implements UIListener interface to handle UI events on the splash screen. It
 * manages UI elements related to the splash screen.
 *
 * @author Jeremyah Flowers, Jordan Haytaian
 */
public class SplashListener implements UIListener {

  private final ThemeController myThemeController;
  private final LanguageController myLanguageController;
  private final HelpController myHelpController;
  private final XmlController myXmlController;
  private final TurtleController myTurtleController;

  /**
   * Constructor for SplashListener.
   */
  public SplashListener() {
    myThemeController = new ThemeController();
    myLanguageController = new LanguageController();
    myHelpController = new HelpController();
    myXmlController = new XmlController();
    myTurtleController = new TurtleController();
  }

  /**
   * Sends a signal to the appropriate controller based on the UI element type.
   *
   * @param element the UI element triggering the signal
   */
  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "checkbox" -> myThemeController.notifyController(element);
      case "internalbutton", "externalbutton" -> handleButtonElement(element);
      case "combobox" -> myLanguageController.notifyController(element);
    }
  }

  /**
   * Passes UI elements to their respective controllers for processing.
   *
   * @param elements the collection of UI elements to pass
   */
  @Override
  public void passElementsToController(Collection<UIElement> elements) {
    myThemeController.addAllElements(elements);
    myTurtleController.addAllElements(elements);
    myLanguageController.addAllElements(elements);
  }

  private void handleButtonElement(UIElement element) {
    switch (element.getID()) {
      case "TurtleSelector", "Load File" -> myTurtleController.notifyController(element);
      case "Help" -> myHelpController.notifyController(element);
    }
  }
}
