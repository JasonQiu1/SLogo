package slogo.model.coderunner;

import java.util.List;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.turtleutil.Turtle;

/**
 * Wrapper for turtle class for ease of use in getting distance moved and angles rotated when
 * sending move and rotate calls.
 *
 * @author Jason Qiu
 */
public class CodeTurtle {

  public CodeTurtle(Turtle turtle) {
    this.turtle = turtle;
  }

  public int getId() {
    return turtle.getId();
  }

  public double getX() {
    return turtle.getCurrentState().position().getX();
  }

  public double getY() {
    return turtle.getCurrentState().position().getX();
  }

  public double getHeading() {
    return turtle.getCurrentState().heading();
  }

  public double setPosition(double x, double y) {
    return turtle.setXY(new Point(x, y)).changeInPosition().getMagnitude();
  }

  public double setHeading(double degrees) {
    return turtle.setHeading(degrees).changeInAngle();
  }

  public double turnTowards(double x, double y) {
    return turtle.turnTowards(new Point(x, y)).changeInAngle();
  }

  public double move(double pixels) {
    List<TurtleStep> steps = turtle.move(pixels);
    double distanceMoved = 0;
    for (TurtleStep step : steps) {
      distanceMoved += step.changeInPosition().getMagnitude();
    }
    return distanceMoved;
  }

  public double rotate(double degrees) {
    return turtle.rotate(degrees).changeInAngle();
  }

  private final Turtle turtle;
}
