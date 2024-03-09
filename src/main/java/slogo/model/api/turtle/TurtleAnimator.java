package slogo.model.api.turtle;

import java.util.List;
import java.util.Map;

/**
 * The TurtleAnimator interface manages the animation of turtles.
 *
 * @author Judy He
 */
public interface TurtleAnimator {

  /**
   * Returns the intermediate states of all turtles.
   * @return a map of turtle IDs to lists of turtle states representing intermediate animation states
   */
  Map<Integer, List<TurtleState>> getIntermediateStates();

  /**
   * Returns the initial state of the turtle.
   * @return the initial state of the turtle
   */
  TurtleState getInitialTurtleState();

  /**
   * Returns the graphics scaling factor.
   * @return the graphics scaling factor
   */
  double getGraphicsScalingFactor();

  /**
   * Returns the speed of the turtle.
   * @return the speed of the turtle
   */
  double getSpeed();

  /**
   * Sets the speed of the turtle.
   * @param speed the new speed of the turtle
   */
  void setSpeed(double speed);

  /**
   * Animates the movement of turtles based on given steps.
   * @param eachTurtlesStep a map of turtle IDs to lists of turtle steps to animate
   */
  void animateStep(Map<Integer, List<TurtleStep>> eachTurtlesStep);

  /**
   * Calculates the next animation frame.
   * @return a map of turtle IDs to the next turtle state in the animation
   */
  Map<Integer, TurtleState> nextFrame();

  /**
   * Calculates the previous animation frame.
   * @return a map of turtle IDs to the previous turtle state in the animation
   */
  Map<Integer, TurtleState> previousFrame();

  /**
   * Resets the animation frame to the beginning.
   * @return a map of turtle IDs to their initial turtle state
   */
  Map<Integer, TurtleState> resetFrame();

  /**
   * Resets the animation frame back a given number of frames.
   * @param frames the number of frames to reset back
   * @return a map of turtle IDs to the turtle states after resetting
   */
  Map<Integer, TurtleState> resetFrame(int frames);
}

