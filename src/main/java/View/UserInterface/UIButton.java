package View.UserInterface;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIButton extends UIElement {

  private static final Font BTN_FONT = Font.font("Verdana", FontWeight.MEDIUM, 15);
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


  protected void createLogo(String imgPath, double width, double height) {
    Image img = new Image(imgPath);
    ImageView buttonView = new ImageView(img);
    buttonView.setFitWidth(width);
    buttonView.setFitHeight(height);
    myButton.setText("");
    myButton.setGraphic(buttonView);
  }

  public void addShadow() {
    myButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        myButton.setEffect(new DropShadow());
      }
    });

    myButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        myButton.setEffect(null);
      }
    });
  }
}
