package slogo.view.userinterface;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import slogo.view.LanguageManager;

/**
 * Represents a basic button in the Slogo user interface. Extends the UIElement class. It
 * encapsulates methods to set up and customize the appearance and behavior of buttons in the Slogo
 * user interface.
 *
 * @author Jeremyah Flowers
 */
public class UIButton extends UIElement {

  // Constants
  private static final Font BTN_FONT = Font.font("Verdana", FontWeight.MEDIUM, 15);
  // Instance Variables
  private final Button myButton;

  private String myPath;

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
    setText(LanguageManager.translate(LanguageManager.getCurrentLanguage(), text));
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

  /**
   * Retrieves the path associated with the button.
   *
   * @return The path associated with the button.
   */
  public String getMyPath() {
    return myPath;
  }

  /**
   * Sets the path associated with the button.
   *
   * @param path The path to be associated with the button.
   */
  protected void setMyPath(String path) {
    myPath = path;
  }

  /**
   * Creates a logo for the button using the specified image path, width, and height.
   *
   * @param imgPath The path of the image to be used as the button logo.
   * @param width   The width of the button logo.
   * @param height  The height of the button logo.
   */
  protected void createLogo(String imgPath, double width, double height) {
    Image img = new Image(imgPath);
    ImageView buttonView = new ImageView(img);
    buttonView.setFitWidth(width);
    buttonView.setFitHeight(height);
    myButton.setText("");
    myButton.setGraphic(buttonView);
  }

  /**
   * Sets text of button
   *
   * @param text displayed text of button
   */
  public void setText(String text) {
    myButton.setText(text);
  }
}
