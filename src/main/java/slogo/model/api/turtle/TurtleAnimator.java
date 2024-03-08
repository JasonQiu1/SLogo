package slogo.model.api.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.turtleutil.TurtleGeometry;

public class TurtleAnimator {

  private static final TurtleState INITIAL_TURTLE_STATE = new TurtleState(new Point(0.0, 0.0), 0.0);
  // get from resource file
  public static final double X_MIN = -150; // get from resource file
  public static final double X_MAX = 150; // get from resource file
  public static final double Y_MIN = -150; // get from resource file
  public static final double Y_MAX = 150; // get from resource file
  private static final double MAX_SPEED = 10; // get from resource file
  private static final double MIN_SPEED = 0; // get from resource file
  public final double STANDARD_FPS = 24.0; // standard FPS for animations is 24 -  get from resource file
  private static final double DEFAULT_PIXELS_PER_SECOND = 50.0; //  get from resource file
  private static final double DEFAULT_SPEED = 1.0; //  get from resource file
  private static final double DEFAULT_GRAPHICS_SCALING_FACTOR = 1.0; //  get from resource file
  private double graphicsScalingFactor;
  private double speed;
  private double pixelsPerSecond;
  private final Map<Integer,Integer> CURRENT_POINT_IN_INTERMEDIATE_STATES = new HashMap<>();
  private int numIntermediateStates;
  private Map<Integer, List<TurtleState>> intermediateStates;
  // WINDOW mode: The turtle can move past the edges of the screen, unbounded.
  public static final String WINDOW_MODE_KEY = "window"; // get from resource file
  // FENCE mode: If the turtle attempts to move past the edge of the screen it will stop
  public static final String FENCE_MODE_KEY = "fence"; // get from resource file
  // WRAP mode: If the turtle moves off the edge of the screen it will continue on the other side
  // . (default)
  public static final String WRAP_MODE_KEY = "wrap"; // get from resource file
  public static String mode = WRAP_MODE_KEY;

  public TurtleAnimator() {
    this.graphicsScalingFactor = DEFAULT_GRAPHICS_SCALING_FACTOR;
    this.speed = DEFAULT_SPEED;
    this.intermediateStates = new HashMap<>();
    this.pixelsPerSecond = DEFAULT_PIXELS_PER_SECOND * DEFAULT_SPEED;
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
    if (speed >= MAX_SPEED) {
      pixelsPerSecond = -1;
    } else if (speed <= MIN_SPEED) {
      pixelsPerSecond = 1;
    } else {
      pixelsPerSecond = DEFAULT_PIXELS_PER_SECOND * speed;
    }
  }

  // return the list of TurtleState needed to smoothly move the turtle for a given TurtleStep
  public void animateStep(Map<Integer, List<TurtleStep>> eachTurtlesStep) {
    for (int turtleId : eachTurtlesStep.keySet()) {
      CURRENT_POINT_IN_INTERMEDIATE_STATES.putIfAbsent(turtleId, 0);
      List<TurtleStep> interSteps = eachTurtlesStep.get(turtleId);
      for (TurtleStep step : interSteps) {
        this.intermediateStates.putIfAbsent(turtleId, new ArrayList<>());
        List<TurtleState> interStates = getAngleInterStates(step.initialState(), step.changeInAngle());
        this.intermediateStates.get(turtleId).addAll(interStates);
        interStates = getMoveInterStates(step.initialState(), step.changeInPosition());
        this.intermediateStates.get(turtleId).addAll(interStates);
      }
    }
  }

  public Map<Integer, TurtleState> nextFrame() {
    Map<Integer, TurtleState> frames = new HashMap<>();
    for (Integer turtleId : intermediateStates.keySet()) {
      int currentPointInIntermediateStates = CURRENT_POINT_IN_INTERMEDIATE_STATES.get(turtleId);
      if (currentPointInIntermediateStates < this.numIntermediateStates) {
        List<TurtleState> states = intermediateStates.get(turtleId);
        frames.put(turtleId, states.get(currentPointInIntermediateStates));
        CURRENT_POINT_IN_INTERMEDIATE_STATES.put(turtleId, currentPointInIntermediateStates+1);
      }
    }
    return frames;
  }

  public Map<Integer, TurtleState> previousFrame() {
    Map<Integer, TurtleState> frames = new HashMap<>();
    for (Integer turtleId : intermediateStates.keySet()) {
      int currentPointInIntermediateStates = this.CURRENT_POINT_IN_INTERMEDIATE_STATES.get(turtleId)-2;
      if (currentPointInIntermediateStates >= 0) {
        List<TurtleState> states = intermediateStates.get(turtleId);
        frames.put(turtleId, states.get(currentPointInIntermediateStates));
        this.CURRENT_POINT_IN_INTERMEDIATE_STATES.put(turtleId, currentPointInIntermediateStates+1);
      }
    }
    return frames;
  }

  // reset frame to the beginning
  public Map<Integer, TurtleState> resetFrame() {
    for (Integer turtleId : intermediateStates.keySet()) {
      CURRENT_POINT_IN_INTERMEDIATE_STATES.put(turtleId, 0);
    }
    return nextFrame();
  }

  // reset frame back a given number of frames
  public Map<Integer, TurtleState> resetFrame(int frames) {
    for (Integer turtleId : intermediateStates.keySet()) {
      int currentPointInIntermediateStates = CURRENT_POINT_IN_INTERMEDIATE_STATES.get(turtleId)-frames;
      CURRENT_POINT_IN_INTERMEDIATE_STATES.put(turtleId, currentPointInIntermediateStates);
    }
    return nextFrame();
  }

  private List<TurtleState> getMoveInterStates(TurtleState initState, Vector posChange) {
    List<TurtleState> interStates = new ArrayList<>();
    double pixels = posChange.getMagnitude(); // pixels / pixels/s * frames/s
    TurtleState currState = new TurtleState(initState.position(), initState.heading());
    double totalFrames = pixels / this.pixelsPerSecond * STANDARD_FPS;

    Vector posChangePerFrame =
        new Vector(posChange.getDx() / totalFrames, posChange.getDy() / totalFrames);

    for (int i = 0; i < totalFrames; i++) {
      Point newPos = TurtleGeometry.calculateFinalPosition(currState.position(), posChangePerFrame);
      currState = new TurtleState(newPos, initState.heading());
      interStates.add(currState);
    }

    this.numIntermediateStates += interStates.size();

    return interStates;
  }

  private List<TurtleState> getAngleInterStates(TurtleState initState, double angleChange) {
    List<TurtleState> interStates = new ArrayList<>();
    double currAngle = initState.heading();
    double totalFrames = Math.abs(angleChange) / this.pixelsPerSecond * STANDARD_FPS;
    double angleChangePerFrame = angleChange / totalFrames;

    for (int i = 0; i < totalFrames; i++) {
      currAngle += angleChangePerFrame;
      TurtleState state = new TurtleState(initState.position(), currAngle);
      interStates.add(state);
    }

    this.numIntermediateStates += interStates.size();

    return interStates;
  }

}
