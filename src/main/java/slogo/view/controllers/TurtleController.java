package slogo.view.controllers;

import java.util.Collection;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITurtle;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class TurtleController extends UIController {

  private double x;
  private double y;
  private double rotation;

  @Override
  public void notifyController(UIElement element) {
    if (element.getType().equalsIgnoreCase("textfield")) {
      setMovement();
    }
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    if (element.getType().equalsIgnoreCase("turtle")) {
      changePosition((UITurtle) element);
    }
  }

  private void setMovement() {
    x = 0;
    y = 0;
    rotation = 0;
  }

  private void changePosition(UITurtle turtle) {
    turtle.moveX(x);
    turtle.moveY(y);
    turtle.rotate(rotation);
  }
}
