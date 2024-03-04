package slogo.model.coderunner;

import java.util.List;
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
