package slogo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import slogo.model.turtle.Point;
import slogo.model.turtle.Turtle;
import slogo.model.turtle.TurtleState;
import slogo.model.turtle.TurtleStep;
import slogo.model.turtle.Vector;
import util.DukeApplicationTest;

public class TurtleTest extends DukeApplicationTest {
  private Turtle myTurtle;
  @BeforeEach
  void setup () {
    myTurtle = new Turtle();
  }

  @Test
  void testNormalTurtleStepForward() {
    TurtleStep result = myTurtle.doStep(100, 0);
    TurtleState expectedInitState = new TurtleState(new Point(0,0), 0);
    Vector expectedPositionChange = new Vector(0, 100);
    double expectedAngelChange = 0;

    assertEquals(expectedInitState.position(), result.initialState().position());
    assertEquals(expectedInitState.heading(), result.initialState().heading());
    assertEquals(expectedPositionChange, result.changeInPosition());
    assertEquals(expectedAngelChange, result.changeInAngle());
  }

  @Test
  void testNormalTurtleStepRotate() {
    TurtleStep result = myTurtle.doStep(0, 36);
    TurtleState expectedInitState = new TurtleState(new Point(0,0), 0);
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
