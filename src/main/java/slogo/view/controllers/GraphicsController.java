package slogo.view.controllers;

import java.util.Collection;
import javafx.scene.Node;
import slogo.view.userinterface.UIElement;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class GraphicsController extends UIController {

  // Instance Variables
  private boolean blackWhiteFlag = true;
  private boolean greenBlueFlag = false;

  public GraphicsController() {

  }

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
  public void addElement(UIElement element) {

  }

  @Override
  public void addAllElements(Collection<UIElement> elements) {

  }

  /**
   * Notifies the graphics controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(Node element) {
    switch (element.getId()) {
      case "BK/WH" -> setBackgroundBKWH();
      case "GN/BL" -> setBackgroundGNBL();
      case "PK/PR" -> setBackgroundPKPR();
      default -> throw new TypeNotPresentException(element.getId(), new Throwable());
    }
  }

  public boolean notifyElement(Node element) {
    switch (element.getId()) {
      case "BK/WH" -> { return isBlackAndWhite(); }
      case "GN/BL" -> { return isGreenAndBlue(); }
      case "PK/PR" -> { return isPinkAndPurple(); }
      default -> throw new TypeNotPresentException(element.getId(), new Throwable());
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
