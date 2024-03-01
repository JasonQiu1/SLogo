package slogo.view.listeners;

import java.util.Collection;
import slogo.view.controllers.BackgroundController;
import slogo.view.controllers.PenController;
import slogo.view.controllers.SpeedController;
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

  public GraphicsListener() {
    myBackgroundController = new BackgroundController();
    mySpeedController = new SpeedController();
    myPenController = new PenController();
    myTurtleController = new TurtleController();
  }

  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "checkbox" -> myBackgroundController.notifyController(element);
      case "turtle", "text_field" -> myTurtleController.notifyController(element);
      case "internalbutton", "externalbutton" -> handleButtonElement(element);
    }
  }

  private void handleButtonElement(UIElement element) {
    switch (element.getID()) {
      case "1x", "2x", "3x", "4x" -> mySpeedController.notifyController(element);
      case "R", "G", "B" -> myPenController.notifyController(element);
    }
  }

  @Override
  public void passElementsToController(Collection<UIElement> elements) {
    myPenController.addAllElements(elements);
    mySpeedController.addAllElements(elements);
    myBackgroundController.addAllElements(elements);
  }
}
