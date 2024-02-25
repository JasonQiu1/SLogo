package View.UserInterface;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class UIButton extends UIElement {
  private static final String RESET_IMG = "/button_images/ResetButton.png";
  private static final String PAUSE_PLAY_IMG = "/button_images/PausePlayButton.png";
  private static final String HOME_IMG = "/button_images/HomeButton.png";

  private final Button myButton;

  public UIButton(String text, double x, double y) {
    super(new Button(text), text);
    myButton = (Button) getElement();
    myButton.setFont(getButtonFont());
    myButton.setTextFill(Color.GREEN);
    setPosition(x, y);
    addShadow();
  }

  public void setSelectorClassic() {
    myButton.setShape(new Rectangle(200.0f, 100.0f));
    myButton.setMinSize(200, 100);
    myButton.setMaxSize(400, 200);
  }

  public void setMenuClassic() {
    myButton.setShape(new Ellipse(200.0f, 120.0f, 150.0f, 80.f));
    myButton.setMinSize(15, 8);
    myButton.setMaxSize(150, 80);
  }

  public void setSpeedClassic() {
    myButton.setShape(new Rectangle(20.0f, 20.0f));
    myButton.setMinSize(20, 20);
    myButton.setMaxSize(40, 40);
  }

  public void setGUIClassic() {
    myButton.setShape(new Rectangle(80.0f, 20.0f));
    myButton.setMinSize(80, 20);
    myButton.setMaxSize(160, 40);
  }

  public void setPenClassic() {
    myButton.setShape(new Circle(20.0f));
    setColor();
  }

  public void setHomeClassic() {
    myButton.setShape(new Circle(15));
    createLogo(HOME_IMG, 15, 15);
  }
  public void setResetClassic() {
    myButton.setShape(new Circle(15));
    createLogo(RESET_IMG, 15, 15);
  }
  public void setPausePlayClassic() {
    myButton.setShape(new Ellipse(90.0f, 20.f));
    createLogo(PAUSE_PLAY_IMG, 20, 20);
  }

  private void createLogo(String imgPath, double width, double height) {
    Image img = new Image(imgPath);
    ImageView buttonView = new ImageView(img);
    buttonView.setFitWidth(width);
    buttonView.setFitHeight(height);
    myButton.setText("");
    myButton.setGraphic(buttonView);
  }




  private void setColor() {
    switch (getID()) {
      case "R" -> myButton.setTextFill(Color.RED);
      case "G" -> myButton.setTextFill(Color.GREEN);
      case "B" -> myButton.setTextFill(Color.BLUE);
    }
  }
}
