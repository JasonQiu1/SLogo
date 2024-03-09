package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.math.Point;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Vector;

/**
 * Represents a turtle object that can move around a 2D space.
 *
 * @author Judy He
 */
public class Turtle {

  private int id;
  private TurtleState currentState;
  private ModeStrategy modeStrategy = new WrapModeStrategy();
  private final List<TurtleStepExtended> stepHistory;
  private int currentPointInStepHistory;
  private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.model.";
  private final ResourceBundle errorResourceBundle = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + "ErrorsEnglish");

  /**
   * Constructs a new turtle with the given ID.
   *
   * @param id the ID of the turtle
   */
  public Turtle(int id) {
    this.id = id;
    this.currentState = TurtleAnimatorImplementation.INITIAL_TURTLE_STATE;
    this.stepHistory = new ArrayList<>();
    this.currentPointInStepHistory = 0;
  }

  /**
   * Gets the ID of the turtle.
   *
   * @return the ID of the turtle
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the current state of the turtle.
   *
   * @return the current state of the turtle
   */
  public TurtleState getCurrentState() {
    return currentState;
  }

  /**
   * Sets the current state of the turtle.
   *
   * @param currentState the new current state of the turtle
   */
  public void setCurrentState(TurtleState currentState) {
    this.currentState = currentState;
  }

  public void setModeStrategy(ModeStrategy moveStrategy) {
    this.modeStrategy = moveStrategy;
  }

  /**
   * Gets the history of turtle steps back a certain length.
   *
   * @param maxLength the number of steps in the history to go back
   * @return the list of turtle steps
   */
  public List<TurtleStep> getStepHistory(int maxLength) {
    List<TurtleStep> history = new ArrayList<>();
    int startInd = 0;
    if (maxLength < this.stepHistory.size()) {
      startInd = currentPointInStepHistory - 1;
      int count = 0;
      while (count != maxLength) {
        while (startInd - 1 >= 0 && this.stepHistory.get(startInd - 1)
            .crossBorderIntermediateStep()) {
          startInd--;
        }
        count++;
      }
    }

    if (startInd == currentPointInStepHistory - 1) {
      startInd = currentPointInStepHistory - maxLength;
    }

    for (int i = startInd; i < this.stepHistory.size(); i++) {
      history.add(this.stepHistory.get(i).turtleStep());
    }
    return history;
  }

  /**
   * Turns the turtle towards a specified point.
   *
   * @param point the point to turn towards
   * @return the step representing the turn
   */
  public TurtleStep turnTowards(Point point) {
    Vector vectorToPoint = getVectorBetweenTwoPoints(currentState.position(), point);
    double angleChange = vectorToPoint.getDirection() - currentState.heading();
    TurtleStep step = new TurtleStep(currentState, new Vector(0, 0), angleChange);
    updateTurtleState(step);
    return step;
  }

  /**
   * Moves the turtle forward by one step.
   *
   * @return the steps representing the forward movement
   */
  public List<TurtleStep> stepForward() {
    List<TurtleStep> steps = new ArrayList<>();
    while (stepHistory.get(currentPointInStepHistory).crossBorderIntermediateStep()) {
      steps.add(updateTurtleStateWhenSteppingForward());
    }
    steps.add(updateTurtleStateWhenSteppingForward());
    return steps;
  }

  private TurtleStep updateTurtleStateWhenSteppingForward() {
    TurtleStep forwardStep = this.stepHistory.get(currentPointInStepHistory).turtleStep();
    updateTurtleState(forwardStep);
    this.currentPointInStepHistory++;
    return forwardStep;
  }

  /**
   * Moves the turtle backward by one step.
   *
   * @return the steps representing the backward movement
   */
  public List<TurtleStep> stepBack() {
    List<TurtleStep> steps = new ArrayList<>();
    steps.add(updateTurtleStateWhenSteppingBackward());
    while (this.stepHistory.get(currentPointInStepHistory).crossBorderIntermediateStep()) {
      steps.add(updateTurtleStateWhenSteppingBackward());
    }
    return steps;
  }

  private TurtleStep updateTurtleStateWhenSteppingBackward() {
    this.currentPointInStepHistory--;
    TurtleStep currStep = this.stepHistory.get(currentPointInStepHistory).turtleStep();
    TurtleStep backwardStep;

    if (currStep.changeInAngle() != 0) {
      backwardStep = new TurtleStep(this.currentState, currStep.changeInPosition(),
          -1 * currStep.changeInAngle());
    } else {
      Vector oldPosChange = currStep.changeInPosition();
      Vector updatedPosChange = new Vector(oldPosChange.dx() * -1, oldPosChange.dy() * -1);
      backwardStep = new TurtleStep(this.currentState, updatedPosChange, currStep.changeInAngle());
    }

    updateTurtleState(backwardStep);

    return backwardStep;
  }

  protected void updateTurtleState(TurtleStep step) {
    // update turtle state
    Point finalPos = calculateFinalPosition(this.currentState.position(),
        step.changeInPosition());
    double finalHeading = this.currentState.heading() + step.changeInAngle();
    this.currentState = new TurtleState(finalPos, finalHeading);
  }

  /**
   * Sets the heading of the turtle to the specified angle.
   *
   * @param degrees the angle in degrees
   * @return the step representing the heading change
   */
  public TurtleStep setHeading(double degrees) {
    double angleChange = this.getAngleChange(this.currentState.heading(), degrees);
    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), angleChange);
    updateHistory(step, false);
    updateTurtleState(step);

    return step;
  }

  /**
   * Sets the position of the turtle to the specified point.
   *
   * @param position the new position of the turtle
   * @return the step representing the position change
   * @throws InvalidPositionException if the position is invalid
   */
  public TurtleStep setXY(Point position) throws InvalidPositionException {

    if (position.getX() > TurtleAnimatorImplementation.X_MAX
        || position.getY() > TurtleAnimatorImplementation.Y_MAX
        || position.getX() < TurtleAnimatorImplementation.X_MIN
        || position.getY() < TurtleAnimatorImplementation.Y_MIN) {
      throw new InvalidPositionException(errorResourceBundle.getString("INVALID_POSITION"));
    }

    Vector posChange =
        this.getVectorBetweenTwoPoints(this.currentState.position(), position);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateHistory(step, false);
    updateTurtleState(step);

    return step;
  }

  /**
   * Rotates the turtle by the specified angle.
   *
   * @param angle the angle to rotate by
   * @return the step representing the rotation
   */
  public TurtleStep rotate(double angle) {
    double referenceAngle = angle % 360;

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), referenceAngle);
    updateHistory(step, false);
    updateTurtleState(step);
    return step;

  }

  /**
   * Moves the turtle by the specified distance.
   *
   * @param distance the distance to move
   * @return the intermediate steps representing the movement
   */
  public List<TurtleStep> move(double distance) {
    if (modeStrategy == null) {
      return Collections.emptyList();
    }
    return modeStrategy.move(this, distance);
  }

  /**
   * Resets the turtle to its initial state.
   *
   * @param initialState the initial state of the turtle
   */
  public void reset(TurtleState initialState) {
    this.currentPointInStepHistory = 0;
    this.currentState = initialState;
  }

  protected void updateHistory(TurtleStep newStep, boolean isCrossBorder) {
    if (currentPointInStepHistory != stepHistory.size()) {
      stepHistory.add(currentPointInStepHistory + 1,
          new TurtleStepExtended(newStep, isCrossBorder));
    } else {
      stepHistory.add(new TurtleStepExtended(newStep, isCrossBorder));
    }
    currentPointInStepHistory++;
  }

  protected static double calculateYComponentGivenXComponentAngle(double x, double angle) {
    double newMagnitude = x / Math.sin(Math.toRadians(angle));
    return newMagnitude * Math.cos(Math.toRadians(angle));
  }

  protected static double calculateXComponentGivenYComponentAngle(double y, double angle) {
    double newMagnitude = y / Math.cos(Math.toRadians(angle));
    return newMagnitude * Math.sin(Math.toRadians(angle));
  }

  protected static double calculateXComponent(double magnitude, double angle) {
    return magnitude * Math.sin(Math.toRadians(angle));
  }

  protected static double calculateYComponent(double magnitude, double angle) {
    return magnitude * Math.cos(Math.toRadians(angle));
  }

  protected static Vector getVectorBetweenTwoPoints(Point p1, Point p2) {
    double dx = p2.getX() - p1.getX();
    double dy = p2.getY() - p1.getY();
    return new Vector(dx, dy);
  }

  protected static double getAngleChange(double a1, double a2) {
    return a2 - a1;
  }

  protected boolean isOutXMax(double dx) {
    return currentState.position().getX() + dx > TurtleAnimatorImplementation.X_MAX;
  }

  protected boolean isOutXMin(double dx) {
    return currentState.position().getX() + dx < TurtleAnimatorImplementation.X_MIN;
  }

  protected boolean isOutYMax(double dy) {
    return currentState.position().getY() + dy > TurtleAnimatorImplementation.Y_MAX;
  }

  protected boolean isOutYMin(double dy) {
    return currentState.position().getY() + dy < TurtleAnimatorImplementation.Y_MIN;
  }

  protected boolean isWithinBounds(double dx, double dy) {
    return currentState.position().getX() + dx <= TurtleAnimatorImplementation.X_MAX
        && currentState.position().getX() + dx >= TurtleAnimatorImplementation.X_MIN
        && currentState.position().getY() + dy <= TurtleAnimatorImplementation.Y_MAX
        && currentState.position().getY() + dy >= TurtleAnimatorImplementation.Y_MIN;
  }

  protected Vector getXYComponents(double distance) {
    double dx = Turtle.calculateXComponent(distance, currentState.heading());
    double dy = Turtle.calculateYComponent(distance, currentState.heading());
    return new Vector(dx, dy);
  }

  /**
   * Calculates the final position after a movement.
   *
   * @param initialPosition the initial position of the turtle
   * @param vector          the vector representing the movement
   * @return the final position after the movement
   */
  protected static Point calculateFinalPosition(Point initialPosition, Vector vector) {
    double xFinal = initialPosition.getX() + vector.dx();
    double yFinal = initialPosition.getY() + vector.dy();

    return new Point(xFinal, yFinal);
  }

}
