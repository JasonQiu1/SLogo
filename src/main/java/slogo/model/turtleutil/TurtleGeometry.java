package slogo.model.turtleutil;

import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.Vector;

public class TurtleGeometry {
  // heading = angle from vertical y-axis (all calculations use angle from horizontal x-axis)
  private Point origin;
  public static double calculateAngle(Vector v) {
    return Math.sqrt(Math.pow(v.getDx(),2) + Math.pow(v.getDy(),2));
  }
  public static double calculateMagnitude(Vector v) {
    return Math.atan(v.getDy()/v.getDx());
  }
  protected static double calculateXComponent(double magnitude, double angle) {
    return magnitude * Math.cos(angle);
  }
  protected static double calculateYComponent(double magnitude, double angle) {
    return magnitude * Math.sin(angle);
  }
  protected static Vector getVectorBetweenTwoPoints(Point p1, Point p2) {
    double dx = p2.getX() - p1.getX();
    double dy = p2.getY() - p1.getY();
    return new Vector(dx, dy);
  }
  protected static double getAngleChange(double a1, double a2) {
    return a2 - a1;
  }
  protected static Point calculateFinalPosition(Point initialPosition, Vector vector) {
    double xFinal = initialPosition.getX() + vector.getDx();
    double yFinal = initialPosition.getY() + vector.getDy();

    return new Point(xFinal, yFinal);
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
