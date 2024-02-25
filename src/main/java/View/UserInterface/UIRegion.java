package View.UserInterface;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class UIRegion extends UIElement {

  private final Region myRegion;
  public UIRegion(String ID, double width, double height, double x, double y) {
    super(new Region(), ID);
    myRegion = (Region) getElement();
    myRegion.setShape(new Rectangle(x, y, width, height));
    myRegion.setPrefWidth(width);
    myRegion.setPrefHeight(height);
    setPosition(x, y);
  }

  public void setBackgroundClassic() {
    myRegion.toBack();

    BackgroundFill fill = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY);
    myRegion.setBackground(new Background(fill));

    BorderStrokeStyle borderStrokeStyle = new BorderStrokeStyle(
            StrokeType.INSIDE,
            StrokeLineJoin.MITER,
            StrokeLineCap.BUTT,
            10,
            0,
            null);

    BorderStroke borderStroke = new BorderStroke(
            Color.BLACK,
            borderStrokeStyle,
            new CornerRadii(0),
            new BorderWidths(1));

    myRegion.setBorder(new Border(borderStroke));
  }

}
