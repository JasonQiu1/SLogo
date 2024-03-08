package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.math.Point;
import slogo.model.api.turtle.TurtleAnimator;
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
    this.currentState = TurtleAnimator.getInitialTurtleState();
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

  private void updateTurtleState(TurtleStep step) {
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

    if (position.getX() > TurtleAnimator.X_MAX || position.getY() > TurtleAnimator.Y_MAX
        || position.getX() < TurtleAnimator.X_MIN || position.getY() < TurtleAnimator.Y_MIN) {
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
    double dx = this.calculateXComponent(distance, this.currentState.heading());
    double dy = this.calculateYComponent(distance, this.currentState.heading());
    List<TurtleStep> intermediateSteps = new ArrayList<>();
    wrapMove(dx, dy, intermediateSteps);
    return intermediateSteps;
  }

  private void wrapMove(double dx, double dy, List<TurtleStep> intermediateSteps) {
    if (isWithinBounds(dx, dy)) {
      intermediateSteps.add(normalMove(dx, dy));
      return;
    }

    Vector interPosChange = findInterPosChange(dx, dy);
    TurtleStep step = new TurtleStep(currentState, interPosChange, 0);
    updateHistory(step, true);
    intermediateSteps.add(step);

    Point finalPos = calculateFinalPosition(currentState.position(), interPosChange);
    finalPos.setX(finalPos.getX() == TurtleAnimator.X_MAX ? TurtleAnimator.X_MIN
        : finalPos.getX() == TurtleAnimator.X_MIN ? TurtleAnimator.X_MAX : finalPos.getX());
    finalPos.setY(finalPos.getY() == TurtleAnimator.Y_MAX ? TurtleAnimator.Y_MIN
        : finalPos.getY() == TurtleAnimator.Y_MIN ? TurtleAnimator.Y_MAX : finalPos.getY());

    currentState = new TurtleState(finalPos, currentState.heading());

    wrapMove(dx - interPosChange.dx(), dy - interPosChange.dy(), intermediateSteps);
  }

  private Vector findInterPosChange(double dx, double dy) {

    double interDx = isOutXMax(dx) ? TurtleAnimator.X_MAX - currentState.position().getX() :
        isOutXMin(dx) ? TurtleAnimator.X_MIN - currentState.position().getX() : 0.0;

    double interDy = calculateYComponentGivenXComponentAngle(interDx, currentState.heading());

    if (isOutYMax(dy) || interDy > TurtleAnimator.Y_MAX) {
      interDy = TurtleAnimator.Y_MAX - currentState.position().getY();
    } else if (isOutYMin(dy) || interDy < TurtleAnimator.Y_MIN) {
      interDy = TurtleAnimator.Y_MIN - currentState.position().getY();
    }

    interDx = this.calculateXComponentGivenYComponentAngle(interDy, this.currentState.heading());

    return new Vector(interDx, interDy);
  }

  private boolean isOutXMax(double dx) {
    return this.currentState.position().getX() + dx > TurtleAnimator.X_MAX;
  }

  private boolean isOutXMin(double dx) {
    return this.currentState.position().getX() + dx < TurtleAnimator.X_MIN;
  }

  private boolean isOutYMax(double dy) {
    return this.currentState.position().getY() + dy > TurtleAnimator.Y_MAX;
  }

  private boolean isOutYMin(double dy) {
    return this.currentState.position().getY() + dy < TurtleAnimator.Y_MIN;
  }

  private boolean isWithinBounds(double dx, double dy) {
    return this.currentState.position().getX() + dx <= TurtleAnimator.X_MAX
        && this.currentState.position().getX() + dx >= TurtleAnimator.X_MIN
        && this.currentState.position().getY() + dy <= TurtleAnimator.Y_MAX
        && this.currentState.position().getY() + dy >= TurtleAnimator.Y_MIN;
  }

  private TurtleStep normalMove(double dx, double dy) {
    Vector posChange = new Vector(dx, dy);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateHistory(step, false);
    updateTurtleState(step);
    return step;
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

  private void updateHistory(TurtleStep newStep, boolean isCrossBorder) {
    if (currentPointInStepHistory != stepHistory.size()) {
      stepHistory.add(currentPointInStepHistory + 1,
          new TurtleStepExtended(newStep, isCrossBorder));
    } else {
      stepHistory.add(new TurtleStepExtended(newStep, isCrossBorder));
    }
    currentPointInStepHistory++;
  }

  private double calculateYComponentGivenXComponentAngle(double x, double angle) {
    double newMagnitude = x / Math.sin(Math.toRadians(angle));
    return newMagnitude * Math.cos(Math.toRadians(angle));
  }

  private double calculateXComponentGivenYComponentAngle(double y, double angle) {
    double newMagnitude = y / Math.cos(Math.toRadians(angle));
    return newMagnitude * Math.sin(Math.toRadians(angle));
  }

  private double calculateXComponent(double magnitude, double angle) {
    return magnitude * Math.sin(Math.toRadians(angle));
  }

  private double calculateYComponent(double magnitude, double angle) {
    return magnitude * Math.cos(Math.toRadians(angle));
  }

  private Vector getVectorBetweenTwoPoints(Point p1, Point p2) {
    double dx = p2.getX() - p1.getX();
    double dy = p2.getY() - p1.getY();
    return new Vector(dx, dy);
  }

  private double getAngleChange(double a1, double a2) {
    return a2 - a1;
  }

  /**
   * Calculates the final position after a movement.
   *
   * @param initialPosition the initial position of the turtle
   * @param vector          the vector representing the movement
   * @return the final position after the movement
   */
  public static Point calculateFinalPosition(Point initialPosition, Vector vector) {
    double xFinal = initialPosition.getX() + vector.dx();
    double yFinal = initialPosition.getY() + vector.dy();

    return new Point(xFinal, yFinal);
  }

}
