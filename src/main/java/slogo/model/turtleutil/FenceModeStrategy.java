package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Vector;

/**
 * Strategy for Fence mode behavior. If the turtle attempts to move past the edge of the screen it
 * will stop.
 *
 * @author Judy He
 */
public class FenceModeStrategy implements ModeStrategy {

  Turtle turtle;

  /**
   * Moves the turtle by the specified distance given the Fence mode strategy
   *
   * @param turtle   the turtle to be moved
   * @param distance the distance to move
   * @return the intermediate steps representing the movement
   */
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
    if (turtle.isOutXMax(dx)) {
      dx = TurtleAnimatorImplementation.X_MAX - turtle.getCurrentState().position().getX();
    } else if (turtle.isOutXMin(dx)) {
      dx = turtle.getCurrentState().position().getX() - TurtleAnimatorImplementation.X_MIN;
    } else if (turtle.isOutYMax(dy)) {
      dy = TurtleAnimatorImplementation.Y_MAX - turtle.getCurrentState().position().getY();
    } else if (turtle.isOutYMin(dy)) {
      dy = turtle.getCurrentState().position().getY() - TurtleAnimatorImplementation.Y_MIN;
    }
    Vector posChange = new Vector(dx, dy);
    TurtleStep step = new TurtleStep(turtle.getCurrentState(), posChange, 0);
    turtle.updateHistory(step, false);
    turtle.updateTurtleState(step);
    return step;
  }
}
