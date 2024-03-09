package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;

public class FenceModeStrategy implements ModeStrategy {
  Turtle turtle;
  @Override
  public List<TurtleStep> move(Turtle turtle, double distance) {
    this.turtle = turtle;
    double dx = Turtle.calculateXComponent(distance, turtle.getCurrentState().heading());
    double dy = Turtle.calculateYComponent(distance, turtle.getCurrentState().heading());
    List<TurtleStep> intermediateSteps = new ArrayList<>();

    intermediateSteps.add(fenceMove(dx, dy));
    return intermediateSteps;
  }

  private TurtleStep fenceMove(double dx, double dy) {
    if (isOutXMax(dx)) {
      dx = TurtleAnimatorImplementation.X_MAX - turtle.getCurrentState().position().getX();
    } else if (isOutXMin(dx)) {
      dx = turtle.getCurrentState().position().getX() - TurtleAnimatorImplementation.X_MIN;
    } else if (isOutYMax(dy)) {
      dy = TurtleAnimatorImplementation.Y_MAX - turtle.getCurrentState().position().getY();
    } else if (isOutYMin(dy)){
      dy = turtle.getCurrentState().position().getY() - TurtleAnimatorImplementation.Y_MIN;
    }
    Vector posChange = new Vector(dx, dy);
    TurtleStep step = new TurtleStep(turtle.getCurrentState(), posChange, 0);
    turtle.updateHistory(step, false);
    turtle.updateTurtleState(step);
    return step;
  }
  private boolean isOutXMax(double dx) {
    return turtle.getCurrentState().position().getX() + dx > TurtleAnimatorImplementation.X_MAX;
  }

  private boolean isOutXMin(double dx) {
    return turtle.getCurrentState().position().getX() + dx < TurtleAnimatorImplementation.X_MIN;
  }

  private boolean isOutYMax(double dy) {
    return turtle.getCurrentState().position().getY() + dy > TurtleAnimatorImplementation.Y_MAX;
  }

  private boolean isOutYMin(double dy) {
    return turtle.getCurrentState().position().getY() + dy < TurtleAnimatorImplementation.Y_MIN;
  }
}
