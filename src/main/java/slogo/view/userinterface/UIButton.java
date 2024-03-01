package slogo.view.userinterface;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents a basic button in the Slogo user interface. Extends the UIElement class.
 *
 * @author Jeremyah Flowers
 */
public class UIButton extends UIElement {

  // Constants
  private static final Font BTN_FONT = Font.font("Verdana", FontWeight.MEDIUM, 15);
  // Instance Variables
  private final Button myButton;

  /**
   * Constructor for UIButton.
   *
   * @param text The text to be displayed on the button.
   * @param x    The x-coordinate of the button's position.
   * @param y    The y-coordinate of the button's position.
   */
  public UIButton(String text, double x, double y) {
    super(new Button(text), text);
    myButton = (Button) getElement();
    myButton.setFont(BTN_FONT);
    myButton.setTextFill(Color.GREEN);
    myButton.toFront();
    setPosition(x, y);
    addShadow();
  }


  /**
   * Adds a shadow effect to the button when the mouse hovers over it.
   */

  public void addShadow() {
    myButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> myButton.setEffect(new DropShadow()));
    myButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> myButton.setEffect(null));
  }

  @Override
  public void update(String type, Object value) {
  }

  protected void createLogo(String imgPath, double width, double height) {
    Image img = new Image(imgPath);
    ImageView buttonView = new ImageView(img);
    buttonView.setFitWidth(width);
    buttonView.setFitHeight(height);
    myButton.setText("");
    myButton.setGraphic(buttonView);
  }
}
