package slogo.view.controllers;

import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
import slogo.view.userinterface.UIElement;

public class XmlController extends UIController {

  XmlConfiguration myXMLConfiguration = new XmlConfiguration();

  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Save" -> saveSession();
      case "Load" -> loadSession();
    }
  }

  private void saveSession(){
//    Session mySession = getCurrentSession();
//    myXMLConfiguration.saveSession(mySession, "exampleFileName");
  }

  private void loadSession(){

  }

}
