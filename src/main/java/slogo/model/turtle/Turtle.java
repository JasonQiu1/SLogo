package slogo.model.turtle;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;

public class Turtle {
  private int id;
  private TurtleState currentState;
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

  public void setId(int id) {
    this.id = id;
  }

  public TurtleState getCurrentState() {
    return currentState;
  }

  public void setCurrentState(TurtleState currentState) {
    this.currentState = currentState;
  }

  public List<TurtleStep> getStepHistory() {
    return stepHistory;
  }

  public void setStepHistory(List<TurtleStep> stepHistory) {
    this.stepHistory = stepHistory;
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
  public TurtleStep doStep (double length, double angle) {
    // TODO
    return null;
  }

  // return turtle's heading towards given point
  public double getHeadingTowards(Point point) {
    // TODO
    return 0;
  }

  public boolean stepForward() {
    // TODO
    return false;
  }

  public boolean stepBack() {
    // TODO
    return false;
  }

  // reset position
  public void reset(TurtleState initialState) {
    this.currentState = initialState;
  }

  private void rotate(TurtleStep step) {
    // TODO

  }
  private void move(TurtleStep step) {
    // TODO

  }

}
