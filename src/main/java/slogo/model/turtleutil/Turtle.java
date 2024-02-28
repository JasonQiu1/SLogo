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
 * The Turtle class represents a turtle in the animation. It keeps track of the turtle's id, step history and current point in history, and current state (position and heading).
 * It updates the turtle's state and returns the corresponding step that the TurtleAnimator and View can interpret after a command is received
 *
 * @author Judy He
 */
public class Turtle {
  private int id;
  private TurtleState currentState; // heading = angle from vertical y-axis (all calculations use angle from horizontal x-axis)
  private final List<TurtleStepExtended> stepHistory;
  private int currentPointInStepHistory;
  private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.model.";
  private final ResourceBundle errorResourceBundle; // resource bundle for error handling messages

  public Turtle() {
    this.id = 0;
    this.currentState = TurtleAnimator.getInitialTurtleState();
    this.stepHistory = new ArrayList<>();
    this.currentPointInStepHistory= 0;
    this.errorResourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ErrorsEnglish");
  }

  public int getId() {
    return id;
  }

  public TurtleState getCurrentState() {
    return currentState;
  }

  public List<TurtleStepExtended> getStepHistory() {
    return stepHistory;
  }

  // return the step turtle is currently on in its step history
  protected int getCurrentPointInStepHistory() {
    return currentPointInStepHistory;

  }

  // update the step turtle is currently on in its step history
  protected void setCurrentPointInStepHistory(int currentPointInStepHistory) {
    this.currentPointInStepHistory = currentPointInStepHistory;
  }

  public TurtleStep turnTowards(Point point) {

    Vector reference = new Vector(0, 1); // vector representing vertical axis
    Vector vectorToPoint = TurtleGeometry.getVectorBetweenTwoPoints(this.currentState.position(), point);
    double angleChange = TurtleGeometry.getAngleBetweenTwoVectors(reference, vectorToPoint) - this.currentState.heading();
    double finalHeading = this.currentState.heading() + angleChange;

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0,0), angleChange);
    updateStateAndHistory(step, false, this.currentState.position(), finalHeading);

    return step;
  }

  public TurtleStep stepForward() {
    this.currentPointInStepHistory++;
    TurtleStepExtended forwardStep = this.stepHistory.get(currentPointInStepHistory);

    // update turtle state
    Point finalPos = TurtleGeometry.calculateFinalPosition(this.currentState.position(), forwardStep.changeInPosition());
    double finalHeading = this.currentState.heading() + forwardStep.changeInAngle();
    this.currentState = new TurtleState(finalPos, finalHeading);

    return forwardStep;
  }

  public TurtleStep stepBack() {
    TurtleStepExtended currStep = this.stepHistory.get(currentPointInStepHistory);

    // perform opposite position/heading change
    Vector oldPosChange = currStep.changeInPosition();
    Vector updatedPosChange = new Vector(oldPosChange.getDx() * -1, oldPosChange.getDy() * -1);
    TurtleStep backwardStep = new TurtleStep(this.currentState, updatedPosChange, -1 * currStep.changeInAngle());
    this.currentPointInStepHistory--;

    // update turtle state
    Point finalPos = TurtleGeometry.calculateFinalPosition(this.currentState.position(), backwardStep.changeInPosition());
    double finalHeading = this.currentState.heading() + backwardStep.changeInAngle();
    this.currentState = new TurtleState(finalPos, finalHeading);

    return backwardStep;
  }

  public TurtleStep setHeading (double degrees) {
    double angleChange = TurtleGeometry.getAngleChange(this.currentState.heading(), degrees);
    TurtleStep step = new TurtleStep(this.currentState, new Vector(0,0), angleChange);

    updateStateAndHistory(step, false, this.currentState.position(), degrees);

    return step;
  }

  public TurtleStep setXY(Point position) throws InvalidPositionException {
    if (TurtleAnimator.mode.equals(TurtleAnimator.FENCE_MODE_KEY)) {
      if (position.getX() > TurtleAnimator.X_MAX || position.getY() > TurtleAnimator.Y_MAX || position.getX() < TurtleAnimator.X_MIN || position.getY() < TurtleAnimator.Y_MIN) {
        throw new InvalidPositionException(errorResourceBundle.getString("InvalidPosition"));
      }
    }

    Vector posChange = TurtleGeometry.getVectorBetweenTwoPoints(this.currentState.position(), position);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);

    updateStateAndHistory(step, false, position, this.currentState.heading());

    return step;
  }

  public TurtleStep rotate(double angle) {
    double referenceAngle = angle % 360;
    double finalHeading = this.currentState.heading() + referenceAngle;

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0,0), referenceAngle);
    updateStateAndHistory(step, false, this.currentState.position(), finalHeading);

    return step;

  }
  public List<TurtleStep> move(double distance) {
    double dx = TurtleGeometry.calculateXComponent(distance, this.currentState.heading());
    double dy = TurtleGeometry.calculateYComponent(distance, this.currentState.heading());
    Point currPos = this.currentState.position();
    List<TurtleStep> intermediateSteps = new ArrayList<>();

    // within bound
    if (currPos.getX() + dx <= TurtleAnimator.X_MAX && currPos.getX() + dx >= TurtleAnimator.X_MIN && currPos.getY() + dy <= TurtleAnimator.Y_MAX && currPos.getY() + dy >= TurtleAnimator.Y_MIN) {
      intermediateSteps.add(normalMove(currPos, dx, dy));
      return intermediateSteps;
    }

    // out of bound
    if (TurtleAnimator.mode.equals(TurtleAnimator.FENCE_MODE_KEY)) {
      intermediateSteps.add(fenceMove(currPos, dx, dy));
      return intermediateSteps;
    }
    if (TurtleAnimator.mode.equals(TurtleAnimator.WINDOW_MODE_KEY)) {
      intermediateSteps.add(normalMove(currPos, dx, dy));
      return intermediateSteps;
    }

    wrapMove(currPos, dx, dy, intermediateSteps);

    return intermediateSteps;

  }
  private TurtleStep normalMove(Point currPos, double dx, double dy) {
    Vector posChange = new Vector(dx, dy);
    Point finalPos = TurtleGeometry.calculateFinalPosition(currPos, posChange);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, false, finalPos, 0);
    return step;
  }
  private TurtleStep fenceMove(Point currPos, double dx, double dy) {
    if (currPos.getX() + dx > TurtleAnimator.X_MAX) {
      dx = TurtleAnimator.X_MAX - currPos.getX();
    }
    else if (currPos.getX() + dx < TurtleAnimator.X_MIN) {
      dx = currPos.getX() - TurtleAnimator.X_MIN;
    }
    else if (currPos.getY() + dy > TurtleAnimator.Y_MAX) {
      dy = TurtleAnimator.Y_MAX - currPos.getY();
    }
    else {
      dy = currPos.getY() - TurtleAnimator.Y_MIN;
    }
    Vector posChange = new Vector(dx, dy);
    Point finalPos = TurtleGeometry.calculateFinalPosition(this.currentState.position(), posChange);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, false, finalPos, 0);
    return step;
  }
  // recursive method for out of bound move in WRAP mode
  private void wrapMove(Point currPos, double dx, double dy, List<TurtleStep> intermediateSteps) {
    if (currPos.getX() + dx < TurtleAnimator.X_MAX && currPos.getX() + dx < TurtleAnimator.X_MIN && currPos.getY() + dy > TurtleAnimator.Y_MAX && currPos.getY() + dy < TurtleAnimator.Y_MIN) {
      intermediateSteps.add(normalMove(currPos, dx, dy)); // last step of a cross border step will have crossBorderIntermediateStep = false;
      return;
    }

    double interDx = 0;
    double interDy = 0;

    if (currPos.getX() + dx > TurtleAnimator.X_MAX) {
      interDx = TurtleAnimator.X_MAX - currPos.getX();
    }
    else if (currPos.getX() + dx < TurtleAnimator.X_MIN) {
      interDx = currPos.getX() - TurtleAnimator.X_MIN;
    }
    else if (currPos.getY() + dy > TurtleAnimator.Y_MAX) {
      interDy = TurtleAnimator.Y_MAX - currPos.getY();
    }
    else {
      interDy = currPos.getY() - TurtleAnimator.Y_MIN;
    }

    Vector posChange = new Vector(interDx, interDy);
    Point finalPos = TurtleGeometry.calculateFinalPosition(this.currentState.position(), posChange);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, true, finalPos, 0);
    intermediateSteps.add(step);

    wrapMove(currPos, dx-interDx, dy-interDy, intermediateSteps);
  }

  // reset turtle
  public void reset(TurtleState initialState) {
    this.currentPointInStepHistory = 0;
    this.currentState = initialState;
  }

  private void updateStateAndHistory(TurtleStep newStep, boolean isCrossBorder, Point newPos, double newHeading) {
    // update history
    this.stepHistory.add(new TurtleStepExtended(newStep, isCrossBorder));
    this.currentPointInStepHistory++;
    // update current state
    this.currentState = new TurtleState(newPos, newHeading);
  }

}
