package View.UserInterface;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class UIButton extends UIElement {

  public static final String PAUSE_PLAY_IMG = "/button_images/PausePlayButton.png";
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

  public void setPausePlayClassic() {
    Image img = new Image(PAUSE_PLAY_IMG);
    ImageView pausePlay = new ImageView(img);
    pausePlay.setFitHeight(20);
    pausePlay.setFitWidth(20);
    myButton.setText("");
    myButton.setShape(new Ellipse(200.0f, 120.0f, 150.0f, 80.f));
    myButton.setGraphic(pausePlay);
  }

  private void setColor() {
    switch (getID()) {
      case "R" -> myButton.setTextFill(Color.RED);
      case "G" -> myButton.setTextFill(Color.GREEN);
      case "B" -> myButton.setTextFill(Color.BLUE);
    }
  }
}
