package slogo.model.turtleutil;

import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.Vector;

public class TurtleGeometry {
  // heading = angle from vertical y-axis (all calculations use angle from horizontal x-axis)
  public static double calculateAngle(Vector v) {
    if (v.getDy() == 0 || v.getDx() == 0) return 0;
    return Math.toDegrees(Math.atan(v.getDx()/v.getDy())); // angle from vertical
  }
  public static double calculateMagnitude(Vector v) {
    return Math.sqrt(Math.pow(v.getDx(),2) + Math.pow(v.getDy(),2));
  }
  public static double calculateYComponentGivenXComponentAngle(double x, double angle) {
    double newMagnitude = x / Math.sin(Math.toRadians(angle));
    return newMagnitude * Math.cos(Math.toRadians(angle));
  }
  public static double calculateXComponentGivenYComponentAngle(double y, double angle) {
    double newMagnitude = y / Math.cos(Math.toRadians(angle));
    return newMagnitude * Math.sin(Math.toRadians(angle));
  }
  protected static double calculateXComponent(double magnitude, double angle) {
    return magnitude * Math.sin(Math.toRadians(angle));
  }
  protected static double calculateYComponent(double magnitude, double angle) {
    return magnitude * Math.cos(Math.toRadians(angle));
  }
  protected static Vector getVectorBetweenTwoPoints(Point p1, Point p2) {
    double dx = p2.getX() - p1.getX();
    double dy = p2.getY() - p1.getY();
    return new Vector(dx, dy);
  }
  protected static double getAngleChange(double a1, double a2) {
    return a2 - a1;
  }
  public static Point calculateFinalPosition(Point initialPosition, Vector vector) {
    double xFinal = initialPosition.getX() + vector.getDx();
    double yFinal = initialPosition.getY() + vector.getDy();

    return new Point(xFinal, yFinal);
  }
//  protected static Point calculateFacingPosition(Point initialPosition, double angle) {
//
//    return initialPosition;
//  }
//
////  protected static double getAngleBetweenTwoVectors(Vector v1, Vector v2) {
////    double dotProduct = dotProduct(v1, v2);
////    double magProduct = v1.getMagnitude() * v2.getMagnitude();
////    return Math.toDegrees(Math.acos(dotProduct / magProduct));
////  }
//
//  private static double dotProduct(Vector v1, Vector v2) {
//    return v1.getDx() * v2.getDx() + v1.getDy() * v2.getDy();
//  }
//  private static Vector crossProduct(Vector v1, Vector v2) {
//
//    return v1;
//  }

}
