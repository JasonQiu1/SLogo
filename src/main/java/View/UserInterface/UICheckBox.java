package View.UserInterface;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UICheckBox extends UIElement {

  private static final Font THEME_TXT = Font.font("Verdana", FontWeight.LIGHT, 15);
  private static final Font BACKGROUND_TXT = Font.font("Verdana", FontWeight.LIGHT, 12);
  private final CheckBox myBox;

  public UICheckBox(String text, double x, double y) {
    super(new CheckBox(text), text);
    myBox = (CheckBox) getElement();
    myBox.setTextFill(Color.GREEN);
    myBox.toFront();
    setCheckbox();
    setPosition(x, y);
  }

  public void setThemeCheckBox() {
    myBox.setFont(THEME_TXT);
  }

  public void setBackgroundCheckBox() {
    myBox.setFont(BACKGROUND_TXT);
  }

  private void setCheckbox() {
    myBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println(myBox.isArmed());
      }
    });
  }


}
