package slogo.controller.controllers;

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
    if (element.getType().equalsIgnoreCase("textfield")) {
      if (isVar(element.getID())) {
        updateVarValue(element);
      } else {
        runCommandFromHelp(element);
      }
    } else {
      switch (element.getID()) {
        case "Variables", "Commands", "Help", "History" -> {
          new HelpWindow(element.getID().toLowerCase(), this.getCurrentSession());
        }
        case "User Def Commands" -> {
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
        default -> {
          if (getCurrentSession() != null) {
            String command = getCommandName(element.getID());
            new HelpWindow("parameter expand", this.getCurrentSession(), command);
          }
        }
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

  private String getCommandName(String option) {
    String[] lines = option.split("\n");

    for (String line : lines) {
      if (line.startsWith("Name:")) {
        return line.split(": ")[1];
      }
    }
    return option;
  }

  private String getCommandInfo(String option) {
    String[] parts = option.split("\n");
    String[] commandParts = parts[0].split(":");
    String command = commandParts[1].trim();

    Map<String, Map<String, String>> commandMap = this.getCurrentSession().getUserDefinedCommands();
    Map<String, String> commandMetaData = commandMap.get(command);
    String body = commandMetaData.get("body");
    return option + "\nNested Commands: " + body;
  }

  private void runCommandFromHistory(UIElement element) {
    String command = ((UIListView) element).getSelectedItem();
    this.getCurrentSession().run(command);
  }

  private void runCommandFromHelp(UIElement element) {
    String command = element.getID();
    String parameters = ((UITextField) element).getTextCommands();
    this.getCurrentSession().run(command + " " + parameters);
  }

  private void updateVarValue(UIElement element) {
    String varName = element.getID();
    String varValue = ((UITextField) element).getTextCommands();
    String command = "make :" + varName + " " + varValue;
    this.getCurrentSession().run(command);
  }

  private boolean isVar(String text) {
    return getCurrentSession().getVariables().containsKey(text);
  }
}
