package slogo.controller.controllers;

import java.io.FileOutputStream;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;
import tool.XmlHelper;

/**
 * ThemeController class implements UIController interface to manage theme UI elements. It
 * provides functionality to control the theme of the application.
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
      new XmlHelper().updateFile(theme, "BackgroundTheme", THEME_XML);
    }
}