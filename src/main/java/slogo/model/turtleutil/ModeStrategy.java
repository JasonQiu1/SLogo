package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleStep;

public interface Mode {
  public List<TurtleStep> move(Turtle turtle, double distance);
}
