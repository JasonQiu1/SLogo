package slogo.view.controllers;

import javafx.scene.Node;

public interface UIController {


  /**
   * Notifies the controller about changes in UI elements, updating the UI theme accordingly.
   *
   * @param element the UI element triggering the notification
   */
  void notifyController(Node element);

}
