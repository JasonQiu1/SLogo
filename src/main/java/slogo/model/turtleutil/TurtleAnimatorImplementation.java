package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;

/**
 * The TurtleAnimatorImplementation class implements the TurtleAnimator interface to manage the
 * animation of turtles.
 *
 * @author Judy He
 */
public class TurtleAnimatorImplementation implements TurtleAnimator {

  private static final ResourceBundle configResourceBundle = ResourceBundle.getBundle(
      "slogo.Configuration");

  // Constants for default turtle settings
  private static final double TURTLE_INIT_X = parseConfigDouble("TURTLE_INIT_X");
  private static final double TURTLE_INIT_Y = parseConfigDouble("TURTLE_INIT_Y");
  private static final double TURTLE_INIT_HEADING = parseConfigDouble("TURTLE_INIT_HEADING");
  public static final TurtleState INITIAL_TURTLE_STATE = new TurtleState(
      new Point(TURTLE_INIT_X, TURTLE_INIT_Y), TURTLE_INIT_HEADING);
  public static final double X_MIN = parseConfigDouble("X_MIN");
  public static final double X_MAX = parseConfigDouble("X_MAX");
  public static final double Y_MIN = parseConfigDouble("Y_MIN");
  public static final double Y_MAX = parseConfigDouble("Y_MAX");
  private static final double MAX_SPEED = parseConfigDouble("MAX_SPEED");
  private static final double MIN_SPEED = parseConfigDouble("MIN_SPEED");
  public static final double STANDARD_FPS = parseConfigDouble("STANDARD_FPS");
  private static final double DEFAULT_PIXELS_PER_SECOND = parseConfigDouble(
      "DEFAULT_PIXELS_PER_SECOND");
  private static final double DEFAULT_SPEED = parseConfigDouble("DEFAULT_SPEED");
  private static final double DEFAULT_GRAPHICS_SCALING_FACTOR = parseConfigDouble(
      "DEFAULT_GRAPHICS_SCALING_FACTOR");
  public static final String WINDOW_MODE_KEY = parseConfigString("WINDOW_MODE");
  public static final String FENCE_MODE_KEY = parseConfigString("FENCE_MODE");
  public static final String WRAP_MODE_KEY = parseConfigString("WRAP_MODE");

  // Instance variables
  private double graphicsScalingFactor = DEFAULT_GRAPHICS_SCALING_FACTOR;
  private double speed = DEFAULT_SPEED;
  private double pixelsPerSecond = DEFAULT_PIXELS_PER_SECOND * DEFAULT_SPEED;
  private Map<Integer, Integer> currentPointInIntermediateStates = new HashMap<>();
  private Map<Integer, List<TurtleState>> intermediateStates = new HashMap<>();
  private int numIntermediateStates;
  public static String mode = WRAP_MODE_KEY;

  /**
   * Constructs a TurtleAnimator object.
   */
  public TurtleAnimatorImplementation() {

  }

  /**
   * Returns the intermediate states of all turtles.
   *
   * @return a map of turtle IDs to lists of turtle states representing intermediate animation
   * states
   */
  @Override
  public Map<Integer, List<TurtleState>> getIntermediateStates() {
    return this.intermediateStates;
  }

  /**
   * Returns the initial state of the turtle.
   *
   * @return the initial state of the turtle
   */
  @Override
  public TurtleState getInitialTurtleState() {
    return INITIAL_TURTLE_STATE;
  }

  /**
   * Returns the graphics scaling factor.
   *
   * @return the graphics scaling factor
   */
  @Override
  public double getGraphicsScalingFactor() {
    return this.graphicsScalingFactor;
  }

  /**
   * Returns the speed of the turtle.
   *
   * @return the speed of the turtle
   */
  @Override
  public double getSpeed() {
    return this.speed;
  }

  /**
   * Sets the speed of the turtle.
   *
   * @param speed the new speed of the turtle
   */
  @Override
  public void setSpeed(double speed) {
    this.speed = speed;
    updatePixelsPerSecond();
  }

  /**
   * Animates the movement of turtles based on given steps.
   *
   * @param eachTurtlesStep a map of turtle IDs to lists of turtle steps to animate
   */
  @Override
  public void animateStep(Map<Integer, List<TurtleStep>> eachTurtlesStep) {
    eachTurtlesStep.forEach((turtleId, turtleSteps) -> {
      currentPointInIntermediateStates.putIfAbsent(turtleId, 0);
      turtleSteps.forEach(step -> {
        intermediateStates.putIfAbsent(turtleId, new ArrayList<>());
        intermediateStates.get(turtleId).addAll(getInterStates(step));
      });
    });
  }

  private List<TurtleState> getInterStates(TurtleStep step) {
    List<TurtleState> interStates = new ArrayList<>();
    interStates.addAll(getMoveInterStates(step.initialState(), step.changeInPosition()));
    interStates.addAll(getAngleInterStates(step.initialState(), step.changeInAngle()));
    return interStates;
  }

  /**
   * Calculates the next animation frame.
   *
   * @return a map of turtle IDs to the next turtle state in the animation
   */
  @Override
  public Map<Integer, TurtleState> nextFrame() {
    Map<Integer, TurtleState> frames = new HashMap<>();
    intermediateStates.forEach((turtleId, states) -> {
      int currentPoint = currentPointInIntermediateStates.get(turtleId);
      if (currentPoint < numIntermediateStates) {
        frames.put(turtleId, states.get(currentPoint));
        currentPointInIntermediateStates.put(turtleId, currentPoint + 1);
      }
    });
    return frames;
  }

  /**
   * Calculates the previous animation frame.
   *
   * @return a map of turtle IDs to the previous turtle state in the animation
   */
  @Override
  public Map<Integer, TurtleState> previousFrame() {
    Map<Integer, TurtleState> frames = new HashMap<>();
    intermediateStates.forEach((turtleId, states) -> {
      int currentPoint = currentPointInIntermediateStates.get(turtleId) - 2;
      if (currentPoint < numIntermediateStates) {
        frames.put(turtleId, states.get(currentPoint));
        currentPointInIntermediateStates.put(turtleId, currentPoint + 1);
      }
    });
    return frames;
  }

  /**
   * Resets the animation frame to the beginning.
   *
   * @return a map of turtle IDs to their initial turtle state
   */
  @Override
  public Map<Integer, TurtleState> resetFrame() {
    intermediateStates.keySet()
        .forEach(turtleId -> currentPointInIntermediateStates.put(turtleId, 0));
    return nextFrame();
  }

  /**
   * Resets the animation frame back a given number of frames.
   *
   * @param frames the number of frames to reset back
   * @return a map of turtle IDs to the turtle states after resetting
   */
  @Override
  public Map<Integer, TurtleState> resetFrame(int frames) {
    intermediateStates.keySet().forEach(turtleId -> currentPointInIntermediateStates.put(turtleId,
        currentPointInIntermediateStates.get(turtleId) - frames));
    return nextFrame();
  }

  private List<TurtleState> getMoveInterStates(TurtleState initState, Vector posChange) {
    List<TurtleState> interStates = new ArrayList<>();

    double pixels = posChange.getMagnitude();
    TurtleState currState = new TurtleState(initState.position(), initState.heading());
    double totalFrames = pixels / pixelsPerSecond * STANDARD_FPS;
    Vector posChangePerFrame = new Vector(posChange.dx() / totalFrames,
        posChange.dy() / totalFrames);

    for (int i = 0; i < totalFrames; i++) {
      Point newPos = Turtle.calculateFinalPosition(currState.position(), posChangePerFrame);
      currState = new TurtleState(newPos, initState.heading());
      interStates.add(currState);
    }

    numIntermediateStates += interStates.size();
    return interStates;
  }

  private List<TurtleState> getAngleInterStates(TurtleState initState, double angleChange) {
    List<TurtleState> interStates = new ArrayList<>();

    double currAngle = initState.heading();
    double totalFrames = Math.abs(angleChange) / pixelsPerSecond * STANDARD_FPS;
    double angleChangePerFrame = angleChange / totalFrames;

    for (int i = 0; i < totalFrames; i++) {
      currAngle += angleChangePerFrame;
      TurtleState state = new TurtleState(initState.position(), currAngle);
      interStates.add(state);
    }

    numIntermediateStates += interStates.size();
    return interStates;
  }

  private static double parseConfigDouble(String key) {
    return Double.parseDouble(configResourceBundle.getString(key));
  }

  private static String parseConfigString(String key) {
    return configResourceBundle.getString(key);
  }

  private void updatePixelsPerSecond() {
    if (speed >= MAX_SPEED) {
      pixelsPerSecond = -1;
    } else if (speed <= MIN_SPEED) {
      pixelsPerSecond = 0.5;
    } else {
      pixelsPerSecond = DEFAULT_PIXELS_PER_SECOND * speed;
    }
  }

}
