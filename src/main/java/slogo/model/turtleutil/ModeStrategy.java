package slogo.model.turtleutil;

import java.util.List;
import slogo.model.api.turtle.TurtleStep;

/**
 * Interface for mode strategies.
 *
 * @author Judy He
 */
public interface ModeStrategy {

  /**
   * Moves the turtle by the specified distance given a mode strategy.
   *
   * @param turtle   the turtle to be moved
   * @param distance the distance to move
   * @return the intermediate steps representing the movement
   */
  List<TurtleStep> move(Turtle turtle, double distance);
}
