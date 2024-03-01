package slogo.view.controllers;

import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITurtle;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class TurtleController extends UIController {

  @Override
  public void notifyController(UIElement element) {
    if (element.getType().equals("Turtle")) {
      changePosition((UITurtle) element);
    }
  }

  private void changePosition(UITurtle turtle) {
    turtle.moveX(10);
    turtle.moveY(10);
    turtle.rotate(30);
  }
}
