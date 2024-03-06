package slogo.view.userinterface;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.api.turtle.Vector;
import slogo.model.turtleutil.Turtle;

/**
 * Represents a turtle graphic element in the Slogo user interface.
 * Handles the display and animation of a turtle.
 * Extends the UIElement class.
 * It includes methods to setup the turtle graphic, update its position, and create animation.
 *
 * @author Jeremyah Flowers
 */
public class UITurtle extends UIElement {

  private static final int TURTLE_SIZE = 10;
  private static final String TURTLE_XML = "src/main/resources/turtle_image/selected_turtle.xml";
  private static final String DEFAULT_TURTLE = "turtle_image/turtle_img01.png";
  private static final String IMG_DIR = "turtle_image/";
  public final TurtleAnimator ANIMATOR;
  private final Circle myTurtle;
  private double dx = 0;
  private double dy = 0;
  private double dr = 0;
  private double x;
  private double y;
  private double rotation = 0;

  /**
   * Constructs a UITurtle object with the specified turtle ID, x, and y coordinates.
   *
   * @param turtleID The ID of the turtle.
   * @param x        The x-coordinate of the turtle's position.
   * @param y        The y-coordinate of the turtle's position.
   */
  public UITurtle(String turtleID, double x, double y) {
    super(new Circle(TURTLE_SIZE), turtleID);
    myTurtle = (Circle) getElement();
    myTurtle.setFill(Color.BLACK);
    myTurtle.toFront();
    ANIMATOR = new TurtleAnimator();
    this.x = x;
    this.y = y;
    setSpecialType("Turtle");
    setPosition(x, y);
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
  public void updatePosition() {
    x += dx;
    y += dy;
    rotation += dr;
    myTurtle.setLayoutX(x);
    myTurtle.setLayoutY(y);
    myTurtle.setRotate(rotation);
  }

  /**
   * Creates an animation for the turtle to move to a new position and heading.
   *
   * @param newX       The new x-coordinate of the turtle.
   * @param newY       The new y-coordinate of the turtle.
   * @param newHeading The new heading of the turtle.
   */
  public void createAnimation(double newX, double newY, double newHeading) {


    Map<Integer, List<TurtleStep>> eachTurtlesStep = new HashMap<>();

    Point point = new Point(x, y);
    TurtleState state = new TurtleState(point, rotation);
    Vector changePosition = new Vector(newX, newY);

    TurtleStep step = new TurtleStep(state, changePosition, newHeading);
    List<TurtleStep> steps = new ArrayList<>(List.of(step));
    eachTurtlesStep.put(0, steps);

    ANIMATOR.animateStep(eachTurtlesStep);
    animateTurtle();
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

  private void animateTurtle() {
    Map<Integer, TurtleState> nextState = ANIMATOR.nextFrame();
    while (!nextState.isEmpty()) {
      for (Integer turtleId : nextState.keySet()) {
        TurtleState state = nextState.get(turtleId);
        double heading = state.heading();
        Point position = state.position();

        dx = position.getX();
        dy = position.getY();
        dr = heading;
      }
      nextState = ANIMATOR.nextFrame();
    }
  }

}
