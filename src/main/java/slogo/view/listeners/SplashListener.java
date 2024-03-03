package slogo.view.listeners;

import java.util.Collection;
import slogo.view.controllers.ThemeController;
import slogo.view.userinterface.UIElement;

/**
 * SplashController class implements UIController interface to manage UI elements in the splash
 * screen. It provides functionality to control the appearance of the splash screen UI.
 *
 * @author Jeremyah Flowers
 */
public class SplashListener implements UIListener {

  private final ThemeController myThemeController;

  public SplashListener() {
    myThemeController = new ThemeController();
  }

  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "checkbox" -> myThemeController.notifyController(element);
    }
  }

  @Override
  public void passElementsToController(Collection<UIElement> elements) {
    myThemeController.addAllElements(elements);
  }


}
