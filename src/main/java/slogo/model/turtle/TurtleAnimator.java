package slogo.model.turtle;

import java.util.*;

public class TurtleAnimator {
  private TurtleState initialTurtleState;
  private double height;
  private double width;
  private double graphicsScalingFactor;
  private double speed;

  public TurtleState getInitialTurtleState() {
    return initialTurtleState;
  }

  public void setInitialTurtleState(TurtleState initialTurtleState) {
    this.initialTurtleState = initialTurtleState;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
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
