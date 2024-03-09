package slogo.view.builders;

import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UIElement;

public class InternalButtonBuilder implements UIBuilder {

  @Override
  public void build(UIElement element) {
    InternalButton button = (InternalButton) element;
    switch (button.getID()) {
      case "Play/Pause" -> {
        button.setPausePlayClassic();
      }
      case "Step" -> {
        button.setStepClassic();
        // TODO: MAKE BUTTON STEP THROUGH SIMULATION
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
