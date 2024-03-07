package slogo.controller.controllers;

import java.util.Map;
import slogo.model.api.Session;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIListView;
import slogo.view.windows.HelpWindow;

/**
 * Implements UIController interface to manage Variable, Command, History, and Help Pop Up Windows
 *
 * @author Jordan Haytaian
 */
public class HelpController extends UIController {

  Session session = getCurrentSession();

  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Variables", "Commands", "History", "Help" -> {
        new HelpWindow(element.getID().toLowerCase(), getCurrentSession());
      }
      case "User-Defined Variables" -> {
        String expandText = getVariableInfo(((UIListView) element).getSelectedItem());
        expandOption(expandText);
      }
      case "User-Defined Commands" -> {
        String expandText = getCommandInfo(((UIListView) element).getSelectedItem());
        expandOption(expandText);
      }
    }
  }

  private void expandOption(String expandText) {
    new HelpWindow("expand", getCurrentSession(), expandText);
  }

  private String getVariableInfo(String option) {
    //TODO: implement
  }

  private String getCommandInfo(String option) {

    Map<String, Map<String, String>> commandMap = session.getUserDefinedCommands();
    Map<String, String> commandMetaData = commandMap.get(option);
    String body = commandMetaData.get("body");
    return option + "\nNested Commands: " + body;
  }
}
