package slogo.view.userinterface;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import slogo.model.math.Point;

/**
 * Represents a turtle graphic element in the Slogo user interface. Handles the display and
 * animation of a turtle. Extends the UIElement class. It includes methods to setup the turtle
 * graphic, update its position, and create animation.
 *
 * @author Jeremyah Flowers, Judy He
 */
public class UITurtle extends UIElement {

  private static final ResourceBundle configResourceBundle = ResourceBundle.getBundle(
      "slogo.Configuration");

  private static final int TURTLE_SIZE = 10;
  private static final String TURTLE_XML = "src/main/resources/turtle_image/selected_turtle.xml";
  private static final String DEFAULT_TURTLE = "turtle_image/turtle_img01.png";
  private static final String IMG_DIR = "turtle_image/";
  private static final double X_MIN = parseConfigDouble("X_MIN");
  private static final double X_MAX = parseConfigDouble("X_MAX");
  private static final double Y_MIN = parseConfigDouble("Y_MIN");
  private static final double Y_MAX = parseConfigDouble("Y_MAX");
  private final Circle MY_TURTLE;
  private final Point ORIGIN;
  private final Point CURRENT_POSITION;
  private final double INITIAL_HEADING;
  private double currentHeading;
  private UIPen myPen;
  private boolean penDown;

  /**
   * Constructs a UITurtle object with the specified turtle ID, x, and y coordinates.
   *
   * @param turtleID The ID of the turtle.
   * @param x        The initial x-coordinate of the turtle's position.
   * @param y        The initial y-coordinate of the turtle's position.
   */
  public UITurtle(String turtleID, double x, double y) {
    super(new Circle(TURTLE_SIZE), turtleID);
    MY_TURTLE = (Circle) getElement();
    MY_TURTLE.setFill(Color.BLACK);
    MY_TURTLE.toFront();
    ORIGIN = new Point(x, y);
    CURRENT_POSITION = new Point(x, y);
    currentHeading = 0;
    INITIAL_HEADING = 0;
    penDown = false;
    MY_TURTLE.setOnMouseClicked(click -> showTurtle(!isShowing()));
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
      MY_TURTLE.setFill(new ImagePattern(new Image(turtleImg.toFile().toString())));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Updates the turtle's position on the screen.
   */
  public void updateState(double x, double y, double angle) {
    Point pInitial = move(x, y);

    rotate(angle);

    if (penDown) {
      draw(pInitial, CURRENT_POSITION);
    }
  }

  private void rotate(double newHeading) {
    currentHeading = INITIAL_HEADING + newHeading;
    MY_TURTLE.setRotate(currentHeading);
  }

  private Point move(double x, double y) {
    double xInitial = CURRENT_POSITION.getX();
    double yInitial = CURRENT_POSITION.getY();

    if (xInitial >= X_MAX + ORIGIN.getX()) {
      xInitial = X_MIN + ORIGIN.getX();
      this.penDown = false;
    } else if (xInitial <= X_MIN + ORIGIN.getX()) {
      xInitial = X_MAX + ORIGIN.getX();
      this.penDown = false;
    }

    if (yInitial >= Y_MAX + ORIGIN.getY()) {
      yInitial = Y_MIN + ORIGIN.getY();
      this.penDown = false;
    } else if (yInitial <= Y_MIN + ORIGIN.getY()) {
      yInitial = Y_MAX + ORIGIN.getY();
      this.penDown = false;
    }

    CURRENT_POSITION.setX(ORIGIN.getX() + x);
    CURRENT_POSITION.setY(ORIGIN.getY() - y);

    setPosition(CURRENT_POSITION.getX(), CURRENT_POSITION.getY());

    return new Point(xInitial, yInitial);
  }

  private void draw(Point pInitial, Point pFinal) {
    myPen.draw(pInitial.getX() - MY_TURTLE.getRadius(), pInitial.getY() - MY_TURTLE.getRadius(),
        pFinal.getX() - MY_TURTLE.getRadius(), pFinal.getY() - MY_TURTLE.getRadius());
  }

  public void setPenDown(boolean penDown) {
    this.penDown = penDown;
  }

  public void setPen(UIPen pen) {
    myPen = pen;
  }

  public void clearScreen() {
    myPen.clearScreen();
  }

  public void showTurtle(Boolean doShow) {
    sendSignal();
    setStatus(doShow);
  }

  public Boolean isShowing() {
    return MY_TURTLE.getOpacity() > 0.5;
  }

  public void clearLastLine() {
    myPen.eraseLine();
  }

  private static double parseConfigDouble(String key) {
    return Double.parseDouble(configResourceBundle.getString(key));
  }
}
