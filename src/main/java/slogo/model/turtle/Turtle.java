package slogo.model.turtle;

import java.util.ArrayList;
import java.util.List;
import slogo.exception.model.turtle.InvalidPositionException;

public class Turtle {
  private int id;
  private TurtleState currentState; // heading = angle from vertical y-axis (all calculations use angle from horizontal x-axis)
  private List<TurtleStep> stepHistory;
  private int currentPointInStepHistory;

  public Turtle() {
    this.id = 0;
    this.currentState = TurtleAnimator.getInitialTurtleState();
    this.stepHistory = new ArrayList<>();
    this.currentPointInStepHistory= 0;
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

  // update turtle's position/heading and return turtle's final position/facing position (a point on the edge of screen)
  public TurtleStep doStep (double distance, double angle) {
    TurtleState startingState = this.currentState;
    move(distance);
    rotate(angle);
    return null;
  }

  // return turtle's heading towards given point
//  public double getHeadingTowards(Point point) {
//    // TODO
//    return 0;
//  }

  public TurtleStep stepForward() {
    // TODO
    return null;
  }

  public TurtleStep stepBack() {
    // TODO
    return null;
  }

  public TurtleStep setHeading (double degrees) {
    // TODO
    return null;
  }

  public TurtleStep setPosition (Point position) throws InvalidPositionException {
    // TODO
    return null;
  }

  // reset position
  public void reset(TurtleState initialState) {
    this.currentState = initialState;
  }

  private void rotate(double angle) {
    // TODO

  }
  private void move(double distance) {
    // TODO

  }

}
