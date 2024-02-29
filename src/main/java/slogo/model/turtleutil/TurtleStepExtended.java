package slogo.model.turtleutil;

import slogo.model.api.turtle.TurtleStep;

/**
 * Custom wrapper class for TurtleStep with additional flag variable crossBorderIntermediateStep to keep track of
 * whether a step is an intermediate step when crossing border
 *
 * @author Judy He
 */
public class TurtleStepExtended {
  private TurtleStep turtleStep;
  private boolean crossBorderIntermediateStep;

  public TurtleStepExtended(TurtleStep turtleStep, boolean crossBorderIntermediateStep) {
    this.turtleStep = turtleStep;
    this.crossBorderIntermediateStep = crossBorderIntermediateStep;
  }

  public TurtleStep getTurtleStep() {
    return turtleStep;
  }

  public void setTurtleStep(TurtleStep turtleStep) {
    this.turtleStep = turtleStep;
  }

  public boolean isCrossBorderIntermediateStep() {
    return crossBorderIntermediateStep;
  }

  public void setCrossBorderIntermediateStep(boolean crossBorderIntermediateStep) {
    this.crossBorderIntermediateStep = crossBorderIntermediateStep;
  }
}
