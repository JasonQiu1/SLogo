package slogo.model.api.turtle;

import java.util.*;
import slogo.model.turtleutil.Turtle;
import slogo.model.api.exception.turtle.InvalidPositionException;

public class TurtleAnimator {
  private static final TurtleState INITIAL_TURTLE_STATE = new TurtleState(new Point(0.0,0.0), 0.0); // get from resource file
  public static final double X_MIN = -100; // get from resource file
  public static final double X_MAX = 100; // get from resource file
  public static final double Y_MIN = -100; // get from resource file
  public static final double Y_MAX = 100; // get from resource file
  private static final double SECOND_DELAY = 1.0;
  private double graphicsScalingFactor;
  private double speed;
  private static double frameDuration; // 1s --> dx = 1, dy = 1
  private static Map<Integer, List<TurtleState>> intermediateStates;
  // WINDOW mode: The turtle can move past the edges of the screen, unbounded.
  public static final String WINDOW_MODE_KEY = "window"; // get from resource file
  // FENCE mode: If the turtle attempts to move past the edge of the screen it will stop
  public static final String FENCE_MODE_KEY = "fence"; // get from resource file
  // WRAP mode: If the turtle moves off the edge of the screen it will continue on the other side. (default)
  public static final String WRAP_MODE_KEY = "wrap"; // get from resource file
  public static String mode;

  public TurtleAnimator() {
    this.graphicsScalingFactor = 1;
    this.speed = 1;
    this.frameDuration = 1.0 / (speed * SECOND_DELAY);
    this.intermediateStates = new HashMap<>();
    // set default mode: wrap
    mode = WRAP_MODE_KEY;
  }
  public Map<Integer, List<TurtleState>> getIntermediateStates() {
    return intermediateStates;
  }

  public static TurtleState getInitialTurtleState() {
    return INITIAL_TURTLE_STATE;
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
    // Calculate the duration for the KeyFrame
    this.frameDuration = 1.0 / (speed * SECOND_DELAY);
    this.speed = speed;
  }

  // calculate the list of TurtleState needed to smoothly move the turtle for its given TurtleStep
  public static void animateSteps(Map<Integer, List<TurtleStep>> eachTurtlesStep) {

    for (int turtleId: eachTurtlesStep.keySet()) {
      List<TurtleStep> interSteps = eachTurtlesStep.get(turtleId);
      for (TurtleStep step: interSteps) {
        // given: initial state, changeInPos, changeInAngle
        if (step.changeInAngle() != 0) {
          intermediateStates.put(turtleId, getAngleInterStates(step.initialState().heading(), step.changeInAngle()));
        }
        else {
          intermediateStates.put(turtleId, getMoveInterStates(step.initialState().position(), step.changeInAngle()));
        }
      }
    }


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

  private static List<TurtleState> getMoveInterStates(Point initialPosition, double distance) {
    List<TurtleState> interStates = new ArrayList<>();
    double fps = 1 / frameDuration;
    double currPos = 0;
    double dPos = distance / fps;
    for (int i = 0; i < fps; i++) {
      currPos += dPos;
      TurtleState state = new TurtleState(new Point(0,0),currPos);
      interStates.add(state);
    }

    return interStates;
  }

  private static List<TurtleState> getAngleInterStates(double initialAngle, double angleChange) {
    List<TurtleState> interStates = new ArrayList<>();
    double fps = 1 / frameDuration;
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
