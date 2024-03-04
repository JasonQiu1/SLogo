package slogo.view.userinterface;

import java.io.File;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import slogo.model.LanguageManager;

/**
 * Represents a text element in the user interface. This class provides methods to customize the
 * appearance and style of the text. It includes methods to set the font style to Slogo classic,
 * regular classic, and smaller classic.
 *
 * @author Jeremyah Flowers
 */
public class UIText extends UIElement {

  // Constants
  private static final String FONT_FAMILY = "Verdana";
  private static final Font SLOGO_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 35);
  private static final Font MEDIUM_FONT = Font.font(FONT_FAMILY, FontWeight.MEDIUM, 15);
  private static final Font SMALLER_FONT = Font.font(FONT_FAMILY, FontWeight.MEDIUM, 12);


  // Instance Variable
  private final Text myText;

  /**
   * Constructs a UIText object with the specified text, x, and y coordinates.
   *
   * @param text The text to display.
   * @param x    The x-coordinate of the text's position.
   * @param y    The y-coordinate of the text's position.
   */
  public UIText(String text, double x, double y) {
    super(new Text(text), text);
    myText = (Text) getElement();
    myText.setFill(Color.GREEN);
    myText.toFront();
    myText.setText(LanguageManager.translate(getLanguage(), text));
    setPosition(x, y);
  }

  /**
   * Sets the font style to Slogo classic. This style is bold and larger for emphasis.
   */
  public void setSlogoClassic() {
    myText.setFont(SLOGO_FONT);
  }

  /**
   * Sets the font style to regular classic. This style is medium-sized for general text display.
   */
  public void setRegularClassic() {
    myText.setFont(MEDIUM_FONT);
  }

  /**
   * Sets the font style to smaller classic. This style is smaller for displaying smaller text or
   * labels.
   */
  public void setSmallerClassic() {
    myText.setFont(SMALLER_FONT);
  }

  /**
   * Sets the font style to error classic.  This style is large and dark-colored for displaying
   * error messages.
   */
  public void setErrorClassic() {
    myText.setFont(SLOGO_FONT);
    myText.setFill(Color.BLACK);
  }


  /**
   * Sets display text
   *
   * @param text displayed text
   */
  public void setText(String text) {
    myText.setText(text);
  }
}
