package slogo.view.builders;

import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;

/**
 * Constructs UI regions based on their type.
 *
 * @author Jeremyah Flowers
 */
public class RegionBuilder implements UIBuilder {

  /**
   * Constructs a UI region based on its type.
   *
   * @param element The UI element to be constructed.
   */
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
