package slogo.view.builders;

import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;

/**
 * A builder class for constructing checkboxes in the user interface.
 *
 * @author Jeremyah Flowers
 */
public class CheckBoxBuilder implements UIBuilder {

  /**
   * Constructs a UI element based on its type.
   *
   * @param element The UI element to be constructed.
   */
  @Override
  public void build(UIElement element) {
    UICheckBox checkBox = (UICheckBox) element;
    switch (checkBox.getID()) {
      case "Light", "Dark" -> {
        checkBox.setThemeCheckBox();
        // TODO: Implement functionality to navigate to Slogo Wiki when clicked.
      }
      case "BK/WH", "GN/BL", "PK/PR" -> {
        checkBox.setBackgroundCheckBox();
      }
      default -> {
        throw new TypeNotPresentException(checkBox.getID(), new Throwable());
      }
    }
  }
}
