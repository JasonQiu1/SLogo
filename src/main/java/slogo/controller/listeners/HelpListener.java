package slogo.controller.listeners;

import java.util.Collection;
import slogo.controller.controllers.HelpController;
import slogo.controller.controllers.TurtleController;
import slogo.controller.controllers.XmlController;
import slogo.model.api.Session;
import slogo.model.turtleutil.Turtle;
import slogo.view.userinterface.UIElement;

/**
 * HelpListener class implements UIListener interface to handle UI events in help windows.
 *
 * @author Jordan Haytaian
 */
public class HelpListener implements UIListener {

  HelpController myHelpController = new HelpController();
  XmlController myXmlController = new XmlController();
  TurtleController myTurtleController = new TurtleController();
  private final Session session;

  /**
   * HelpListener constructor when session access is needed
   *
   * @param session current session
   */
  public HelpListener(Session session) {
    super();
    this.session = session;
  }

  /**
   * Default constructor for HelpListener
   */
  public HelpListener() {
    super();
    session = null;
  }

  @Override
  public void sendSignal(UIElement element) {
    myHelpController.setSession(session);
    switch (element.getType().toLowerCase()) {
      case "textfield" ->
        myHelpController.notifyController(element);
    }
    switch (element.getID().toLowerCase()) {
      case "library commands" -> myXmlController.notifyController(element);
      default -> {
<<<<<<< HEAD
        helpController.notifyController(element);
=======
        myHelpController.notifyController(element);
>>>>>>> 17fd9ee (Added XML Helper and Builders. [NOTE: someone pushed code that doesn't compile to the branch. please fix].)
        myTurtleController.notifyController(element);
      }
    }
  }

  /**
   * Passes UI elements to their respective controllers for processing.
   *
   * @param elements the collection of UI elements to pass
   */
  @Override
  public void passElementsToController(Collection<UIElement> elements) {

  }
}
