package slogo.model.api.turtle;

import slogo.model.math.Vector;

/**
 * Represents a step taken by a turtle in a turtle graphics system.
 *
 * @param initialState     The initial state of the turtle before the step.
 * @param changeInPosition The change in position of the turtle after the step
 * @param changeInAngle    The change in angle of the turtle after the step
 * @author Judy He
 */
public record TurtleStep(TurtleState initialState, Vector changeInPosition, double changeInAngle) {

}
