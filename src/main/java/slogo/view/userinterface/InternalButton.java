package slogo.view.userinterface;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 * Represents a button with functionalities that only control internal states (no external links)
 * for the Slogo user interface. Extends the UIButton class. It encapsulates methods to set up
 * various types of internal buttons with specific appearances and functionalities.
 *
 * @author Jeremyah Flowers
 */
public class InternalButton extends UIButton {

  // Constants
  private static final String RESET_IMG = "/button_images/ResetButton.png";
  private static final String PAUSE_PLAY_IMG = "/button_images/PausePlayButton.png";

  // Instance Variables
  private final Button myButton;

  /**
   * Constructor for InternalButton.
   *
   * @param text The text to be displayed on the button.
   * @param x    The x-coordinate of the button's position.
   * @param y    The y-coordinate of the button's position.
   */
  public InternalButton(String text, double x, double y) {
    super(text, x, y);
    myButton = (Button) getElement();
    myButton.setOnMouseClicked(click -> sendSignal());
    setSpecialType("internalbutton");
  }

  /**
   * Sets the button to have a classic speed control appearance.
   */
  public void setSpeedClassic() {
    myButton.setShape(new Rectangle(20.0f, 20.0f));
    myButton.setMinSize(20, 20);
    myButton.setMaxSize(40, 40);
  }

  /**
   * Sets the button to have a classic pen control appearance.
   */
  public void setPenClassic() {
    myButton.setShape(new Circle(20.0f));
    setColor();
  }

  /**
   * Sets the button to have a classic reset appearance.
   */
  public void setResetClassic() {
    myButton.setShape(new Circle(15));
    createLogo(RESET_IMG, 15, 15);
  }

  /**
   * Sets the button to have a classic pause/play appearance.
   */
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
