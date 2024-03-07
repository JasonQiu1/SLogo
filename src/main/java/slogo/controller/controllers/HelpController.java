package slogo.controller.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  /**
   * Notifies the help controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
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
      case "Command History" -> {
        String expandText = getHistoryInfo(((UIListView) element).getSelectedItem());
        expandOption(expandText);
      }
    }
  }

  private void expandOption(String expandText) {
    new HelpWindow("expand", getCurrentSession(), expandText);
  }

  private String getVariableInfo(String option) {
    //TODO: implement
    return null;
  }

  private String getCommandInfo(String option) {
    Map<String, Map<String, String>> commandMap = session.getUserDefinedCommands();
    Map<String, String> commandMetaData = commandMap.get(option);
    String body = commandMetaData.get("body");
    return option + "\nNested Commands: " + body;
  }

  private String getHistoryInfo(String option) {
    List<Map<String, Map<String, String>>> historyList = session.getCommandHistory(0);
    //TODO: implement
    return null;
  }
}
