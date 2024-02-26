package View.UserInterface;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class ExternalButton extends UIButton {

  private static final String HOME_IMG = "/button_images/HomeButton.png";
  private final Button myButton;

  public ExternalButton(String text, double x, double y) {
    super(text, x, y);
    myButton = (Button) getElement();
    setSpecialType("externalbutton");
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


  public void setHomeClassic() {
    myButton.setShape(new Circle(15));
    createLogo(HOME_IMG, 15, 15);
  }
}
