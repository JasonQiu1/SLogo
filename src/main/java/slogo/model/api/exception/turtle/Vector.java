<<<<<<<< Updated upstream:src/main/java/slogo/model/api/turtle/Vector.java
package slogo.model.api.turtle;

import slogo.model.turtleUtil.TurtleGeometry;
========
package slogo.exception.model.turtle;

import slogo.model.turtle.TurtleGeometry;
>>>>>>>> Stashed changes:src/main/java/slogo/model/api/exception/turtle/Vector.java

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

  public void setDx(double dx) {
    this.dx = dx;
  }

  public double getDy() {
    return dy;
  }

  public void setDy(double dy) {
    this.dy = dy;
  }

  public double getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(double magnitude) {
    this.magnitude = magnitude;
  }

  public double getDirection() {
    return direction;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }
  public boolean equals(Vector vector) {
    return this.getMagnitude() == vector.getMagnitude() && this.getDirection() == vector.getDirection();
  }

}
