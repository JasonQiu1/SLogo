package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleStep;

public class WindowModeStrategy implements ModeStrategy {

  @Override
  public List<TurtleStep> move(Turtle turtle, double distance) {
    return null;
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
