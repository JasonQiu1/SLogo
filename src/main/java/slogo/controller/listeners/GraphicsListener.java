package slogo.controller.listeners;

import java.util.Collection;

import slogo.controller.controllers.BackgroundController;
import slogo.controller.controllers.SpeedController;
import slogo.controller.controllers.TurtleController;
import slogo.controller.controllers.PenController;
import slogo.controller.controllers.ThemeController;
import slogo.controller.controllers.HelpController;
import slogo.controller.controllers.XmlController;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.view.userinterface.UIElement;
import slogo.model.api.Session;

/**
 * GraphicsListener class implements UIListener interface to handle UI events and pass them to
 * respective controllers. It manages UI elements related to graphics.
 *
 * @author Jeremyah Flowers, Judy He
 */
public class GraphicsListener implements UIListener {

  private final BackgroundController myBackgroundController;
  private final TurtleController myTurtleController;
  private final SpeedController mySpeedController;
  private final PenController myPenController;
  private final ThemeController myThemeController;
  private final HelpController myHelpController;
  private final XmlController myXmlController;
  private final Session SESSION = new Session();
  private final TurtleAnimator TURTLE_ANIMATOR = new TurtleAnimator();

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
      case "textfield" -> myTurtleController.notifyController(element);
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
        case "internalbutton", "externalbutton" -> passButtonElement(element);
        case "region" -> {
          passToBackground(element);
          passToTheme(element);
        }
        case "textfield" -> passToTurtle(element);
        case "turtle" -> {
          passToTurtle(element);
          passToPen(element);
        }
      }
    }
  }

  private void handleButtonElement(UIElement element) {
    switch (element.getID()) {
      case "0.5x", "1x", "2x", "4x" -> {
        mySpeedController.notifyController(element);
        myTurtleController.notifyController(element);
      }
      case "R", "G", "B" -> myPenController.notifyController(element);
      case "Play/Pause", "Reset" -> myTurtleController.notifyController(element);
      case "Variables", "Commands", "History", "Help" -> myHelpController.notifyController(element);
      case "Save", "Load" -> myXmlController.notifyController(element);
    }
  }

  private void passButtonElement(UIElement element) {
    switch (element.getID()) {
      case "0.5x", "1x", "2x", "4x" -> {
        passToSpeed(element);
        passToTurtle(element);
      }
      case "R", "G", "B" -> passToPen(element);
      case "Play/Pause", "Reset" -> passToTurtle(element);
      case "History" -> passToHelp(element);
      case "Save", "Load" -> passToXML(element);
    }
  }

  private void passToBackground(UIElement element) {
    myBackgroundController.addElement(element);
  }

  private void passToTurtle(UIElement element) {
    myTurtleController.setSession(SESSION);
    myTurtleController.setTurtleAnimator(TURTLE_ANIMATOR);
    myTurtleController.addElement(element);
  }

  private void passToTheme(UIElement element) {
    myThemeController.addElement(element);
  }

  private void passToSpeed(UIElement element) {
    mySpeedController.setSession(SESSION);
    mySpeedController.setTurtleAnimator(TURTLE_ANIMATOR);
    mySpeedController.addElement(element);
  }

  private void passToPen(UIElement element) {
    myPenController.addElement(element);
  }

  private void passToHelp(UIElement element) {
    myHelpController.setSession(SESSION);
    myHelpController.addElement(element);
  }

  private void passToXML(UIElement element) {
    myXmlController.setSession(SESSION);
    myXmlController.addElement(element);
  }
}