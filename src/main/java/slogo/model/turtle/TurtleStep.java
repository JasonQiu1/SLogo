package slogo.model.turtle;

public record TurtleStep() {

  private static TurtleState initialState;
  private static Vector changeInPosition;
  private static double changeInAngle;

  public static TurtleState getInitialState() {
    return initialState;
  }

  public static void setInitialState(TurtleState initialState) {
    TurtleStep.initialState = initialState;
  }

  public static Vector getChangeInPosition() {
    return changeInPosition;
  }

  public static void setChangeInPosition(Vector changeInPosition) {
    TurtleStep.changeInPosition = changeInPosition;
  }

  public static double getChangeInAngle() {
    return changeInAngle;
  }

  public static void setChangeInAngle(double changeInAngle) {
    TurtleStep.changeInAngle = changeInAngle;
  }
}
