package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class SpeedController extends UIController {

  // Instance Variables
  private boolean oneFlag = true;
  private boolean twoFlag = false;
  private boolean threeFlag = false;
  private boolean fourFlag = false;

  /**
   * Notifies the graphics controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "1x" -> setSpeedOne();
      case "2x" -> setSpeedTwo();
      case "3x" -> setSpeedThree();
      case "4x" -> setSpeedFour();
    }
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getID()) {
      case "1x" -> {
        selectButton((UIButton) element, oneFlag);
      }
      case "2x" -> {
        selectButton((UIButton) element, twoFlag);
      }
      case "3x" -> {
        selectButton((UIButton) element, threeFlag);
      }
      case "4x" -> {
        selectButton((UIButton) element, fourFlag);
      }
    }
  }

  private void setSpeedFour() {
    oneFlag = false;
    twoFlag = false;
    threeFlag = false;
    fourFlag = true;
  }

  private void setSpeedThree() {
    oneFlag = false;
    twoFlag = false;
    threeFlag = true;
    fourFlag = false;
  }

  private void setSpeedTwo() {
    oneFlag = false;
    twoFlag = true;
    threeFlag = false;
    fourFlag = false;
  }

  private void setSpeedOne() {
    oneFlag = true;
    twoFlag = false;
    threeFlag = false;
    fourFlag = false;
  }
}

