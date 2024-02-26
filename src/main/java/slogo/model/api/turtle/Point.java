package slogo.model.api.turtle;

public class Point {
  private double x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object obj) {
    Point point = (Point) obj;
    return this.x == point.x && this.y == point.y;
  }
}
