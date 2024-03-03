package slogo.view.userinterface;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class UITurtle extends UIElement {

  private static final int TURTLE_SIZE = 10;
  private final Circle myTurtle;
  private final String turtleImg = "/turtle_image/turtle_img01.png";

  public UITurtle(String turtleID, double x, double y) {
    super(new Circle(TURTLE_SIZE), turtleID);
    myTurtle = (Circle) getElement();
    myTurtle.setFill(Color.BLACK);
    myTurtle.toFront();
    setSpecialType("Turtle");
    setPosition(x, y);
  }


  public void setupTurtle() {
    myTurtle.setFill(new ImagePattern(new Image(turtleImg)));
  }

  public void rotate(double degree) {
    myTurtle.setRotate(degree);
  }

  public void moveX(double x) {
    myTurtle.setLayoutX(myTurtle.getCenterX() + x);
  }

  public void moveY(double y) {
    myTurtle.setLayoutY(myTurtle.getCenterY() + y);
  }

  public void setIMG(String path) {
    myTurtle.setFill(new ImagePattern(new Image(path)));
  }

}
