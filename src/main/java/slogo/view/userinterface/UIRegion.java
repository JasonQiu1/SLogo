package slogo.view.userinterface;

import java.io.File;
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
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * Represents a region in the Slogo user interface. Extends the UIElement class.
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
    try {
      File file = new File(THEME_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
      return doc.getElementsByTagName("BackgroundTheme").item(0).getTextContent();

    } catch (Exception e) {
      System.out.println(e);
    }
    return "light";
  }

  /**
   * Sets the classic background style for the region.
   */

  public void setupBackground() {
    String theme = findTheme();
    if (theme.equalsIgnoreCase("light")) {
      setBlackWhite();
    } else {
      setWhiteBlack();
    }
  }

  public void setBlackWhite() {
    setBackground(Color.WHITE, Color.BLACK);
  }

  public void setWhiteBlack() {
    setBackground(Color.BLACK, Color.WHITE);
  }

  public void setGreenBlue() {
    setBackground(Color.GREEN, Color.BLUE);
  }

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