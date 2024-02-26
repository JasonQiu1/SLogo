package slogo.view.userinterface;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class InternalButton extends UIButton {

  private static final String RESET_IMG = "/button_images/ResetButton.png";
  private static final String PAUSE_PLAY_IMG = "/button_images/PausePlayButton.png";

  private final Button myButton;

  public InternalButton(String text, double x, double y) {
    super(text, x, y);
    myButton = (Button) getElement();
    setSpecialType("internalbutton");
  }

  public void setSpeedClassic() {
    myButton.setShape(new Rectangle(20.0f, 20.0f));
    myButton.setMinSize(20, 20);
    myButton.setMaxSize(40, 40);
  }

  public void setPenClassic() {
    myButton.setShape(new Circle(20.0f));
    setColor();
  }

  public void setResetClassic() {
    myButton.setShape(new Circle(15));
    createLogo(RESET_IMG, 15, 15);
  }

  public void setPausePlayClassic() {
    myButton.setShape(new Ellipse(90.0f, 20.0f));
    createLogo(PAUSE_PLAY_IMG, 20, 20);
  }

  private void setColor() {
    switch (getID()) {
      case "R" -> myButton.setTextFill(Color.RED);
      case "G" -> myButton.setTextFill(Color.GREEN);
      case "B" -> myButton.setTextFill(Color.BLUE);
    }
  }
}
