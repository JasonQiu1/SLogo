package slogo.view.userinterface;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class UITurtle extends UIElement {

  private static final int TURTLE_SIZE = 10;
  private final Circle myTurtle;
  private String turtleImg = "/turtle_image/turtle_img01.jpg";

  public UITurtle(String turtleID, double x, double y) {
    super(new Circle(TURTLE_SIZE), turtleID);
    myTurtle = (Circle) getElement();
    myTurtle.setFill(Color.BLACK);
    myTurtle.toFront();
    setSpecialType("Turtle");
    setPosition(x, y);
  }

  @Override
  public void update(String type, Object value) {
    switch (type) {
      case "X" -> myTurtle.setLayoutX((Double) value);
      case "Y" -> myTurtle.setLayoutY((Double) value);
      case "Heading" -> myTurtle.setRotate((Double) value);
      case "IMAGE" -> {
        turtleImg = (String) value;
        setupTurtle();
      }
    }
  }

  public void setupTurtle() {
    myTurtle.setFill(new ImagePattern(new Image(turtleImg)));
  }
}
