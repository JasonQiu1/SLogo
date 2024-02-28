package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.api.exception.UnsupportedTurtleCommandException;
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
  private List<TurtleStep> stepHistory;
  private int currentPointInStepHistory;
  private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.model.";
  private ResourceBundle errorResourceBundle; // resource bundle for error handling messages

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

  public List<TurtleStep> getStepHistory() {
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
    updateStateAndHistory(step, this.currentState.position(), finalHeading);

    return step;
  }

  public TurtleStep stepForward() {
    this.currentPointInStepHistory++;
    TurtleStep forwardStep = this.stepHistory.get(currentPointInStepHistory);

    // update turtle state
    Point finalPos = TurtleGeometry.calculateFinalPosition(this.currentState.position(), forwardStep.changeInPosition());
    double finalHeading = this.currentState.heading() + forwardStep.changeInAngle();
    this.currentState = new TurtleState(finalPos, finalHeading);

    return forwardStep;
  }

  public TurtleStep stepBack() {
    TurtleStep currStep = this.stepHistory.get(currentPointInStepHistory);

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

    updateStateAndHistory(step, this.currentState.position(), degrees);

    return step;
  }

  public TurtleStep setXY (Point position) throws InvalidPositionException {
    if (TurtleAnimator.mode.equals("window")) {
      if (position.getX() > TurtleAnimator.getWidth() / 2 || position.getY() > TurtleAnimator.getHeight() / 2 || position.getX() < - TurtleAnimator.getWidth() / 2 || position.getY() < - TurtleAnimator.getHeight() / 2) {
        throw new InvalidPositionException(errorResourceBundle.getString("InvalidPosition"));
      }
    }

    Vector posChange = TurtleGeometry.getVectorBetweenTwoPoints(this.currentState.position(), position);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);

    updateStateAndHistory(step, position, this.currentState.heading());

    return step;
  }

  public TurtleStep rotate(double angle) {
    double referenceAngle = angle % 360;
    double finalHeading = this.currentState.heading() + referenceAngle;

    TurtleStep step = new TurtleStep(this.currentState, new Vector(0,0), referenceAngle);
    updateStateAndHistory(step, this.currentState.position(), finalHeading);

    return step;

  }
  public TurtleStep move(double distance) {
    double dx = TurtleGeometry.calculateXComponent(distance, this.currentState.heading());
    double dy = TurtleGeometry.calculateYComponent(distance, this.currentState.heading());
    Vector posChange = new Vector(dx, dy);
    Point finalPos = TurtleGeometry.calculateFinalPosition(this.currentState.position(), posChange);

    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
    updateStateAndHistory(step, finalPos, 0);

    return step;

  }

  // reset turtle
  public void reset(TurtleState initialState) {
    this.stepHistory.clear();
    this.currentPointInStepHistory = 0;
    this.currentState = initialState;
  }

  private void updateStateAndHistory(TurtleStep newStep, Point newPos, double newHeading) {
    // update history
    this.stepHistory.add(newStep);
    this.currentPointInStepHistory++;
    // update current state
    this.currentState = new TurtleState(newPos, newHeading);
  }

}
