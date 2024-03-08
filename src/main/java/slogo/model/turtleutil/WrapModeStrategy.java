package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;

public class WrapMode implements Mode {

  @Override
  public TurtleStep move(Point currPos, double dx, double dy, TurtleState currentState, List<TurtleStep> intermediateStates) {
    return null;
  }
}
