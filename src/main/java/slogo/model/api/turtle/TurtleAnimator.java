package slogo.model.api.turtle;

import java.util.*;
import slogo.model.turtleutil.Turtle;
import slogo.model.api.exception.turtle.InvalidPositionException;

public class TurtleAnimator {
  // public static final String RESOURCE_PACKAGE = "slogo.model.Configuration";
  // private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PACKAGE);
  public static final TurtleState INITIAL_TURTLE_STATE = new TurtleState(new Point(0.0,0.0), 0.0); // get from resource file
  private static final double HEIGHT = 200; // Double.parseDouble(resourceBundle.getString("height")); // get from resource file
  private static final double WIDTH = 200; // Double.parseDouble(resourceBundle.getString("width")); // get from resource file
  private static final double SECOND_DELAY = 1.0;
  private double graphicsScalingFactor;
  private double speed;
  private double frameDuration; // 1s --> dx = 1, dy = 1
  // double frameDuration = 1.0 / SECOND_DELAY_KEY; // Calculate the duration for the KeyFrame
  private Map<Integer, List<TurtleState>> intermediateStates;
  public static String mode = "window";

  public TurtleAnimator() {
    this.graphicsScalingFactor = 1;
    this.speed = 1;
    this.frameDuration = 1.0 / (speed * SECOND_DELAY);
    this.intermediateStates = new HashMap<>();
  }

//  public double getFrameDuration() {
//    return frameDuration;
//  }
//
//  public void setFrameDuration(double frameDuration) {
//    this.frameDuration = frameDuration;
//  }

  public Map<Integer, List<TurtleState>> getIntermediateStates() {
    return intermediateStates;
  }

//  public void setIntermediateStates(
//      Map<Integer, List<TurtleState>> intermediateStates) {
//    this.intermediateStates = intermediateStates;
//  }

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
    return this.speed;
  }

  public void setSpeed(double speed) {
    frameDuration = 1.0 / (speed * SECOND_DELAY);
    this.speed = speed;
  }

  // calculate the list of TurtleState needed to smoothly move the turtle for its given TurtleStep
  public static void animateSteps(Map<Integer, TurtleStep> eachTurtlesStep) {
    // TODO

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
  public static void resetTurtles(List<Turtle> turtles) {
    for (Turtle turtle: turtles) turtle.reset(INITIAL_TURTLE_STATE);
  }

  // check if turtle is within the animation window after a step
  protected static boolean checkInBound(Point position) throws InvalidPositionException {
    // TODO
     return false;
  }

  private List<TurtleState> prepareMoveInterStates(double distance) {
    List<TurtleState> interStates = new ArrayList<>();
    double fps = 1 / this.frameDuration;
    double currPos = 0;
    double dPos = distance / fps;
    for (int i = 0; i < fps; i++) {
      currPos += dPos;
      TurtleState state = new TurtleState(new Point(0,0),currPos);
      interStates.add(state);
    }

    return interStates;
  }

  private List<TurtleState> prepareAngelInterStates(double angleChange) {
    List<TurtleState> interStates = new ArrayList<>();
    double fps = 1 / this.frameDuration;
    double currAngle = 0;
    double dAngle = angleChange / fps;
    for (int i = 0; i < fps; i++) {
      currAngle += dAngle;
      TurtleState state = new TurtleState(new Point(0,0),currAngle);
      interStates.add(state);
    }

    return interStates;
  }

}
