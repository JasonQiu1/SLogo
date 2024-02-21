package View;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIButton {

  private final Button button;
  private final String ID;

  private static final Font SLOGO_BUTTON = Font.font("Verdana", FontWeight.MEDIUM, 15);

  public UIButton(String text, double positionX, double positionY) {
    button = new Button(text);
    ID = text;
    button.setLayoutX(positionX);
    button.setLayoutY(positionY);
  }

  public Button getButton() {
    return (button);
  }

  public String getID() {
    return (ID);
  }

  public void setSelectorClassic() {
    button.setShape(new Rectangle( 400.0f, 400.0f));
    button.setTextFill(Color.GREEN);
    button.setFont(SLOGO_BUTTON);
    button.setLayoutX(button.getLayoutX() - (button.getWidth())/2);
    button.setLayoutY(button.getLayoutY() - (button.getHeight())/2);
  }

  public void setMenuClassic() {
    button.setShape(new Ellipse(200.0f, 120.0f, 150.0f, 80.f));
    button.setTextFill(Color.GREEN);
    button.setFont(SLOGO_BUTTON);
    button.setLayoutX(button.getLayoutX() - (button.getWidth())/2);
    button.setLayoutY(button.getLayoutY() - (button.getHeight())/2);
  }

  public void addShadow() {
    button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent e) {
            button.setEffect(new DropShadow());
          }
        });

    button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent e) {
            button.setEffect(null);
          }
        });
  }

  public void addClickLink() {
    button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        button.setEffect(new DropShadow());
      }
    });

    button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        button.setEffect(null);
      }
    });
  }


}
