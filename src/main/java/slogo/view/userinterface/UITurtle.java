package slogo.view.userinterface;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class UITurtle extends UIElement {

  private static final int TURTLE_SIZE = 10;
  private static final String TURTLE_XML = "src/main/resources/turtle_image/selected_turtle.xml";
  private static final String DEFAULT_TURTLE = "turtle_image/turtle_img01.png";
  private static final String IMG_DIR = "turtle_image/";

  private final Circle myTurtle;


  public UITurtle(String turtleID, double x, double y) {
    super(new Circle(TURTLE_SIZE), turtleID);
    myTurtle = (Circle) getElement();
    myTurtle.setFill(Color.BLACK);
    myTurtle.toFront();
    setSpecialType("Turtle");
    setPosition(x, y);
  }

  private static String findTurtle() {
    try {
      File file = new File(TURTLE_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
      return doc.getElementsByTagName("SelectedTurtle").item(0).getTextContent();

    } catch (Exception e) {
      System.out.println(e);
    }
    return DEFAULT_TURTLE;
  }

  public void setupTurtle() {
    try {
      Path turtleImg = Paths.get(IMG_DIR, findTurtle());
      myTurtle.setFill(new ImagePattern(new Image(turtleImg.toFile().toString())));
    } catch (Exception e) {
      System.out.println(e);
    }
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
