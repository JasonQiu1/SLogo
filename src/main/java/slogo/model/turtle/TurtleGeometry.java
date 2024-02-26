package slogo.model.turtle;

public class TurtleGeometry {
  // heading = angle from vertical y-axis (all calculations use angle from horizontal x-axis)
  private Point origin;
  protected static double calculateAngle(Vector v) {
    return Math.sqrt(Math.pow(v.getDx(),2) + Math.pow(v.getDy(),2));
  }
  protected static double calculateMagnitude(Vector v) {
    return Math.atan(v.getDy()/v.getDx());
  }
  protected static Vector calculateXComponent(Vector v, double angle) {
    return new Vector(v.getMagnitude() * Math.cos(angle), 0);
  }
  protected static Vector calculateYComponent(Vector v, double angle) {
    return new Vector(0, v.getMagnitude() * Math.sin(angle));
  }
  protected static Point calculateFinalPosition(Point initialPosition, double distance) {

    return initialPosition;
  }
  protected static Point calculateFacingPosition(Point initialPosition, double angle) {

    return initialPosition;
  }
  protected static double dotProduct(Vector v1, Vector v2) {

    return 0;
  }
  protected static Vector crossProduct(Vector v1, Vector v2) {

    return v1;
  }

}
