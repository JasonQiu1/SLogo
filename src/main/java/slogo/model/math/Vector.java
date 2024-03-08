package slogo.model.api.turtle;

public class Vector {

  private double dx;
  private double dy;
  private double magnitude; // length
  private double direction; // angle

  public Vector(double dx, double dy) {
    this.dx = dx;
    this.dy = dy;
    this.magnitude = this.calculateMagnitude(this);
    this.direction = this.calculateAngle(this);
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

  private double calculateAngle(Vector v) {
    if (v.getDy() == 0 || v.getDx() == 0) return 0;
    return Math.toDegrees(Math.atan(v.getDx()/v.getDy()));
  }

  private double calculateMagnitude(Vector v) {
    return Math.sqrt(Math.pow(v.getDx(),2) + Math.pow(v.getDy(),2));
  }

}
