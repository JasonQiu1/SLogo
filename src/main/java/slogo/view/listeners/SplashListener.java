package slogo.view.listeners;

import java.util.Collection;
import slogo.view.controllers.LanguageController;
import slogo.view.controllers.ThemeController;
import slogo.view.controllers.TurtleController;
import slogo.view.userinterface.UIElement;

/**
 * SplashListener class implements UIListener interface to handle UI events on the splash screen.
 * It manages UI elements related to the splash screen.
 *
 * @author Jeremyah Flowers
 */
public class SplashListener implements UIListener {

  private final ThemeController myThemeController;
  private final TurtleController myTurtleController;
  private final LanguageController myLanguageController;
  /**
   * Constructor for SplashListener.
   */
  public SplashListener() {
    myThemeController = new ThemeController();
    myTurtleController = new TurtleController();
    myLanguageController = new LanguageController();
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
      case "button" -> myTurtleController.notifyController(element);
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
}
