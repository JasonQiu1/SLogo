package slogo.view.builders;

import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;

public class RegionBuilder implements UIBuilder {

  @Override
  public void build(UIElement element) {
    UIRegion box = (UIRegion) element;
    switch (box.getID()) {
      case "BackgroundTheme", "TurtleBox", "BottomBox", "RightBox" -> {
        box.setupBackground();
      }
      default -> {
        throw new TypeNotPresentException(box.getID(), new Throwable());
      }
    }
  }
}
