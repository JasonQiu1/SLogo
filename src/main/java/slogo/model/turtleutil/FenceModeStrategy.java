package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;

public class FenceMode implements Mode {

  @Override
  public TurtleStep move(Point currPos, double dx, double dy, TurtleState currentState, List<TurtleStep> intermediateStates) {
//    if (currPos.getX() + dx > TurtleAnimator.X_MAX) {
//      dx = TurtleAnimator.X_MAX - currPos.getX();
//    } else if (currPos.getX() + dx < TurtleAnimator.X_MIN) {
//      dx = currPos.getX() - TurtleAnimator.X_MIN;
//    } else if (currPos.getY() + dy > TurtleAnimator.Y_MAX) {
//      dy = TurtleAnimator.Y_MAX - currPos.getY();
//    } else {
//      dy = currPos.getY() - TurtleAnimator.Y_MIN;
//    }
//    Vector posChange = new Vector(dx, dy);
//    Point finalPos = calculateFinalPosition(this.currentState.position(), posChange);
//    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
//    updateStateAndHistory(step, false, finalPos, this.currentState.heading());
//    return step;
    return null;
  }
}

//  private TurtleStep fenceMove(Point currPos, double dx, double dy) {
//    if (currPos.getX() + dx > TurtleAnimator.X_MAX) {
//      dx = TurtleAnimator.X_MAX - currPos.getX();
//    } else if (currPos.getX() + dx < TurtleAnimator.X_MIN) {
//      dx = currPos.getX() - TurtleAnimator.X_MIN;
//    } else if (currPos.getY() + dy > TurtleAnimator.Y_MAX) {
//      dy = TurtleAnimator.Y_MAX - currPos.getY();
//    } else {
//      dy = currPos.getY() - TurtleAnimator.Y_MIN;
//    }
//    Vector posChange = new Vector(dx, dy);
//    Point finalPos = calculateFinalPosition(this.currentState.position(), posChange);
//    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
//    updateStateAndHistory(step, false, finalPos, this.currentState.heading());
//    return step;
//  }
