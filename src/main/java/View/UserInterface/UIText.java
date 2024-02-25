package View.UserInterface;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UIText extends UIElement {

  private static final Font SLOGO_FONT = Font.font("Verdana", FontWeight.BOLD, 35);
  private static final Font THEME_FONT = Font.font("Verdana", FontWeight.MEDIUM, 15);
  private static final Font SMALLER_FONT = Font.font("Verdana", FontWeight.MEDIUM, 12);

  private final Text myText;

  public UIText(String text, double x, double y) {
    super(new Text(text), text);
    myText = (Text) getElement();
    myText.setFill(Color.GREEN);
    setPosition(x, y);
  }

  public void setSlogoClassic() {
    myText.setFont(SLOGO_FONT);
  }

  public void setRegularClassic() {
    myText.setFont(THEME_FONT);
  }

  public void setSmallerClassic() {
    myText.setFont(SMALLER_FONT);
  }

}
