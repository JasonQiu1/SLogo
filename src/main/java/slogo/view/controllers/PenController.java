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
  private boolean redFlag = true;
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
        selectPen((UIButton) element, redFlag);
      }
      case "G" -> {
        selectPen((UIButton) element, greenFlag);
      }
      case "B" -> {
        selectPen((UIButton) element, blueFlag);
      }
    }
  }

  private void selectPen(UIButton pen, boolean flag) {
    pen.setPenStatus(flag);
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
