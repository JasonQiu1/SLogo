package slogo.model.api.turtle;

import slogo.model.math.Vector;

public record TurtleStep(TurtleState initialState, Vector changeInPosition, double changeInAngle) { }
