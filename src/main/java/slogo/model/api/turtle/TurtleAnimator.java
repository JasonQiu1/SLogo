package slogo.model.api.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  public final double STANDARD_FPS = 24.0;
  // standard FPS for animations is 24 -  get from resource file
  private static final double DEFAULT_SECONDS_PER_STEP = 1.0; //  get from resource file
  private double graphicsScalingFactor;
  private double speed;
  private double secondsPerStep;
  private int currentPointInIntermediateStates;
  private int numIntermediateStates;
  private final Map<Integer, List<TurtleState>> intermediateStates;
  // WINDOW mode: The turtle can move past the edges of the screen, unbounded.
  public static final String WINDOW_MODE_KEY = "window"; // get from resource file
  // FENCE mode: If the turtle attempts to move past the edge of the screen it will stop
  public static final String FENCE_MODE_KEY = "fence"; // get from resource file
  // WRAP mode: If the turtle moves off the edge of the screen it will continue on the other side
  // . (default)
  public static final String WRAP_MODE_KEY = "wrap"; // get from resource file
  public static String mode;

  public TurtleAnimator() {
    this.graphicsScalingFactor = 1;
    this.speed = 1;
    intermediateStates = new HashMap<>();
    secondsPerStep = DEFAULT_SECONDS_PER_STEP;
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
      secondsPerStep = 0;
    } else if (speed < MIN_SPEED) {
      secondsPerStep = 0.25;
    } else {
      secondsPerStep = DEFAULT_SECONDS_PER_STEP / speed;
    }
  }

  // return the list of TurtleState needed to smoothly move the turtle for a given TurtleStep
  public void animateStep(Map<Integer, List<TurtleStep>> eachTurtlesStep) {
    for (int turtleId : eachTurtlesStep.keySet()) {
      List<TurtleStep> interSteps = eachTurtlesStep.get(turtleId);
      for (TurtleStep step : interSteps) {
        if (step.changeInAngle() != 0) {
          List<TurtleState> interStates = getAngleInterStates(step.initialState(), step.changeInAngle());
          this.intermediateStates.putIfAbsent(turtleId, new ArrayList<>());
          this.intermediateStates.get(turtleId).addAll(interStates);
        } else {
          List<TurtleState> interStates = getMoveInterStates(step.initialState(), step.changeInPosition());
          this.intermediateStates.putIfAbsent(turtleId, new ArrayList<>());
          this.intermediateStates.get(turtleId).addAll(interStates);
        }
      }
    }
  }

  public Map<Integer, TurtleState> nextFrame() {
    Map<Integer, TurtleState> frames = new HashMap<>();
    if (currentPointInIntermediateStates == this.numIntermediateStates) {
      return frames;
    }
    for (Integer turtleId : intermediateStates.keySet()) {
      List<TurtleState> states = intermediateStates.get(turtleId);
      frames.put(turtleId, states.get(currentPointInIntermediateStates));
    }
    currentPointInIntermediateStates++;
    return frames;
  }

  public Map<Integer, TurtleState> previousFrame() {
    Map<Integer, TurtleState> frames = new HashMap<>();
    currentPointInIntermediateStates -= 2;
    if (currentPointInIntermediateStates < 0) {
      return frames;
    }
    for (Integer turtleId : intermediateStates.keySet()) {
      List<TurtleState> states = intermediateStates.get(turtleId);
      frames.put(turtleId, states.get(currentPointInIntermediateStates));
    }
    currentPointInIntermediateStates++;
    return frames;
  }

  // reset frame for replying animation and changing speed during animation
  public Map<Integer, TurtleState> resetFrame(int frames) {
    currentPointInIntermediateStates -= frames;
    return nextFrame();
  }

  private List<TurtleState> getMoveInterStates(TurtleState initState, Vector posChange) {
    List<TurtleState> interStates = new ArrayList<>();
    TurtleState currState = new TurtleState(initState.position(), initState.heading());
    double totalFrames = secondsPerStep * STANDARD_FPS;

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
    double totalFrames = secondsPerStep * STANDARD_FPS;
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
