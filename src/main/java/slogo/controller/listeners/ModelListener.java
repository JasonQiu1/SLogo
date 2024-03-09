package slogo.controller.listeners;

import slogo.view.userinterface.UIElement;

/**
 * ModelListener interface defines methods to handle Model events and pass these events to
 * controllers. It facilitates communication between Model and controllers.
 *
 * @author Jeremyah Flowers
 */
public interface ModelListener {

  /**
   * Sends a signal to handle a UI element event.
   *
   * @param element the UI element triggering the event
   */
  void sendSignal(UIElement element);

}
