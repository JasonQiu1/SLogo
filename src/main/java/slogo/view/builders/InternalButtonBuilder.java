package slogo.view.builders;

import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UIElement;

/**
 * Constructs internal buttons based on their type.
 *
 * @author Jeremyah Flowers
 */
public class InternalButtonBuilder implements UIBuilder {

  /**
   * Constructs an internal button based on its type.
   *
   * @param element The UI element to be constructed.
   */
  @Override
  public void build(UIElement element) {
    InternalButton button = (InternalButton) element;
    switch (button.getID()) {
      case "Play/Pause" -> {
        button.setPausePlayClassic();
      }
      case "Step" -> {
        button.setStepClassic();
        // TODO: Implement functionality to step through simulation when clicked.
      }
      case "Reset" -> {
        button.setResetClassic();
      }
      case ".5x", "1x", "2x", "3x", "4x" -> {
        button.setSpeedClassic();
      }
      case "R", "G", "B" -> {
        button.setPenClassic();
      }
      default -> {
        throw new TypeNotPresentException(button.getID(), new Throwable());
      }
    }
  }
}
