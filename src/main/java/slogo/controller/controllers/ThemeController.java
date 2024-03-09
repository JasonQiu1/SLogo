package slogo.controller.controllers;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Map;
import javafx.scene.effect.Light;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;
import slogo.view.windows.HelpWindow;

/**
 * ThemeController class implements UIController interface to manage theme UI elements. It provides
 * functionality to control the theme of the application.
 *
 * @author Jeremyah Flowers
 */
public class ThemeController extends UIController {

  public static final String THEME_XML = "data/saved_files/theme.xml";

  // Instance Variable
  private boolean lightFlag = true;

  /**
   * Notifies the theme controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(UIElement element) {
    switch (element.getID()) {
      case "Light" -> setLightFlag();
      case "Dark" -> setDarkFlag();
      case "Load Preferences" -> setFlagFromPreferences(((UIButton) element).getMyPath());
    }
    updateElements();
  }

  // Private helper methods

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    switch (element.getID()) {
      case "Light" -> setCheckBoxTheme((UICheckBox) element, lightFlag);
      case "Dark" -> setCheckBoxTheme((UICheckBox) element, !lightFlag);
      case "BackgroundTheme" -> updateBackgroundTheme((UIRegion) element);
      case "Load Preferences" -> {
        UIRegion region = getBackgroundElement();
        if (region != null) {
          updateBackgroundTheme(getBackgroundElement());
        }
      }
    }
  }

  private void setCheckBoxTheme(UICheckBox checkbox, boolean flag) {
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

  private void setFlagFromPreferences(String filePath) {
    try {
      Map<String, String> prefMap = xmlConfiguration.getPreferences(filePath);
      String theme = prefMap.get("theme");
      if (theme.equalsIgnoreCase("light")) {
        setLightFlag();
      } else if (theme.equalsIgnoreCase("dark")) {
        setDarkFlag();
      }
    } catch (XmlException e) {
      new HelpWindow("error", getCurrentSession(), "unable to load preferences");
    }
  }

  private UIRegion getBackgroundElement() {
    for (UIElement element : getMyElements()) {
      if (element.getID().equalsIgnoreCase("BackgroundTheme")) {
        return ((UIRegion) element);
      }
    }
    return null;
  }
}