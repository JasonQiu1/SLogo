package slogo.view.builders;

import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIText;

/**
 * Constructs UI text elements based on their type.
 *
 * @author Jeremyah Flowers
 */
public class TextBuilder implements UIBuilder {

  /**
   * Constructs a UI text element based on its type.
   *
   * @param element The UI element to be constructed.
   */
  @Override
  public void build(UIElement element) {
    UIText text = (UIText) element;
    switch (text.getID()) {
      case "SLOGO", "Help", "Command History", "User-Defined Variables", "User-Defined "
          + "Commands", "Save Session", "Set Value", "Index List" -> {
        text.setSlogoClassic();
      }
      case "Theme:", "Pen Colors:", "Speed:" -> {
        text.setRegularClassic();
      }
      case "Background:" -> {
        text.setSmallerClassic();
      }
      case "Error" -> {
        text.setErrorClassic();
      }
      default -> {
        text.setRegularClassic();
        text.setClickable();
      }
    }
  }
}
