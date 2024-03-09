package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Vector;

/**
 * Strategy for Window mode behavior. The turtle can move past the edges of the screen, unbounded.
 *
 * @author Judy He
 */
public class WindowModeStrategy implements ModeStrategy {

  private Turtle turtle;

  /**
   * Moves the turtle by the specified distance given the Window mode strategy
   *
   * @param turtle   the turtle to be moved
   * @param distance the distance to move
   * @return the intermediate steps representing the movement
   */
  @Override
  public List<TurtleStep> move(Turtle turtle, double distance) {
    this.turtle = turtle;
    return generateInterSteps(calculatePosChange(distance));
  }

  private Vector calculatePosChange(double distance) {
    double dx = Turtle.calculateXComponent(distance, turtle.getCurrentState().heading());
    double dy = Turtle.calculateYComponent(distance, turtle.getCurrentState().heading());
    return new Vector(dx, dy);
  }

  private List<TurtleStep> generateInterSteps(Vector v) {
    List<TurtleStep> intermediateSteps = new ArrayList<>();
    intermediateSteps.add(normalMove(v.dx(), v.dy()));
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

