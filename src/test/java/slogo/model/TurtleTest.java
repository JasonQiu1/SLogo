package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.api.turtle.Vector;
import slogo.model.turtleutil.Turtle;
import util.DukeApplicationTest;

public class TurtleTest extends DukeApplicationTest {

  private Turtle myTurtle;

  @BeforeEach
  void setup() {
    myTurtle = new Turtle();
  }

  @Test
  void testNormalTurtleStepForward() {
    TurtleStep result = myTurtle.move(100).get(0);
    TurtleState expectedInitState = new TurtleState(new Point(0, 0), 0);
    Vector expectedPositionChange = new Vector(0, 100);
    double expectedAngelChange = 0;

    assertEquals(expectedInitState.position(), result.initialState().position());
    assertEquals(expectedInitState.heading(), result.initialState().heading());
    assertEquals(expectedPositionChange, result.changeInPosition());
    assertEquals(expectedAngelChange, result.changeInAngle());
  }

  @Test
  void testNormalTurtleStepRotate() {
    TurtleStep result = myTurtle.rotate(36);
    TurtleState expectedInitState = new TurtleState(new Point(0, 0), 0);
    Vector expectedPositionChange = new Vector(0, 100);
    double expectedAngelChange = 36;

    assertEquals(expectedInitState.position(), result.initialState().position());
    assertEquals(expectedInitState.heading(), result.initialState().heading());
    assertEquals(expectedPositionChange, result.changeInPosition());
    assertEquals(expectedAngelChange, result.changeInAngle());

  }

  @Test
  void testOutOfBoundWrapStep() {
  }

  @Test
  void testIntermediateStatesSpeed1() {

  }

  @Test
  void testIntermediateStatesSpeed5() {

  }

  @Test
  void testIntermediateStatesSpeed10() {

  }

  @Test
  void testStepForward() {

  }

  @Test
  void testStepBackward() {

  }

  @Test
  void testSetHeading() {

  }

  @Test
  void testSetInvalidPosition() {

  }


}
