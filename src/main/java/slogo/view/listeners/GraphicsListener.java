package slogo.view.listeners;

import java.util.Collection;
import slogo.view.controllers.BackgroundController;
import slogo.view.controllers.PenController;
import slogo.view.controllers.SpeedController;
import slogo.view.controllers.TextFieldController;
import slogo.view.controllers.TurtleController;
import slogo.view.userinterface.UIElement;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class GraphicsListener implements UIListener {

  private final BackgroundController myBackgroundController;
  private final TurtleController myTurtleController;
  private final SpeedController mySpeedController;
  private final PenController myPenController;
  private final TextFieldController myTextFieldController;

  public GraphicsListener() {
    myBackgroundController = new BackgroundController();
    myTextFieldController = new TextFieldController();
    myTurtleController = new TurtleController();
    mySpeedController = new SpeedController();
    myPenController = new PenController();
  }

  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "checkbox" -> myBackgroundController.notifyController(element);
      case "turtle" -> myTurtleController.notifyController(element);
      case "textfield" -> myTextFieldController.notifyController(element);
      case "internalbutton", "externalbutton" -> handleButtonElement(element);
    }
  }

  @Override
  public void passElementsToController(Collection<UIElement> elements) {
    for (UIElement element : elements) {
      switch (element.getType().toLowerCase()) {
        case "checkbox", "region" -> passToBackground(element);
        case "turtle" -> passToTurtle(element);
        case "textfield" -> {
          passToTextField(element);
          passToTurtle(element);
        }
        case "internalbutton" -> passButtonElement(element);
      }
    }
  }

  private void handleButtonElement(UIElement element) {
    switch (element.getID()) {
      case "1x", "2x", "3x", "4x" -> mySpeedController.notifyController(element);
      case "R", "G", "B" -> myPenController.notifyController(element);
    }
  }

  private void passButtonElement(UIElement element) {
    switch (element.getID()) {
      case "1x", "2x", "3x", "4x" -> passToSpeed(element);
      case "R", "G", "B" -> passToPen(element);
    }
  }

  private void passToBackground(UIElement element) {
    myBackgroundController.addElement(element);
  }

  private void passToTurtle(UIElement element) {
    myTurtleController.addElement(element);
  }

  private void passToTextField(UIElement element) {
    myTextFieldController.addElement(element);
  }

  private void passToSpeed(UIElement element) {
    mySpeedController.addElement(element);
  }

  private void passToPen(UIElement element) {
    myPenController.addElement(element);
  }

}
