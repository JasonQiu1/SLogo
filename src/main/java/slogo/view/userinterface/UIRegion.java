package slogo.view.userinterface;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import tool.XmlHelper;

/**
 * Represents a region in the Slogo user interface. Extends the UIElement class. It provides methods
 * to set up the background and border styles for the region. It also contains methods to read theme
 * settings from an XML file.
 *
 * @author Jeremyah Flowers
 */
public class UIRegion extends UIElement {

  public static final String THEME_XML = "data/saved_files/theme.xml";
  // Instance Variables
  private final Region myRegion;

  /**
   * Constructor for UIRegion.
   *
   * @param ID     The unique identifier for the region.
   * @param width  The width of the region.
   * @param height The height of the region.
   * @param x      The x-coordinate of the region's position.
   * @param y      The y-coordinate of the region's position.
   */
  public UIRegion(String ID, double width, double height, double x, double y) {
    super(new Region(), ID);
    myRegion = (Region) getElement();
    myRegion.setShape(new Rectangle(x, y, width, height));
    myRegion.setPrefWidth(width);
    myRegion.setPrefHeight(height);
    myRegion.toBack();
    setPosition(x, y);
  }

  private static String findTheme() {

    return new XmlHelper().getElementFromTag("BackgroundTheme", THEME_XML);
  }

  /**
   * Sets up the background style for the region based on the current theme setting.
   */
  public void setupBackground() {
    String theme = findTheme();
    if (theme.equalsIgnoreCase("light")) {
      setBlackWhite();
    } else {
      setWhiteBlack();
    }
  }

  /**
   * Sets the background and border styles for the region to black and white.
   */
  public void setBlackWhite() {
    setBackground(Color.WHITE, Color.BLACK);
  }

  /**
   * Sets the background and border styles for the region to white and black.
   */
  public void setWhiteBlack() {
    setBackground(Color.BLACK, Color.WHITE);
  }

  /**
   * Sets the background and border styles for the region to green and blue.
   */
  public void setGreenBlue() {
    setBackground(Color.GREEN, Color.BLUE);
  }

  /**
   * Sets the background and border styles for the region to pink and purple.
   */
  public void setPinkPurple() {
    setBackground(Color.PINK, Color.PURPLE);
  }

  private void setBackground(Color background, Color border) {
    BackgroundFill fill = new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY);
    myRegion.setBackground(new Background(fill));

    BorderStrokeStyle borderStrokeStyle = new BorderStrokeStyle(
        StrokeType.INSIDE,
        StrokeLineJoin.MITER,
        StrokeLineCap.BUTT,
        10,
        0,
        null);

    BorderStroke borderStroke = new BorderStroke(
        border,
        borderStrokeStyle,
        new CornerRadii(0),
        new BorderWidths(1));

    myRegion.setBorder(new Border(borderStroke));
  }

}
