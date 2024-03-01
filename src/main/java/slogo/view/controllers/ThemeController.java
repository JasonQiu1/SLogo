package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UIElement;

/**
 * SplashController class implements UIController interface to manage UI elements in the splash
 * screen. It provides functionality to control the appearance of the splash screen UI.
 *
 * @author Jeremyah Flowers
 */
public class ThemeController extends UIController {

  // Instance Variable
  private boolean lightFlag = true;


  /**
   * Checks if the UI theme is set to light. m
   *
   * @return true if the UI theme is light, false otherwise
   */
  public boolean isLight() {
    return lightFlag;
  }

  /**
   * Checks if the UI theme is set to dark.
   *
   * @return true if the UI theme is dark, false otherwise
   */
  public boolean isDark() {
    return !lightFlag;
  }

  /**
   * Notifies the splash controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */

  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Light" -> setLightFlag();
      case "Dark" -> setDarkFlag();
    }
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getID()) {
      case "Light" -> {
        element.update("Boolean", isLight());
      }
      case "Dark" -> {
        element.update("Boolean", isDark());
      }
    }
  }

  private void setDarkFlag() {
    lightFlag = false;
  }

  private void setLightFlag() {
    lightFlag = true;
  }
}
