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
  private final Circle myTurtle;
  private final double initX;
  private final double initY;
  private final double initHeading;
  private double x, y, heading;

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
    this.initX = x;
    this.initY = y;
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

//  /**
//   * Creates an animation for the turtle to move to a new position and heading.
//   *
//   * @param stepHistories       The step history of each turtle.
//   */
//  public void createAnimation(Map<Integer, List<TurtleStep>> stepHistories) {
//    ANIMATOR.animateStep(stepHistories);
//    animateTurtles();
//  }

  /**
   * Updates the turtle's position on the screen.
   */
  public void updateState(double x, double y, double angle) {
    this.x = initX + x;
    this.y = initY - y;
    setPosition(this.x, this.y);

    this.heading = initHeading + angle;
    myTurtle.setRotate(this.heading);
  }

//  private void updateTurtlePosition(double dx, double dy) {
//    Map<Integer, TurtleState> currentFrame = ANIMATOR.nextFrame();
//    while (!currentFrame.isEmpty()) {
//      for (Integer turtleId : currentFrame.keySet()) {
//        TurtleState state = currentFrame.get(turtleId);
//        double heading = state.heading();
//        Point position = state.position();
//
//        dx = position.getX();
//        dy = position.getY();
//        dr = heading;
//      }
//      currentFrame = ANIMATOR.nextFrame();
//    }
//  }

}
