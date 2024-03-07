package slogo.controller.controllers;

import java.util.Map;
import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIListView;
import slogo.view.windows.HelpWindow;

public class XmlController extends UIController {

  XmlConfiguration myXMLConfiguration = new XmlConfiguration();
  private final String helpFile = "data/commands/command_help_basic.xml";

  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Save" -> saveSession(((UIButton) element).getMyPath());
      case "Load" -> loadSession(((UIButton) element).getMyPath());
      case "library commands" -> expandCommand(((UIListView) element).getSelectedItem());
    }
  }

  private void saveSession(String path) {
    try {
      Session mySession = getCurrentSession();
      myXMLConfiguration.saveSession(mySession, path);
    } catch (XmlException e) {
      //TODO: Exception Handling
    }
  }

  private void loadSession(String filePath) {
    try {
      setSession(myXMLConfiguration.loadSession(filePath));
    } catch (XmlException e) {
      //TODO: Exception Handling
    }
  }

  private void expandCommand(String option) {
    try {
      Map<String, String> commandMap = myXMLConfiguration.loadHelpFile(helpFile);
      String commandInfo = commandMap.get(option);
      new HelpWindow("expand", getCurrentSession(), commandInfo);
    } catch (XmlException e) {
      //TODO: Exception handling
    }

  }

}
