package slogo.view.builders;

import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;

public class CheckBoxBuilder implements UIBuilder {

  @Override
  public void build(UIElement element) {
    UICheckBox checkBox = (UICheckBox) element;
    switch (checkBox.getID()) {
      case "Light", "Dark" -> {
        checkBox.setThemeCheckBox();
        // TODO: MAKE BUTTON JUMP TO SLOGO WIKI
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

