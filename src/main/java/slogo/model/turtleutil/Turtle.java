package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.api.turtle.Vector;

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
  private final ResourceBundle errorResourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ErrorsEnglish");

  /**
   * Constructor for instantiating a new Turtle
   * @param id, id of turtle
   */
  public Turtle(int id) {
    this.id = id;
    this.currentState = TurtleAnimator.getInitialTurtleState();
    this.stepHistory = new ArrayList<>();
    this.currentPointInStepHistory= 0;
  }

  /**
   * Retrieve a turtle's id instance variable
   * @return id of turtle
   */
  public int getId() {
    return id;
  }

  /**
   * Retrieve a turtle's current state
   * @return the current state of turtle
   */
  public TurtleState getCurrentState() {
    return currentState;
  }

  /**
   * Returns the current step history of a certain length. May return more steps
   * than maxLength due to intermediate states from wrapping.
   * maxLength limits the number of commands, not number of steps.
   *
   * @param maxLength the max length of step history to return. If 0, then return entire step
   *                  history. If positive, then return the backwards
   *                  history up to length.
   * @return turtle's step history
   */
  public List<TurtleStep> getStepHistory(int maxLength) {
    List<TurtleStep> history = new ArrayList<>();
    int startInd = 0;
    if (maxLength < this.stepHistory.size()) {
      startInd = currentPointInStepHistory-1;
      int count = 0;
      while (count != maxLength) {
        while (startInd-1 >= 0 && this.stepHistory.get(startInd-1).crossBorderIntermediateStep()) {
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
   * @param point, point to turn towards
   * @return TurtleStep representing the turn took
   */
  public TurtleStep turnTowards(Point point) {

    Vector vectorToPoint =
        this.getVectorBetweenTwoPoints(this.currentState.position(), point);
    double angleChange = vectorToPoint.getDirection() - this.currentState.heading();
    double finalHeading = vectorToPoint.getDirection();

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), angleChange);
    updateStateAndHistory(step, false, this.currentState.position(), finalHeading);

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
      Vector updatedPosChange = new Vector(oldPosChange.getDx() * -1, oldPosChange.getDy() * -1);
      backwardStep = new TurtleStep(this.currentState, updatedPosChange, currStep.changeInAngle());
    }

    updateTurtleState(backwardStep);

    return backwardStep;
  }

  private void updateTurtleState(TurtleStep step) {
    // update turtle state
    Point finalPos = this.calculateFinalPosition(this.currentState.position(),
        step.changeInPosition());
    double finalHeading = this.currentState.heading() + step.changeInAngle();
    this.currentState = new TurtleState(finalPos, finalHeading);
  }

  public TurtleStep setHeading(double degrees) {
    double angleChange = this.getAngleChange(this.currentState.heading(), degrees);
    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), angleChange);

    updateStateAndHistory(step, false, this.currentState.position(), degrees);

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

    updateStateAndHistory(step, false, position, this.currentState.heading());

    return step;
  }

  public TurtleStep rotate(double angle) {
    double referenceAngle = angle % 360;
    double finalHeading = this.currentState.heading() + referenceAngle;

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0, 0), referenceAngle);
    updateStateAndHistory(step, false, this.currentState.position(), finalHeading);

    return step;

  }

  public List<TurtleStep> move(double distance) {
    double dx = this.calculateXComponent(distance, this.currentState.heading());
    double dy = this.calculateYComponent(distance, this.currentState.heading());
    Point currPos = this.currentState.position();
    List<TurtleStep> intermediateSteps = new ArrayList<>();

    // within bound
    if (currPos.getX() + dx <= TurtleAnimator.X_MAX && currPos.getX() + dx >= TurtleAnimator.X_MIN
        && currPos.getY() + dy <= TurtleAnimator.Y_MAX
        && currPos.getY() + dy >= TurtleAnimator.Y_MIN) {
      intermediateSteps.add(normalMove(currPos, dx, dy));
      return intermediateSteps;
    }

    // out of bound
    if (TurtleAnimator.mode.equals(TurtleAnimator.FENCE_MODE_KEY)) {
      intermediateSteps.add(fenceMove(currPos, dx, dy));
    } else if (TurtleAnimator.mode.equals(TurtleAnimator.WINDOW_MODE_KEY)) {
      intermediateSteps.add(normalMove(currPos, dx, dy));
    } else {
      wrapMove(currPos, dx, dy, intermediateSteps);
    }

    return intermediateSteps;

  }

  private TurtleStep normalMove(Point currPos, double dx, double dy) {
    Vector posChange = new Vector(dx, dy);
    Point finalPos = this.calculateFinalPosition(currPos, posChange);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, false, finalPos, this.currentState.heading());
    return step;
  }

  private TurtleStep fenceMove(Point currPos, double dx, double dy) {
    if (currPos.getX() + dx > TurtleAnimator.X_MAX) {
      dx = TurtleAnimator.X_MAX - currPos.getX();
    } else if (currPos.getX() + dx < TurtleAnimator.X_MIN) {
      dx = currPos.getX() - TurtleAnimator.X_MIN;
    } else if (currPos.getY() + dy > TurtleAnimator.Y_MAX) {
      dy = TurtleAnimator.Y_MAX - currPos.getY();
    } else {
      dy = currPos.getY() - TurtleAnimator.Y_MIN;
    }
    Vector posChange = new Vector(dx, dy);
    Point finalPos = this.calculateFinalPosition(this.currentState.position(), posChange);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, false, finalPos, this.currentState.heading());
    return step;
  }

  // recursive method for out of bound move in WRAP mode
  private void wrapMove(Point currPos, double dx, double dy, List<TurtleStep> intermediateSteps) {
    if (currPos.getX() + dx <= TurtleAnimator.X_MAX && currPos.getX() + dx >= TurtleAnimator.X_MIN
        && currPos.getY() + dy <= TurtleAnimator.Y_MAX
        && currPos.getY() + dy >= TurtleAnimator.Y_MIN) {
      intermediateSteps.add(normalMove(currPos, dx,
          dy)); // last step of a cross border step will have crossBorderIntermediateStep = false;
      return;
    }

    double interDx = 0;
    double interDy = 0;
    Point oppositeBorderPos = new Point(0, 0);

    if (currPos.getX() + dx > TurtleAnimator.X_MAX) {
      interDx = TurtleAnimator.X_MAX - currPos.getX();
      interDy = this.calculateYComponentGivenXComponentAngle(interDx,
          this.currentState.heading());
      if (Math.abs(interDy) > TurtleAnimator.Y_MAX) interDy = 0;
      oppositeBorderPos.setX(TurtleAnimator.X_MIN);
    } else if (currPos.getX() + dx < TurtleAnimator.X_MIN) {
      interDx = TurtleAnimator.X_MIN - currPos.getX();
      interDy = this.calculateYComponentGivenXComponentAngle(interDx,
          this.currentState.heading());
      if (Math.abs(interDy) > TurtleAnimator.Y_MAX) interDy = 0;
      oppositeBorderPos.setX(TurtleAnimator.X_MAX);
    }

    if (currPos.getY() + dy > TurtleAnimator.Y_MAX && interDy == 0) {
      interDy = TurtleAnimator.Y_MAX - currPos.getY();
      interDx = this.calculateXComponentGivenYComponentAngle(interDy,
          this.currentState.heading());
      oppositeBorderPos.setY(TurtleAnimator.Y_MIN);
    } else if (currPos.getY() + dy < TurtleAnimator.Y_MIN && interDy == 0) {
      interDy = TurtleAnimator.Y_MIN - currPos.getY();
      interDx = this.calculateXComponentGivenYComponentAngle(interDy,
          this.currentState.heading());
      oppositeBorderPos.setY(TurtleAnimator.Y_MAX);
    }

    Vector posChange = new Vector(interDx, interDy);
    Point finalPos = this.calculateFinalPosition(currPos, posChange);
    if (oppositeBorderPos.getX() == 0) {
      oppositeBorderPos.setX(finalPos.getX());
    }
    if (oppositeBorderPos.getY() == 0) {
      oppositeBorderPos.setY(finalPos.getY());
    }

    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, true, finalPos, this.currentState.heading());
    intermediateSteps.add(step);

    // update position to cross border
    this.currentState = new TurtleState(oppositeBorderPos, this.currentState.heading());

    wrapMove(this.currentState.position(), dx - interDx, dy - interDy, intermediateSteps);
  }

  // reset turtle
  public void reset(TurtleState initialState) {
    this.currentPointInStepHistory = 0;
    this.currentState = initialState;
  }


  private void updateStateAndHistory(TurtleStep newStep, boolean isCrossBorder, Point newPos,
      double newHeading) {
    // update history
    if (currentPointInStepHistory != this.stepHistory.size()) {
      this.stepHistory.add(currentPointInStepHistory + 1,
          new TurtleStepExtended(newStep, isCrossBorder));
    } else {
      this.stepHistory.add(new TurtleStepExtended(newStep, isCrossBorder));
    }
    this.currentPointInStepHistory++;
    // update current state
    this.currentState = new TurtleState(newPos, newHeading);
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
    double xFinal = initialPosition.getX() + vector.getDx();
    double yFinal = initialPosition.getY() + vector.getDy();

    return new Point(xFinal, yFinal);
  }

}
