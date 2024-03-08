package slogo.model.turtleutil;

import slogo.model.api.turtle.TurtleStep;

/**
 * Custom wrapper class for TurtleStep with additional flag variable crossBorderIntermediateStep to
 * keep track of whether a step is an intermediate step when crossing border.
 * This supports Wrap mode
 *
 * @author Judy He
 */
public record TurtleStepExtended(TurtleStep turtleStep, boolean crossBorderIntermediateStep) {

}
