package slogo.controller.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
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
      if (isCommand(element.getID())) {
        runCommandFromHelp(element);
      } else {
        updateVarValue(element);
      }
    } else {
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
    Map<String, Map<String, String>> commandMap = this.getCurrentSession().getUserDefinedCommands();
    Map<String, String> commandMetaData = commandMap.get(option);
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

  private boolean isCommand(String text) {
    try {
      Map<String, String> commandMap = xmlConfiguration.loadHelpFile(helpFile);
      List<String> commands = new ArrayList<>();

      for (String command : commandMap.keySet()) {
        String[] lines = command.split("\n");
        for (String line : lines) {
          if (line.startsWith("Name:")) {
            commands.add(line.split(": ")[1]);
          }
        }
      }
      return commands.contains(text);
    } catch (XmlException e){
      return false;
    }
  }
}
