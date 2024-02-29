package slogo.view.controllers;

import javafx.scene.Node;

/**
 * UIController interface defines methods to manage UI elements and update UI themes.
 *
 * @author Jeremyah Flowers
 */
public interface UIController {


  /**
   * Notifies the controller about changes in UI elements, updating the UI theme accordingly.
   *
   * @param element the UI element triggering the notification
   */
  void notifyController(Node element);

}
