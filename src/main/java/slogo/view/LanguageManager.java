package slogo.view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A class responsible for managing language translations.
 */
public class LanguageManager {

  private final static ResourceBundle englishResourceBundle;
  private final static ResourceBundle spanishResourceBundle;
  private final static ResourceBundle frenchResourceBundle;

  // Static block to initialize the resource bundle
  static {
    englishResourceBundle = ResourceBundle.getBundle("Translations/translations");
    spanishResourceBundle = ResourceBundle.getBundle("Translations/translations_spanish");
    frenchResourceBundle = ResourceBundle.getBundle("Translations/translations_french");
  }

  /**
   * Translates the given text to the specified language
   *
   * @param text     the text to be translated
   * @param language the desired language
   * @return the translated text
   */
  public static String translate(String language, String text) {
    String sanitizedKey = text.trim().replaceAll("\\s+", "_").
        replaceAll(":", "");

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
}
