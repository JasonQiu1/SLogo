package slogo.model.turtleutil;

import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.api.exception.UnsupportedTurtleCommandException;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.api.turtle.Vector;

/**
 * The Turtle class represents a turtle in the animation. It keeps track of the turtle's id, step history and current point in history, and current state (position and heading).
 * It updates the turtle's state and returns the corresponding step that the TurtleAnimator and View can interpret after a command is received
 *
 * @author Judy He
 */
public class Turtle {
  private int id;
  private TurtleState currentState; // heading = angle from vertical y-axis (all calculations use angle from horizontal x-axis)
  private List<TurtleStep> stepHistory;
  private int currentPointInStepHistory;
  private static final String FORWARD_COMMAND = "fd";
  private static final String ROTATE_COMMAND = "rt";
  private static final String SET_HEADING_COMMAND = "setheading";
  private static final String SET_XY_COMMAND = "setxy";
  private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.model.";
  private ResourceBundle errorResourceBundle; // resource bundle for error handling messages

  public Turtle() {
    this.id = 0;
    this.currentState = TurtleAnimator.getInitialTurtleState();
    this.stepHistory = new ArrayList<>();
    this.currentPointInStepHistory= 0;
    this.errorResourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ErrorsEnglish");
  }

  public int getId() {
    return id;
  }

  public TurtleState getCurrentState() {
    return currentState;
  }

  public List<TurtleStep> getStepHistory() {
    return stepHistory;
  }

  // return the step turtle is currently on in its step history
  protected int getCurrentPointInStepHistory() {
    return currentPointInStepHistory;

  }

  // update the step turtle is currently on in its step history
  protected void setCurrentPointInStepHistory(int currentPointInStepHistory) {
    this.currentPointInStepHistory = currentPointInStepHistory;
  }

  // update turtle's position/heading and return turtle's final position/heading
  public <T> TurtleStep doStep (String command, T value) throws UnsupportedTurtleCommandException {
    switch (command) {
      case FORWARD_COMMAND -> {
        return move((double) value);
      }
      case ROTATE_COMMAND -> {
        return rotate((double) value);
      }
      case SET_HEADING_COMMAND -> {
        return setHeading((double) value);
      }
      case SET_XY_COMMAND -> {
        return setXY((Point) value);
      }
      default -> throw new UnsupportedTurtleCommandException(errorResourceBundle.getString("UnsupportedTurtleCommand"));

    }
  }

  // return turtle's heading towards given point
  public double getHeadingTowards(Point point) {
    // TODO
    return 0;
  }

  public TurtleStep stepForward() {
    TurtleState startingState = this.currentState;

    return null;
  }

  public TurtleStep stepBack() {
    // TODO
    return null;
  }

  private TurtleStep setHeading (double degrees) {
    TurtleStep step = new TurtleStep(this.currentState, new Vector(0,0), degrees);

    this.currentState = new TurtleState(this.currentState.position(), degrees);
    return step;
  }

  private TurtleStep setXY (Point position) throws InvalidPositionException {
    if (TurtleAnimator.mode.equals("window")) {
      if (position.getX() > TurtleAnimator.getWidth() / 2 || position.getY() > TurtleAnimator.getHeight() / 2 || position.getX() < - TurtleAnimator.getWidth() / 2 || position.getY() < - TurtleAnimator.getHeight() / 2) {
        throw new InvalidPositionException(errorResourceBundle.getString("InvalidPosition"));
      }
    }
    Vector posChange = TurtleGeometry.getVectorBetweenTwoPoints(this.currentState.position(), position);
    TurtleStep step = new TurtleStep(this.currentState, posChange, 0);

    this.currentState = new TurtleState(position, this.currentState.heading());
    return step;
  }

  // reset position
  private void reset(TurtleState initialState) {
    this.currentState = initialState;
  }

  private TurtleStep rotate(double angle) {

    // TODO



  }
  private TurtleStep move(double distance) {
    // TODO

  }

}
