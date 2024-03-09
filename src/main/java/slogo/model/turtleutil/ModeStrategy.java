package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleStep;


public interface ModeStrategy {

  /**
   * Moves the turtle by the specified distance.
   *
   * @param turtle the turtle to be moved
   * @param distance the distance to move
   * @return the intermediate steps representing the movement
   */
  List<TurtleStep> move(Turtle turtle, double distance);
}
