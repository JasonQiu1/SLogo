package slogo.view.userinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class UIRegionTest extends DukeApplicationTest {

  private UIRegion testRegion;
  private String random;
  private double x;
  private double y;
  private double width;
  private double height;

  @BeforeEach
  void setUp() {
    random = "Random";
    x = 100;
    y = 100;
    width = 100;
    height = 100;
    testRegion = new UIRegion(random, width, height, x, y);
  }

  @Test
  void setBackgroundClassicTest() {
    // Given: a UI button object
    Region actual = (Region) testRegion.getElement();
    // When: a user calls the method setMenuClassic on the UIButton
<<<<<<< HEAD
    //testRegion.setBackgroundClassic();
=======
>>>>>>> b46af0e (Abstracted away the listener class and controller into the controller folder)

    // Then: the region should have the default background
    // AND the region should have the default border

    BackgroundFill fill = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY);
    Background expectedBackground = new Background(fill);

    BorderStrokeStyle expectedStyle =
        new BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.MITER, StrokeLineCap.BUTT, 10, 0,
            null);

    BorderStroke expectedBorderStroke =
        new BorderStroke(Color.BLACK, expectedStyle, new CornerRadii(0), new BorderWidths(1));

    Border expectedBorder = new Border(expectedBorderStroke);

    assertEquals(expectedBackground, actual.getBackground());
    assertEquals(expectedBorder, actual.getBorder());
  }
}