# Model
Judy He
### TurtleAnimator

This class's purpose or value is to manage the turtle animation
```Java
public class TurtleAnimator {
  // return height of the window
  public static double getHeight()
  // return width of the window
  public static double getWidth()
  // return the graphics scaling factor the animation window should use
  public static double getGraphicsScalingFactor()
  // return the intermediate states of the turtle 
  public Map<Integer, List<TurtleState>> getIntermediateStates()
  // return initial state of turtles
  public static TurtleState getInitialTurtleState()
  // return the animation speed
  public static double getSpeed()
  // update the animation speed
  public static void setSpeed(double speed)
  // calculate the list of TurtleState needed to smoothly move the turtle for its given TurtleStep
  public static void animateSteps(Map<int, TurtleStep> eachTurtlesStep)
  // return the list of TurtleState needed to smoothly move the turtle(s) a step forward in the animation
  public static Map<int, List<TurtleStates>> stepForward()
  // return the list of TurtleState needed to smoothly move the turtle(s) a step backward in the animation
  public static Map<int, List<TurtleStates>> stepBackward()
  // reset turtles' state
  public static boolean resetTurtles(List<Turtle>)
  // check if turtle is within the animation window after a step
  protected static boolean checkInBound(Point position)
}
```