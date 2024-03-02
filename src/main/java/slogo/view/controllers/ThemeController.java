package slogo.view.controllers;

import java.io.FileOutputStream;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;

/**
 * SplashController class implements UIController interface to manage UI elements in the splash
 * screen. It provides functionality to control the appearance of the splash screen UI.
 *
 * @author Jeremyah Flowers
 */
public class ThemeController extends UIController {

  public static final String THEME_XML = "data/saved_files/theme.xml";

  // Instance Variable
  private boolean lightFlag = true;

  /**
   * Notifies the splash controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */

  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Light" -> setLightFlag();
      case "Dark" -> setDarkFlag();
    }
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getID()) {
      case "Light" -> setTheme((UICheckBox) element, lightFlag);
      case "Dark" -> setTheme((UICheckBox) element, !lightFlag);
      case "BackgroundTheme" -> updateBackgroundTheme((UIRegion) element);
    }
  }

  private void setTheme(UICheckBox checkbox, boolean flag) {
    checkbox.updateSelect(flag);
  }

  private void setDarkFlag() {
    lightFlag = false;
  }

  private void setLightFlag() {
    lightFlag = true;
  }

  private void updateBackgroundTheme(UIRegion region) {
    if (lightFlag) {
      setTheme("light");
    } else {
      setTheme("dark");
    }
    region.setupBackground();
  }

  private void setTheme(String newTheme) {
    updateThemeFile(newTheme);
  }

  private void updateThemeFile(String theme) {
    try {
      FileOutputStream file = new FileOutputStream(THEME_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element backgroundTheme = doc.createElement("BackgroundTheme");
      backgroundTheme.setTextContent(theme);
      doc.appendChild(backgroundTheme);
      writeXml(doc, file);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
