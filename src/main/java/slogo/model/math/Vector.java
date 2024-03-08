package slogo.model.math;

/**
 * Represents a 2D vector with components dx and dy.
 *
 * @author Judy He
 */
public record Vector(double dx, double dy) {

  /**
   * Calculates the magnitude of the vector.
   *
   * @return the magnitude of the vector
   */
  public double getMagnitude() {
    return Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Calculates the direction of the vector in degrees.
   *
   * @return the direction of the vector in degrees
   */
  public double getDirection() {
    if (dy == 0 || dx == 0) {
      return 0;
    }
    return Math.toDegrees(Math.atan(dx / dy));
  }

  /**
   * Checks if this vector is equal to another object.
   *
   * @param other the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Vector otherVector = (Vector) other;
    return Double.compare(otherVector.dx, dx) == 0 &&
        Double.compare(otherVector.dy, dy) == 0;
  }

}
