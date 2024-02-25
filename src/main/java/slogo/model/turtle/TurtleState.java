package slogo.model.turtle;

public record TurtleState() {

  private static Point position;
  private static double heading;

  public static Point getPosition() {
    return position;
  }

  public static void setPosition(Point position) {
    TurtleState.position = position;
  }

  public static double getHeading() {
    return heading;
  }

  public static void setHeading(double heading) {
    TurtleState.heading = heading;
  }

}
