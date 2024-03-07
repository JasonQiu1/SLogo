package slogo.controller.controllers;

import slogo.view.userinterface.UIElement;
import slogo.view.windows.HelpWindow;

/**
 * Implements UIController interface to manage Variable, Command, History, and Help Pop Up Windows
 *
 * @author Jordan Haytaian
 */
public class HelpController extends UIController {

  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Variables", "Commands", "History", "Help" -> {
        new HelpWindow(element.getID().toLowerCase(), getCurrentSession());
      }
    }
  }
}
