package View.UserInterface;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIButton extends UIElement {

  private static final Font BTN_FONT = Font.font("Verdana", FontWeight.MEDIUM, 15);
  private static final String RESET_IMG = "/button_images/ResetButton.png";
  private static final String PAUSE_PLAY_IMG = "/button_images/PausePlayButton.png";
  private static final String HOME_IMG = "/button_images/HomeButton.png";

  private final Button myButton;

  public UIButton(String text, double x, double y) {
    super(new Button(text), text);
    myButton = (Button) getElement();
    myButton.setFont(BTN_FONT);
    myButton.setTextFill(Color.GREEN);
    myButton.toFront();
    setPosition(x, y);
    addShadow();
  }


  public void setGUIClassic() {
    myButton.setShape(new Rectangle(80.0f, 20.0f));
    myButton.setMinSize(80, 20);
    myButton.setMaxSize(160, 40);
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

