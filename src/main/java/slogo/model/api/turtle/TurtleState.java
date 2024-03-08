package slogo.model.api.turtle;

import slogo.model.math.Point;

/**
 * Represents the state of a turtle consisted of its position and heading
 *
 * @param position position of the turtle in the 2D coordinate system
 * @param heading heading of the turtle in degrees, measured from the vertical axis
 *
 * @author Judy He
 */
public record TurtleState(Point position, double heading) {

}
