package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;

public class WrapModeStrategy implements ModeStrategy {
  Turtle turtle;
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
    if (isWithinBounds(dx, dy)) {
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

    if (isOutXMax(dx)) {
      interDx = TurtleAnimatorImplementation.X_MAX - turtle.getCurrentState().position().getX();
      interDy = Turtle.calculateYComponentGivenXComponentAngle(interDx, turtle.getCurrentState().heading());
    } else if (isOutXMin(dx)) {
      interDx = TurtleAnimatorImplementation.X_MIN - turtle.getCurrentState().position().getX();
      interDy = Turtle.calculateYComponentGivenXComponentAngle(interDx, turtle.getCurrentState().heading());
    }

    if (isOutYMax(interDy) || isOutYMin(interDy)) {
      interDy = 0.0;
    }

    if (isOutYMax(dy) && interDy == 0) {
      interDy = TurtleAnimatorImplementation.Y_MAX - turtle.getCurrentState().position().getY();
      interDx = Turtle.calculateXComponentGivenYComponentAngle(interDy, turtle.getCurrentState().heading());
    } else if (isOutYMin(dy) && interDy == 0) {
      interDy = TurtleAnimatorImplementation.Y_MIN - turtle.getCurrentState().position().getY();
      interDx = Turtle.calculateXComponentGivenYComponentAngle(interDy, turtle.getCurrentState().heading());
    }

    return new Vector(interDx, interDy);
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

  private boolean isWithinBounds(double dx, double dy) {
    return turtle.getCurrentState().position().getX() + dx <= TurtleAnimatorImplementation.X_MAX
        && turtle.getCurrentState().position().getX() + dx >= TurtleAnimatorImplementation.X_MIN
        && turtle.getCurrentState().position().getY() + dy <= TurtleAnimatorImplementation.Y_MAX
        && turtle.getCurrentState().position().getY() + dy >= TurtleAnimatorImplementation.Y_MIN;
  }

  private TurtleStep normalMove(double dx, double dy) {
    Vector posChange = new Vector(dx, dy);
    TurtleStep step = new TurtleStep(turtle.getCurrentState(), posChange, 0);
    turtle.updateHistory(step, false);
    turtle.updateTurtleState(step);
    return step;
  }
}
