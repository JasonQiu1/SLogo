package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Vector;

public class WindowModeStrategy implements ModeStrategy {
  Turtle turtle;

  @Override
  public List<TurtleStep> move(Turtle turtle, double distance) {
    this.turtle = turtle;
    double dx = Turtle.calculateXComponent(distance, turtle.getCurrentState().heading());
    double dy = Turtle.calculateYComponent(distance, turtle.getCurrentState().heading());
    List<TurtleStep> intermediateSteps = new ArrayList<>();

    intermediateSteps.add(normalMove(dx, dy));
    return intermediateSteps;
  }
  private TurtleStep normalMove(double dx, double dy) {
    Vector posChange = new Vector(dx, dy);
    TurtleStep step = new TurtleStep(turtle.getCurrentState(), posChange, 0);
    turtle.updateHistory(step, false);
    turtle.updateTurtleState(step);
    return step;
  }

}

