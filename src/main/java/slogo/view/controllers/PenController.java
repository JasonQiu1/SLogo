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
public class PenController extends UIController {

  // Instance Variables
  private boolean redFlag = false;
  private boolean greenFlag = false;
  private boolean blueFlag = false;

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
        selectButton((UIButton) element, redFlag);
      }
      case "G" -> {
        selectButton((UIButton) element, greenFlag);
      }
      case "B" -> {
        selectButton((UIButton) element, blueFlag);
      }
    }
  }

  private void setPenR() {
    redFlag = !redFlag;
  }

  private void setPenG() {
    greenFlag = !greenFlag;
  }

  private void setPenB() {
    blueFlag = !blueFlag;
  }
}
