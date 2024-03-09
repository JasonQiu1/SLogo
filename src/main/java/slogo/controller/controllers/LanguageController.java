package slogo.controller.controllers;

import java.util.Collection;
import slogo.view.LanguageManager;
import slogo.view.userinterface.ExternalButton;
import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIDropDown;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIText;
import tool.XmlHelper;

/**
 * LanguageController class implements UIController interface to manage language translations.
 *
 * @author Jordan Haytaian
 */
public class LanguageController extends UIController {

  public static final String LANGUAGE_XML = "data/saved_files/language.xml";
  public static final String LANGUAGE_TAG = "Language";
  private static final String ENGLISH = "english";
  private static final String SPANISH = "spanish";
  private static final String FRENCH = "french";
  private String languageFlag = "english";

  public LanguageController() {
    super();
    updateLanguageFile();
  }

  /**
   * Notifies the splash controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  public void notifyController(UIElement element) {
    UIDropDown dropDown = (UIDropDown) element;
    String selectedLanguage = dropDown.getValue();
    if (selectedLanguage != null) {
      switch (selectedLanguage.toLowerCase()) {
        case "spanish/española/espagnol":
          setSpanishFlag();
          break;
        case "french/francés/français":
          setFrenchFlag();
          break;
        default:
          setEnglishFlag();
          break;
      }
      updateLanguageFile();
      updateElements();
    }
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void updateElement(UIElement element) {
    String type = element.getType();
    switch (type.toLowerCase()) {
      case "internalbutton" -> {
        InternalButton button = (InternalButton) element;
        button.setText(LanguageManager.translate(languageFlag, button.getID()));
      }
      case "externalbutton" -> {
        ExternalButton button = (ExternalButton) element;
        button.setText(LanguageManager.translate(languageFlag, button.getID()));
      }
      case "text" -> {
        UIText text = (UIText) element;
        text.setText(LanguageManager.translate(languageFlag, text.getID()));
      }
      case "checkbox" -> {
        UICheckBox checkBox = (UICheckBox) element;
        checkBox.setText(LanguageManager.translate(languageFlag, checkBox.getID()));
      }
    }
  }

  private void setEnglishFlag() {
    languageFlag = ENGLISH;
  }

  private void setSpanishFlag() {
    languageFlag = SPANISH;
  }

  private void setFrenchFlag() {
    languageFlag = FRENCH;
  }

  private void updateLanguageFile() {
    new XmlHelper().updateFile(languageFlag, LANGUAGE_TAG, LANGUAGE_XML);
  }
}
