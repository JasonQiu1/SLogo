package slogo.model.turtleutil;

import slogo.model.api.turtle.TurtleStep;

/**
 * Custom wrapper class for TurtleStep with additional flag variable crossBorderIntermediateStep to
 * keep track of whether a step is an intermediate step when crossing border. This supports Wrap
 * mode
 *
 * @param turtleStep                  the step taken by the turtle
 * @param crossBorderIntermediateStep flag indicating whether the step is an intermediate step due
 *                                    to border crossing
 * @author Judy He
 */
public record TurtleStepExtended(TurtleStep turtleStep, boolean crossBorderIntermediateStep) {

}
