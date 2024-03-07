package slogo.controller.controllers;

import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;

public class XmlController extends UIController {

  XmlConfiguration myXMLConfiguration = new XmlConfiguration();

  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Save" -> saveSession(((UIButton) element).getMyPath());
      case "Load" -> loadSession(((UIButton) element).getMyPath());
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
      System.out.println("here");
      setSession(myXMLConfiguration.loadSession(filePath));
    } catch (XmlException e) {
      //TODO: Excpetion Handling
    }
  }

}
