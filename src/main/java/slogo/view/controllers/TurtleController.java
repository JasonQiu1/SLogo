package slogo.view.controllers;

import slogo.model.turtleUtil.Turtle;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITurtle;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class TurtleController extends UIController {

  private UITurtle turtle;
  @Override
  public void notifyController(UIElement element) {
    turtle = (UITurtle) element;
  }

  private void changePosition(UIElement turtle) {
    turtle.update("X", 10);
    turtle.update("Y", 10);
    turtle.update("Heading", 30);
  }
}
