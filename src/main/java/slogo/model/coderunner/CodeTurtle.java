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
    return turtle.getCurrentState().position().getY();
  }

  public double getHeading() {
    return turtle.getCurrentState().heading();
  }

  /**
   * Returns the distance traveled going to (x,y)
   *
   * @param x target x coord
   * @param y target y coord
   * @return the distance traveled
   */
  public double setPosition(double x, double y) {
    return turtle.setXY(new Point(x, y)).changeInPosition().getMagnitude();
  }

  /**
   * Returns the degrees rotated to reach the target heading
   * @param degrees target heading
   * @return the degrees rotated
   */
  public double setHeading(double degrees) {
    return turtle.setHeading(degrees).changeInAngle();
  }

  /**
   * Returns the degrees rotated to turn towards (x,y)
   * @param x target x coord
   * @param y target y coord
   * @return the degrees rotated
   */
  public double turnTowards(double x, double y) {
    return turtle.turnTowards(new Point(x, y)).changeInAngle();
  }

  /**
   * Returns the distance traveled to move in the direction of the current heading
   * @param pixels the number of pixels to travel
   * @return the distance traveled
   */
  public double move(double pixels) {
    List<TurtleStep> steps = turtle.move(pixels);
    double distanceMoved = 0;
    for (TurtleStep step : steps) {
      distanceMoved += step.changeInPosition().getMagnitude();
    }
    return distanceMoved;
  }

  /**
   * Returns the degrees rotated after rotating the given amount
   * @param degrees the number of degrees to rotate
   * @return the degrees rotated
   */
  public double rotate(double degrees) {
    return turtle.rotate(degrees).changeInAngle();
  }

  private final Turtle turtle;
}
