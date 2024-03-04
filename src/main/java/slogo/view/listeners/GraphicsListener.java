package slogo.view.listeners;

import java.util.Collection;
import slogo.view.controllers.BackgroundController;
import slogo.view.controllers.PenController;
import slogo.view.controllers.SpeedController;
import slogo.view.controllers.ThemeController;
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
  private final ThemeController myThemeController;

  public GraphicsListener() {
    myBackgroundController = new BackgroundController();
    myTurtleController = new TurtleController();
    mySpeedController = new SpeedController();
    myPenController = new PenController();
    myThemeController = new ThemeController();
  }

  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "checkbox" -> myBackgroundController.notifyController(element);
      case "turtle", "textfield" -> myTurtleController.notifyController(element);
      case "internalbutton", "externalbutton" -> handleButtonElement(element);
    }
  }

  @Override
  public void passElementsToController(Collection<UIElement> elements) {
    for (UIElement element : elements) {
      switch (element.getType().toLowerCase()) {
        case "checkbox" -> passToBackground(element);
        case "internalbutton" -> passButtonElement(element);
        case "region" -> {
          passToBackground(element);
          passToTheme(element);
        }
        case "turtle", "textfield" -> passToTurtle(element);

      }
    }
  }


  private void handleButtonElement(UIElement element) {
    switch (element.getID()) {
      case "1x", "2x", "3x", "4x" -> mySpeedController.notifyController(element);
      case "R", "G", "B" -> myPenController.notifyController(element);
    }
  }

  private void passToTheme(UIElement element) {
    myThemeController.addElement(element);
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

  private void passToSpeed(UIElement element) {
    mySpeedController.addElement(element);
  }

  private void passToPen(UIElement element) {
    myPenController.addElement(element);
  }

}
