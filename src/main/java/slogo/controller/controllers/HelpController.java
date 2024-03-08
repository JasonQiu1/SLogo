package slogo.controller.controllers;

import java.util.HashMap;
import java.util.Map;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIListView;
import slogo.view.userinterface.UITextField;
import slogo.view.windows.HelpWindow;

/**
 * Implements UIController interface to manage Variable, Command, History, and Help Pop Up Windows
 *
 * @author Jordan Haytaian
 */
public class HelpController extends UIController {

  /**
   * Notifies the help controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  public void notifyController(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "textfield" -> updateVarValue(element);
    }
    switch (element.getID()) {
      case "Variables", "Commands", "Help", "History" -> {
        new HelpWindow(element.getID().toLowerCase(), this.getCurrentSession());
      }
      case "User-Defined Commands" -> {
        String expandText = getCommandInfo(((UIListView) element).getSelectedItem());
        new HelpWindow("command expand", this.getCurrentSession(), expandText);
      }
      case "Command History" -> {
        runCommandFromHistory(element);
      }
      case "Variable List" -> {
        String varName = getVarName(((UIListView) element).getSelectedItem());
        new HelpWindow("variable expand", this.getCurrentSession(), varName);
      }
    }
  }

  private String getVarName(String option) {
    Map<String, Double> varMap = getCurrentSession().getVariables();

    String[] parts = option.split("Name: |\\nValue: ");
    String varName = parts[1];
    varMap.get(varName);
    return varName;
  }

  private String getCommandInfo(String option) {
    Map<String, Map<String, String>> commandMap = this.getCurrentSession().getUserDefinedCommands();
    Map<String, String> commandMetaData = commandMap.get(option);
    String body = commandMetaData.get("body");
    return option + "\nNested Commands: " + body;
  }

  private void runCommandFromHistory(UIElement element) {
    String command = ((UIListView) element).getSelectedItem();
    this.getCurrentSession().run(command);
  }

  private void updateVarValue(UIElement element) {
    String varName = element.getID();
    String varValue = ((UITextField) element).getTextCommands();
    String command = "make :" + varName + " " + varValue;
    this.getCurrentSession().run(command);
  }
}
