package slogo.exception.model.turtle;

import java.util.*;
import slogo.exception.model.turtle.Point;
import slogo.exception.model.turtle.TurtleState;
import slogo.exception.model.turtle.TurtleStep;
import slogo.model.turtle.Turtle;

public class TurtleAnimator {
  public static final String RESOURCE_PACKAGE = "slogo.model.configuration";
  private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PACKAGE);
  private static final TurtleState INITIAL_TURTLE_STATE = new TurtleState(new Point(0.0,0.0), 0.0); // get from resource file
  private static final double HEIGHT = Double.parseDouble(resourceBundle.getString("height")); // get from resource file
  private static final double WIDTH = Double.parseDouble(resourceBundle.getString("width")); // get from resource file
  private double graphicsScalingFactor;
  private double speed;

  public static TurtleState getInitialTurtleState() {
    return INITIAL_TURTLE_STATE;
  }

  public static double getHeight() {
    return HEIGHT;
  }

  public static double getWidth() {
    return WIDTH;
  }

  public double getGraphicsScalingFactor() {
    return graphicsScalingFactor;
  }

  public void setGraphicsScalingFactor(double graphicsScalingFactor) {
    this.graphicsScalingFactor = graphicsScalingFactor;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  // calculate the list of TurtleState needed to smoothly move the turtle for its given TurtleStep
  public static void animateSteps(Map<Integer, TurtleStep> eachTurtlesStep) {

  }
  // return the list of TurtleState needed to smoothly move the turtle(s) a step forward in the animation
  public static Map<Integer, List<TurtleState>> stepForward() {
    // TODO
    return null;
  }
  // return the list of TurtleState needed to smoothly move the turtle(s) a step backward in the animation
  public static Map<Integer, List<TurtleState>> stepBackward() {
    // TODO
    return null;
  }

  // reset turtles' state
  public static boolean resetTurtles(List<Turtle> turtles) {
    // TODO
    return false;
  }

  // check if turtle is within the animation window after a step
  protected static boolean checkInBound(Point position) {
    // TODO
     return false;
  }

}
