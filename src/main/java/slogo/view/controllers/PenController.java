package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UIElement;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class PenController extends UIController {

  // Instance Variables
  private boolean redFlag = true;
  private boolean greenFlag = false;
  private boolean blueFlag = false;


  /**
   * Checks if the UI theme is set to black and white.
   *
   * @return true if the UI theme is black and white, false otherwise
   */
  public boolean isRed() {
    return redFlag;
  }

  /**
   * Checks if the UI theme is set to green and blue.
   *
   * @return true if the UI theme is green and blue, false otherwise
   */
  public boolean isGreen() {
    return greenFlag;
  }

  /**
   * Checks if the UI theme is set to pink and purple.
   *
   * @return true if the UI theme is pink and purple, false otherwise
   */
  public boolean isBlue() {
    return blueFlag;
  }

  /**
   * Notifies the graphics controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "R" -> setPenR();
      case "G" -> setPenG();
      case "B" -> setPenB();
    }
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getID()) {
      case "R" -> {
        element.update("Boolean", isRed());
      }
      case "G" -> {
        element.update("Boolean", isBlue());
      }
      case "B" -> {
        element.update("Boolean", isGreen());
      }
    }
  }

  private void setPenR() {
    redFlag = true;
    greenFlag = false;
    blueFlag = false;
  }

  private void setPenG() {
    redFlag = false;
    greenFlag = true;
    blueFlag = false;
  }

  private void setPenB() {
    redFlag = false;
    greenFlag = false;
    blueFlag = true;
  }
}
