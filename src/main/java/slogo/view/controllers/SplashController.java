package slogo.view.controllers;

import javafx.scene.Node;

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
