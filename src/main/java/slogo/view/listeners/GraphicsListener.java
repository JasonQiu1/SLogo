package slogo.view.listeners;

import java.util.Collection;
import slogo.view.controllers.BackgroundController;
import slogo.view.controllers.HelpController;
import slogo.view.controllers.PenController;
import slogo.view.controllers.SpeedController;
import slogo.view.controllers.ThemeController;
import slogo.view.controllers.TurtleController;
import slogo.view.controllers.XmlController;
import slogo.view.userinterface.UIElement;
import slogo.view.windows.HelpWindow;

/**
 * GraphicsListener class implements UIListener interface to handle UI events and pass them to
 * respective controllers. It manages UI elements related to graphics.
 *
 * @author Jeremyah Flowers
 */
public class GraphicsListener implements UIListener {

  private final BackgroundController myBackgroundController;
  private final TurtleController myTurtleController;
  private final SpeedController mySpeedController;
  private final PenController myPenController;
  private final ThemeController myThemeController;
  private final HelpController myHelpController;
  private final XmlController myXmlController;

  /**
   * Constructor for GraphicsListener.
   */
  public GraphicsListener() {
    myBackgroundController = new BackgroundController();
    myTurtleController = new TurtleController();
    mySpeedController = new SpeedController();
    myPenController = new PenController();
    myThemeController = new ThemeController();
    myHelpController = new HelpController();
    myXmlController = new XmlController();
  }

  /**
   * Sends a signal to the appropriate controller based on the UI element type.
   *
   * @param element the UI element triggering the signal
   */
  @Override
  public void sendSignal(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "checkbox" -> myBackgroundController.notifyController(element);
      case "turtle" -> myTurtleController.notifyController(element);
      case "textfield" -> handleTextField(element);
      case "internalbutton", "externalbutton" -> handleButtonElement(element);
    }
  }

  /**
   * Passes UI elements to their respective controllers for processing.
   *
   * @param elements the collection of UI elements to pass
   */
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

  // Private helper methods

  private void handleButtonElement(UIElement element) {
    switch (element.getID()) {
      case "1x", "2x", "3x", "4x" -> mySpeedController.notifyController(element);
      case "R", "G", "B" -> myPenController.notifyController(element);
      case "Variables", "Commands", "History", "Help" -> myHelpController.notifyController(element);
      case "Save", "Load" -> myXmlController.notifyController(element);
    }
  }

  private void handleTextField(UIElement element) {
    if (element.getID().equals("CommandLine")) {
      myTurtleController.notifyController(element);
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