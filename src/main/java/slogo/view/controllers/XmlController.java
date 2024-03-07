package slogo.view.controllers;

import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;
import slogo.view.windows.HelpWindow;

public class XmlController extends UIController {

  private String dirPath = "src/main/resources";

  XmlConfiguration myXMLConfiguration = new XmlConfiguration();

  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Save" -> promptFileName(((UIButton) element).getMyPath());
      case "FileName" -> saveSession(((UITextField) element).getText()[0]);
      case "Load" -> loadSession(((UIButton) element).getMyPath());
    }
  }

  private void promptFileName(String dirPath) {
    this.dirPath = dirPath;
    new HelpWindow("promptFileName", getCurrentSession());
  }

  private void saveSession(String fileName) {
    try {
      Session mySession = getCurrentSession();
      String path = dirPath + "/" + fileName + ".slogo";
      myXMLConfiguration.saveSession(mySession, path);
    } catch (XmlException e) {
      //TODO: Exception Handling
    }
  }

  private void loadSession(String filePath) {
    try {
      myXMLConfiguration.loadSession(filePath);
    } catch (XmlException e) {
      //TODO: Excpetion Handling
    }
  }

}
