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
 * The Turtle class represents a turtle in the animation. It keeps track of the turtle's id, step
 * history and current point in history, and current state (position and heading). It updates the
 * turtle's state and returns the corresponding step that the TurtleAnimator and View can interpret
 * after a command is received
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
   * Constructor for instantiating a new Turtle
   *
   * @param id, id of turtle
   */
  public Turtle(int id) {
    this.id = id;
    this.currentState = TurtleAnimator.getInitialTurtleState();
    this.stepHistory = new ArrayList<>();
    this.currentPointInStepHistory = 0;
  }

  /**
   * Retrieve a turtle's id instance variable
   *
   * @return id of turtle
   */
  public int getId() {
    return id;
  }

  /**
   * Retrieve a turtle's current state
   *
   * @return the current state of turtle
   */
  public TurtleState getCurrentState() {
    return currentState;
  }

  /**
   * Returns the current step history of a certain length. May return more steps than maxLength due
   * to intermediate states from wrapping. maxLength limits the number of commands, not number of
   * steps.
   *
   * @param maxLength the max length of step history to return. If 0, then return entire step
   *                  history. If positive, then return the backwards history up to length.
   * @return turtle's step history
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
   * Turn turtle towards a given point
   *
   * @param point, point to turn towards
   * @return TurtleStep representing the turn took
   */
  public TurtleStep turnTowards(Point point) {

    Vector vectorToPoint =
        this.getVectorBetweenTwoPoints(this.currentState.position(), point);
    double angleChange = vectorToPoint.getDirection() - this.currentState.heading();
    double finalHeading = vectorToPoint.getDirection();

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), angleChange);
    updateHistory(step, false);
    updateTurtleState(step);
    return step;
  }

  public List<TurtleStep> stepForward() {
    List<TurtleStep> steps = new ArrayList<>();
    // check if intermediate steps exists due to border crossing
    while (this.stepHistory.get(currentPointInStepHistory).crossBorderIntermediateStep()) {
      steps.add(updateTurtleStateWhenSteppingForward());
    }

    // step forward once
    steps.add(updateTurtleStateWhenSteppingForward());

    return steps;
  }

  private TurtleStep updateTurtleStateWhenSteppingForward() {
    TurtleStep forwardStep = this.stepHistory.get(currentPointInStepHistory).turtleStep();

    updateTurtleState(forwardStep);

    this.currentPointInStepHistory++;
    return forwardStep;
  }

  public List<TurtleStep> stepBack() {
    List<TurtleStep> steps = new ArrayList<>();
    // step back once
    steps.add(updateTurtleStateWhenSteppingBackward());
    // check if intermediate steps exists due to border crossing
    while (this.stepHistory.get(currentPointInStepHistory).crossBorderIntermediateStep()) {
      steps.add(updateTurtleStateWhenSteppingBackward());
    }
    return steps;
  }

  private TurtleStep updateTurtleStateWhenSteppingBackward() {
    this.currentPointInStepHistory--;
    TurtleStep currStep = this.stepHistory.get(currentPointInStepHistory).turtleStep();
    TurtleStep backwardStep;

    // perform opposite position/heading change
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

  public TurtleStep setHeading(double degrees) {
    double angleChange = this.getAngleChange(this.currentState.heading(), degrees);
    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), angleChange);
    updateHistory(step, false);
    updateTurtleState(step);

    return step;
  }

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

  public TurtleStep rotate(double angle) {
    double referenceAngle = angle % 360;

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), referenceAngle);
    updateHistory(step, false);
    updateTurtleState(step);
    return step;

  }

  public List<TurtleStep> move(double distance) {
    double dx = this.calculateXComponent(distance, this.currentState.heading());
    double dy = this.calculateYComponent(distance, this.currentState.heading());
    List<TurtleStep> intermediateSteps = new ArrayList<>();

    wrapMove(dx, dy, intermediateSteps);

    return intermediateSteps;

  }

  // recursive method for out of bound move in WRAP mode
  private void wrapMove(double dx, double dy, List<TurtleStep> intermediateSteps) {
    if (isWithinBounds(dx, dy)) {
      intermediateSteps.add(normalMove(dx, dy));
      return;
    }

    Vector interPosChange = findInterPosChange(dx, dy);
    TurtleStep step = new TurtleStep(this.currentState, interPosChange, 0);
    updateHistory(step, true);
    intermediateSteps.add(step);

    Point finalPos = calculateFinalPosition(this.currentState.position(), interPosChange);
    finalPos.setX(finalPos.getX() == TurtleAnimator.X_MAX ? TurtleAnimator.X_MIN
        : finalPos.getX() == TurtleAnimator.X_MIN ? TurtleAnimator.X_MAX : finalPos.getX());
    finalPos.setY(finalPos.getY() == TurtleAnimator.Y_MAX ? TurtleAnimator.Y_MIN
        : finalPos.getY() == TurtleAnimator.Y_MIN ? TurtleAnimator.Y_MAX : finalPos.getY());

    this.currentState = new TurtleState(finalPos, this.currentState.heading());

    wrapMove(dx - interPosChange.dx(), dy - interPosChange.dy(), intermediateSteps);
  }

  private Vector findInterPosChange(double dx, double dy) {
    double interDx = 0.0;
    double interDy = 0.0;

    if (isOutXMax(dx)) {
      interDx = TurtleAnimator.X_MAX - this.currentState.position().getX();
    } else if (isOutXMin(dx)) {
      interDx = TurtleAnimator.X_MIN - this.currentState.position().getX();
    }

    interDy = this.calculateYComponentGivenXComponentAngle(interDx, this.currentState.heading());

    if (isOutYMax(dy) || interDy > TurtleAnimator.Y_MAX) {
      interDy = TurtleAnimator.Y_MAX - this.currentState.position().getY();
    } else if (isOutYMin(dy) || interDy < TurtleAnimator.Y_MIN) {
      interDy = TurtleAnimator.Y_MIN - this.currentState.position().getY();
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

  // reset turtle
  public void reset(TurtleState initialState) {
    this.currentPointInStepHistory = 0;
    this.currentState = initialState;
  }

  private void updateHistory(TurtleStep newStep, boolean isCrossBorder) {
    // update history
    if (currentPointInStepHistory != this.stepHistory.size()) {
      this.stepHistory.add(currentPointInStepHistory + 1,
          new TurtleStepExtended(newStep, isCrossBorder));
    } else {
      this.stepHistory.add(new TurtleStepExtended(newStep, isCrossBorder));
    }
    this.currentPointInStepHistory++;
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

  public static Point calculateFinalPosition(Point initialPosition, Vector vector) {
    double xFinal = initialPosition.getX() + vector.dx();
    double yFinal = initialPosition.getY() + vector.dy();

    return new Point(xFinal, yFinal);
  }

}
