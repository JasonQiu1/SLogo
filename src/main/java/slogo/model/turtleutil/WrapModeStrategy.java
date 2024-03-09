package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;

/**
 * Strategy for Wrap mode behavior.
 *
 * @author Judy He
 *
 */
public class WrapModeStrategy implements ModeStrategy {
  Turtle turtle;

  /**
   * Moves the turtle by the specified distance given the Wrap mode strategy
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

    wrapMove(dx, dy, intermediateSteps);
    return intermediateSteps;
  }

  private void wrapMove(double dx, double dy, List<TurtleStep> intermediateSteps) {
    if (turtle.isWithinBounds(dx, dy)) {
      intermediateSteps.add(normalMove(dx, dy));
      return;
    }

    Vector interPosChange = findInterPosChange(dx, dy);
    TurtleStep step = new TurtleStep(turtle.getCurrentState(), interPosChange, 0);
    turtle.updateHistory(step, true);
    intermediateSteps.add(step);

    Point finalPos = Turtle.calculateFinalPosition(turtle.getCurrentState().position(), interPosChange);

    if (finalPos.getX() == TurtleAnimatorImplementation.X_MAX) finalPos.setX(TurtleAnimatorImplementation.X_MIN);
    else if (finalPos.getX() == TurtleAnimatorImplementation.X_MIN) finalPos.setX(TurtleAnimatorImplementation.X_MAX);

    if (finalPos.getY() == TurtleAnimatorImplementation.Y_MAX) finalPos.setY(TurtleAnimatorImplementation.Y_MIN);
    else if (finalPos.getY() == TurtleAnimatorImplementation.Y_MIN) finalPos.setY(TurtleAnimatorImplementation.Y_MAX);

    turtle.setCurrentState(new TurtleState(finalPos, turtle.getCurrentState().heading()));

    wrapMove(dx - interPosChange.dx(), dy - interPosChange.dy(), intermediateSteps);
  }

  private Vector findInterPosChange(double dx, double dy) {

    double interDx = 0.0;
    double interDy = 0.0;

    if (turtle.isOutXMax(dx)) {
      interDx = TurtleAnimatorImplementation.X_MAX - turtle.getCurrentState().position().getX();
      interDy = Turtle.calculateYComponentGivenXComponentAngle(interDx, turtle.getCurrentState().heading());
    } else if (turtle.isOutXMin(dx)) {
      interDx = TurtleAnimatorImplementation.X_MIN - turtle.getCurrentState().position().getX();
      interDy = Turtle.calculateYComponentGivenXComponentAngle(interDx, turtle.getCurrentState().heading());
    }

    if (turtle.isOutYMax(interDy) || turtle.isOutYMin(interDy)) {
      interDy = 0.0;
    }

    if (turtle.isOutYMax(dy) && interDy == 0) {
      interDy = TurtleAnimatorImplementation.Y_MAX - turtle.getCurrentState().position().getY();
      interDx = Turtle.calculateXComponentGivenYComponentAngle(interDy, turtle.getCurrentState().heading());
    } else if (turtle.isOutYMin(dy) && interDy == 0) {
      interDy = TurtleAnimatorImplementation.Y_MIN - turtle.getCurrentState().position().getY();
      interDx = Turtle.calculateXComponentGivenYComponentAngle(interDy, turtle.getCurrentState().heading());
    }

    return new Vector(interDx, interDy);
  }


  private TurtleStep normalMove(double dx, double dy) {
    Vector posChange = new Vector(dx, dy);
    TurtleStep step = new TurtleStep(turtle.getCurrentState(), posChange, 0);
    turtle.updateHistory(step, false);
    turtle.updateTurtleState(step);
    return step;
  }
}
