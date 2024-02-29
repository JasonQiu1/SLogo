package slogo.model.api.turtle;

import java.util.*;
import slogo.model.turtleutil.Turtle;
import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.turtleutil.TurtleGeometry;

public class TurtleAnimator {
  private static final TurtleState INITIAL_TURTLE_STATE = new TurtleState(new Point(0.0,0.0), 0.0); // get from resource file
  public static final double X_MIN = -100; // get from resource file
  public static final double X_MAX = 100; // get from resource file
  public static final double Y_MIN = -100; // get from resource file
  public static final double Y_MAX = 100; // get from resource file
  private static final int MAX_SPEED = 10; // get from resource file
  private static final int MIN_SPEED = 0; // get from resource file
  private static final double STANDARD_FPS = 24.0; // standard FPS for animations is 24
  private static final double DEFAULT_SECONDS_PER_FRAME = 1.0; // standard FPS for animations is 24
  private double graphicsScalingFactor;
  private double speed;
  private static double secondsPerStep;
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
    intermediateStates = new HashMap<>();
    secondsPerStep = DEFAULT_SECONDS_PER_FRAME;
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
    this.speed = speed;
    if (speed == MAX_SPEED) {
      secondsPerStep = 1 / STANDARD_FPS;
    }
    else if (speed == MIN_SPEED) {
      secondsPerStep = 0;
    }
    else {
      secondsPerStep = DEFAULT_SECONDS_PER_FRAME / speed;
    }
  }

  // return the list of TurtleState needed to smoothly move the turtle for a given TurtleStep
  public static Map<Integer, List<TurtleState>> animateStep(Map<Integer, List<TurtleStep>> eachTurtlesStep) {
    for (int turtleId: eachTurtlesStep.keySet()) {
      List<TurtleStep> interSteps = eachTurtlesStep.get(turtleId);
      for (TurtleStep step: interSteps) {
        // given: initial state, changeInPos, changeInAngle
        if (step.changeInAngle() != 0) {
          intermediateStates.put(turtleId, getAngleInterStates(step.initialState(), step.changeInAngle()));
        }
        else {
          intermediateStates.put(turtleId, getMoveInterStates(step.initialState(), step.changeInPosition()));
        }
      }
    }
    return intermediateStates;
  }
  // return the list of TurtleState needed to smoothly move the turtle(s) a step forward or backward in the animation
  public static Map<Integer, List<TurtleState>> doStep(List<Turtle> turtles, boolean isForward) {
    Map<Integer, List<TurtleStep>> eachTurtlesStep = new HashMap<>();
    for (Turtle turtle: turtles) {
      if (isForward) {
        eachTurtlesStep.put(turtle.getId(), turtle.stepForward());
      }
      else {
        eachTurtlesStep.put(turtle.getId(), turtle.stepBack());
      }
    }
    return animateStep(eachTurtlesStep);
  }

  // reset turtles' state
  public static Map<Integer, List<TurtleState>> resetTurtles(List<Turtle> turtles) {
    intermediateStates = new HashMap<>();
    for (Turtle turtle: turtles) {
      intermediateStates.put(turtle.getId(), List.of(INITIAL_TURTLE_STATE));
      turtle.reset(INITIAL_TURTLE_STATE);
    }
    return intermediateStates;
  }

  private static List<TurtleState> getMoveInterStates(TurtleState initState, Vector posChange) {
    List<TurtleState> interStates = new ArrayList<>();
    TurtleState currState = new TurtleState(initState.position(), initState.heading());
    double totalFrames = secondsPerStep * STANDARD_FPS;
    if (totalFrames == 0) {
      return Collections.emptyList();
    }

    Vector posChangePerFrame = new Vector(posChange.getDx() / totalFrames, posChange.getDy() / totalFrames);
    for (int i = 0; i < totalFrames; i++) {
      Point newPos = TurtleGeometry.calculateFinalPosition(currState.position(), posChangePerFrame);
      currState = new TurtleState(newPos, initState.heading());
      interStates.add(currState);
    }

    return interStates;
  }

  private static List<TurtleState> getAngleInterStates(TurtleState initState, double angleChange) {
    List<TurtleState> interStates = new ArrayList<>();
    double currAngle = initState.heading();
    double totalFrames = secondsPerStep * STANDARD_FPS;
    if (totalFrames == 0) {
      return Collections.emptyList();
    }

    double angleChangePerFrame = angleChange / totalFrames;
    for (int i = 0; i < totalFrames; i++) {
      currAngle += angleChangePerFrame;
      TurtleState state = new TurtleState(new Point(0,0),currAngle);
      interStates.add(state);
    }

    return interStates;
  }

}
