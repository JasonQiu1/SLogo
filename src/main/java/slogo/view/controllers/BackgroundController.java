package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;

/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class BackgroundController extends UIController {

  // Instance Variables
  private boolean blackWhiteFlag = true;
  private boolean greenBlueFlag = false;

  /**
   * Notifies the graphics controller about changes in UI elements. Updates background according
   * to the UI element triggered.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "BK/WH" -> setBackgroundBKWH();
      case "GN/BL" -> setBackgroundGNBL();
      case "PK/PR" -> setBackgroundPKPR();
    }
    updateElements();
  }

  // Private helper methods

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getID()) {
      case "TurtleBox" -> updateTurtleBox((UIRegion) element);
      case "BK/WH" -> updateBKWHCheckbox((UICheckBox) element);
      case "GN/BL" -> updateGNBLCheckbox((UICheckBox) element);
      case "PK/PR" -> updatePKPRCheckbox((UICheckBox) element);
    }
  }

  private void updatePKPRCheckbox(UICheckBox checkbox) {
    checkbox.updateSelect(!blackWhiteFlag && !greenBlueFlag);
  }

  private void updateGNBLCheckbox(UICheckBox checkbox) {
    checkbox.updateSelect(greenBlueFlag);
  }

  private void updateBKWHCheckbox(UICheckBox checkbox) {
    checkbox.updateSelect(blackWhiteFlag);
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

  private void updateTurtleBox(UIRegion region) {
    if (blackWhiteFlag) {
      region.setBlackWhite();
    } else if (greenBlueFlag) {
      region.setGreenBlue();
    } else {
      region.setPinkPurple();
    }
  }
}
