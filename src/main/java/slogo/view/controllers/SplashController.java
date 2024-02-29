package slogo.view.controllers;

import javafx.scene.Node;

/**
 * SplashController class implements UIController interface to manage UI elements in the splash
 * screen.
 * It provides functionality to control the appearance of the splash screen UI.
 *
 * @author Jeremyah Flowers
 */
public class SplashController implements UIController {

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
  public void notifyController(Node element) {
    switch (element.getId()) {
      case "Light" -> setLightFlag();
      case "Dark" -> setDarkFlag();
    }
  }

  private void setDarkFlag() {
    lightFlag = false;
  }

  private void setLightFlag() {
    lightFlag = true;
  }
}
