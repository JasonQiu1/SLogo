package slogo.controller.controllers;

import java.util.List;
import java.util.Map;
import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIListView;
import slogo.view.windows.HelpWindow;

/**
 * Implements UIController interface to manage XML functionality
 *
 * @author Jordan Haytaian
 */
public class XmlController extends UIController {

  /**
   * Notifies the xml controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Save" -> saveSession(((UIButton) element).getMyPath());
      case "library commands" -> expandCommand(((UIListView) element).getSelectedItem());
    }
  }

  private void saveSession(String path) {
    try {
      Session mySession = getCurrentSession();
      xmlConfiguration.saveSession(mySession, path);
    } catch (XmlException e) {
      new HelpWindow("error", getCurrentSession(), "unable to save file");
    }
  }

  private void expandCommand(String option) {
    try {
      Map<String, String> commandMap = xmlConfiguration.loadHelpFile(helpFile);
      String commandInfo = commandMap.get(option);
      new HelpWindow("help expand", getCurrentSession(), commandInfo);
    } catch (XmlException e) {
      new HelpWindow("error", getCurrentSession(), "unable to load command info");
    }
  }

}
