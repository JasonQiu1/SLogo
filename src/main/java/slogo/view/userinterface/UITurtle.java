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

/**
 * Represents a turtle graphic element in the Slogo user interface. Handles the display and
 * animation of a turtle. Extends the UIElement class. It includes methods to setup the turtle
 * graphic, update its position, and create animation.
 *
 * @author Jeremyah Flowers, Judy He
 */
public class UITurtle extends UIElement {

  private static final int TURTLE_SIZE = 10;
  private static final String TURTLE_XML = "src/main/resources/turtle_image/selected_turtle.xml";
  private static final String DEFAULT_TURTLE = "turtle_image/turtle_img01.png";
  private static final String IMG_DIR = "turtle_image/";
  public static final double X_MIN = -150; // get from resource file
  public static final double X_MAX = 150; // get from resource file
  public static final double Y_MIN = -150; // get from resource file
  public static final double Y_MAX = 150; // get from resource file
  private final Circle myTurtle;
  private final double X_ORIGIN;
  private final double Y_ORIGIN;
  private final double initHeading;
  private double x, y, heading;
  private UIPen myPen;

  /**
   * Constructs a UITurtle object with the specified turtle ID, x, and y coordinates.
   *
   * @param turtleID The ID of the turtle.
   * @param x        The initial x-coordinate of the turtle's position.
   * @param y        The initial y-coordinate of the turtle's position.
   */
  public UITurtle(String turtleID, double x, double y) {
    super(new Circle(TURTLE_SIZE), turtleID);
    myTurtle = (Circle) getElement();
    myTurtle.setFill(Color.BLACK);
    myTurtle.toFront();
    this.x = x;
    this.y = y;
    this.heading = 0;
    this.X_ORIGIN = x;
    this.Y_ORIGIN = y;
    this.initHeading = 0;
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

  public void setPenColor(Color color) {
    myPen.setColor(color);
  }

  /**
   * Sets up the turtle graphic.
   */
  public void setupTurtle() {
    try {
      Path turtleImg = Paths.get(IMG_DIR, findTurtle());
      myTurtle.setFill(new ImagePattern(new Image(turtleImg.toFile().toString())));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Updates the turtle's position on the screen.
   */
  public void updateState(double x, double y, double angle) {
    double xInitial = this.x;
    double yInitial = this.y;

    if (xInitial >= X_MAX + X_ORIGIN) {
      xInitial = X_MIN + X_ORIGIN;
    }
    else if (xInitial <= X_MIN + X_ORIGIN) {
      xInitial = X_MAX + X_ORIGIN;
    }

    if (yInitial >= Y_MAX + Y_ORIGIN) {
      yInitial = Y_MIN + Y_ORIGIN;
    }
    else if (yInitial <= Y_MIN + Y_ORIGIN) {
      yInitial = Y_MAX + Y_ORIGIN;
    }

    this.x = X_ORIGIN + x;
    this.y = Y_ORIGIN - y;
    setPosition(this.x, this.y);
    
    this.heading = initHeading + angle;
    myTurtle.setRotate(this.heading);

    draw(xInitial, yInitial, this.x, this.y);
  }

  private void draw(double xInitial, double yInitial, double xFinal, double yFinial) {
    myPen.draw(xInitial - myTurtle.getRadius(), yInitial- myTurtle.getRadius(), xFinal - myTurtle.getRadius(), yFinial - myTurtle.getRadius());
  }


  public void setPen(UIPen pen) {
    myPen = pen;
  }

  public void clearScreen() {
    myPen.clearScreen();
  }

  public void showTurtle(Boolean doShow) {
    myTurtle.setDisable(!doShow);
  }

  public Boolean isShowing() {
    return myTurtle.isDisable();
  }

  public void clearLastLine() {
    myPen.eraseLine();
  }
}
