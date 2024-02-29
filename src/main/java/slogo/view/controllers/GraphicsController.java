package slogo.view.controllers;

import javafx.scene.Node;

public class GraphicsController implements UIController {

  // Instance Variables
  private boolean blackWhiteFlag = true;
  private boolean greenBlueFlag = false;

  /**
   * Checks if the UI theme is set to black and white.
   *
   * @return true if the UI theme is black and white, false otherwise
   */
  public boolean isBlackAndWhite() {
    return blackWhiteFlag;
  }

  /**
   * Checks if the UI theme is set to green and blue.
   *
   * @return true if the UI theme is green and blue, false otherwise
   */
  public boolean isGreenAndBlue() {
    return greenBlueFlag;
  }

  /**
   * Checks if the UI theme is set to pink and purple.
   *
   * @return true if the UI theme is pink and purple, false otherwise
   */
  public boolean isPinkAndPurple() {
    return !blackWhiteFlag && !greenBlueFlag;
  }

  @Override
  public void notifyController(Node element) {
    switch (element.getId()) {
      case "BK/WH" -> setBackgroundBKWH();
      case "GN/BL" -> setBackgroundGNBL();
      case "PK/PR" -> setBackgroundPKPR();
    }
  }

  private void setBackgroundPKPR() {
    blackWhiteFlag = false;
    greenBlueFlag = false;
  }

  private void setBackgroundGNBL() {
    blackWhiteFlag = false;
    greenBlueFlag = true;
  }

  private void setBackgroundBKWH() {
    blackWhiteFlag = true;
    greenBlueFlag = false;
  }


}
