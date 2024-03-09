package slogo.view;

import static slogo.controller.controllers.LanguageController.LANGUAGE_TAG;
import static slogo.controller.controllers.LanguageController.LANGUAGE_XML;

import java.io.File;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * A class responsible for managing language translations.
 *
 * @author Jordan Haytaian, Jason Qiu
 */
public class LanguageManager {

  private final static String RESOURCES_DIRECTORY = "Translations/";
  private final static ResourceBundle englishResourceBundle;
  private final static ResourceBundle spanishResourceBundle;
  private final static ResourceBundle frenchResourceBundle;

  // Static block to initialize the resource bundle
  static {
    englishResourceBundle = ResourceBundle.getBundle(RESOURCES_DIRECTORY + "english");
    spanishResourceBundle = ResourceBundle.getBundle(RESOURCES_DIRECTORY + "spanish");
    frenchResourceBundle = ResourceBundle.getBundle(RESOURCES_DIRECTORY + "french");
  }

  public static String getKeyValue(String key) {
    return translate(getCurrentLanguage(), key);
  }

  /**
   * Translates the given text to the specified language
   *
   * @param text     the text to be translated
   * @param language the desired language
   * @return the translated text
   */
  public static String translate(String language, String text) {
    String sanitizedKey = text.trim().replaceAll("\\s+", "_").replaceAll(":", "");

    try {
      switch (language.toLowerCase()) {
        case "spanish" -> {
          return spanishResourceBundle.getString(sanitizedKey);
        }
        case "french" -> {
          return frenchResourceBundle.getString(sanitizedKey);
        }
        default -> {
          return englishResourceBundle.getString(sanitizedKey);
        }
      }
    } catch (MissingResourceException e) {
      return text;
    }
  }

  /**
   * Gets the current user-selected language of the application with english as default
   *
   * @return the current user-selected language
   */
  public static String getCurrentLanguage() {
    try {
      File file = new File(LANGUAGE_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
      return doc.getElementsByTagName(LANGUAGE_TAG).item(0).getTextContent();

    } catch (Exception e) {
      return "english";
    }
  }
}
