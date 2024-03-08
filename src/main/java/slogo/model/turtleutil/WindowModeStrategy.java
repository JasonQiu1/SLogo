package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;

public class WindowMode implements Mode {

  @Override
  public List<TurtleStep> move(Turtle turtle, double distance) {
    // Implementation for window mode behavior
  }

}
//  @Override
//  public TurtleStep move(Point currPos, double dx, double dy, TurtleState currentState, List<TurtleStep> intermediateStates) {
////    Vector posChange = new Vector(dx, dy);
////    Point finalPos = Turtle.calculateFinalPosition(currPos, posChange);
////    intermediateStates.add(new TurtleStep(currentState, posChange, 0));
////    return new TurtleStep(currentState, posChange, 0);
//    return null;
//  }
//}

// private TurtleStep normalMove(Point currPos, double dx, double dy) {
//    Vector posChange = new Vector(dx, dy);
//    Point finalPos = calculateFinalPosition(currPos, posChange);
//    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);
//    updateStateAndHistory(step, false, finalPos, this.currentState.heading());
//    return step;
//  }
