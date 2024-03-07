package slogo.model.api.turtle;

import slogo.model.turtleutil.TurtleGeometry;

public class Vector {

  private double dx;
  private double dy;
  private double magnitude; // length
  private double direction; // angle

  public Vector(double dx, double dy) {
    this.dx = dx;
    this.dy = dy;
    this.magnitude = TurtleGeometry.calculateMagnitude(this);
    this.direction = TurtleGeometry.calculateAngle(this);
  }

  public double getDx() {
    return dx;
  }

  public double getDy() {
    return dy;
  }

  public double getMagnitude() {
    return magnitude;
  }

  public double getDirection() {
    return direction;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (getClass() != other.getClass()) {
      return false;
    }
    Vector otherVector = (Vector) other;
    return this.getMagnitude() == otherVector.getMagnitude()
        && this.getDirection() == otherVector.getDirection();
  }

}
