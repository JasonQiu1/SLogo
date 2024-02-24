package View.UserInterface;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class UICheckBox extends UIElement {

  private final CheckBox myBox;

  public UICheckBox(String text, double x, double y) {
    super(new CheckBox(text), text);
    myBox = (CheckBox) getElement();
    myBox.setFont(getFont());
    myBox.setTextFill(Color.GREEN);
    setPosition(x, y);
  }

  public void setCheckbox() {
    myBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        myBox.arm();
        System.out.println(myBox.isArmed());
      }
    });
  }
}
