package slogo.model.api.turtle;

public record TurtleStep(TurtleState initialState, Vector changeInPosition, double changeInAngle) { }
