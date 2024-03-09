package slogo.controller.controllers;

import java.util.Collection;
import java.util.List;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITurtle;

public class SessionController extends UIController {

  private int myPenIndex = 0;
  private PenController penController;
  private TurtleController turtleController;


  public SessionController(List<UIController> controllers) {
    for (UIController controller : controllers) {
      addController(controller.getClass().getSimpleName(), controller);
    }
  }

  private void addController(String controllerName, UIController controller) {
    switch (controllerName) {
      case "PenController" -> penController = (PenController) controller;
      case "TurtleController" -> turtleController = (TurtleController) controller;

    }
  }

  @Override
  public void notifyController(UIElement element) {
    updateElements();
  }

  public void setPenIndex(int index) {
    myPenIndex = index;
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getType()) {
      case "Turtle" -> clearLastTurtleLine((UITurtle) element);
    }
  }

  private void clearLastTurtleLine(UITurtle turtle) {
    turtle.clearLastLine();
  }

  private void clearTurtleDrawing(UITurtle turtle) {
    turtle.clearScreen();
  }

}
