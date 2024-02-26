package slogo.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;
import slogo.exception.model.turtle.InvalidPositionException;
import slogo.model.turtle.Point;
import slogo.model.turtle.Turtle;
import slogo.model.turtle.TurtleAnimator;
import slogo.model.turtle.TurtleState;
import slogo.model.turtle.TurtleStep;
import slogo.model.turtle.Vector;
import util.DukeApplicationTest;

public class TurtleTest extends DukeApplicationTest {
  private Turtle myTurtle;
  private TurtleAnimator myTurtleAnimator;
  @BeforeEach
  void setup () {
    myTurtle = new Turtle();
    myTurtleAnimator = new TurtleAnimator();
  }

  @Test
  void testNormalTurtleStepForward() {
    // fd 100
    TurtleStep step = myTurtle.doStep(100, 0);
    TurtleState expectedInitState = new TurtleState(new Point(0,0), 0);
    Vector expectedPositionChange = new Vector(0, 100);
    double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);
    double Xf = 0;
    double Yf = 100;
    double finalHeading = 0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check TurtleStep
    checkTurtleStep(expectedStep, step);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  @Test
  void testNormalTurtleStepRotate() {
    // rt 36
    TurtleStep step = myTurtle.doStep(0, 36);
    TurtleState expectedInitState = new TurtleState(new Point(0,0), 0);
    Vector expectedPositionChange = new Vector(0, 0);
    double expectedAngelChange = 36;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);
    double Xf = 0;
    double Yf = 0;
    double finalHeading = 36;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check TurtleStep
    checkTurtleStep(expectedStep, step);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());

  }

  @Test
  void testTurtleStepHistory() {
    List<TurtleStep> expectedStepHistory = new ArrayList<>();

    // step 1: fd 50
    TurtleState expectedInitState1 = new TurtleState(new Point(0,0), 0);
    myTurtle.doStep(50, 0);
    Vector expectedPositionChange1 = new Vector(0, 50);
    double expectedAngelChange1 = 0;
    TurtleStep expectedStep1 = new TurtleStep(expectedInitState1, expectedPositionChange1, expectedAngelChange1);
    expectedStepHistory.add(expectedStep1);
    double Xf1 = 0;
    double Yf1 = 50;
    TurtleState expectedFinalState1 = new TurtleState(new Point(Xf1, Yf1), 0);

    // step 2: rt 55
    TurtleState expectedInitState2 = myTurtle.getCurrentState();
    myTurtle.doStep(0, 55);
    Vector expectedPositionChange2 = new Vector(0, 0);
    double expectedAngelChange2 = 55;
    TurtleStep expectedStep2 = new TurtleStep(expectedInitState2, expectedPositionChange2, expectedAngelChange2);
    expectedStepHistory.add(expectedStep2);
    TurtleState expectedFinalState2 = new TurtleState(new Point(Xf1, Yf1), 55);

    // step 3: fd 100
    TurtleState expectedInitState3 = myTurtle.getCurrentState();
    myTurtle.doStep(100, 0);
    Vector expectedPositionChange3 = new Vector(100*Math.sin(myTurtle.getCurrentState().heading()), 100*Math.cos(myTurtle.getCurrentState().heading()));
    double expectedAngelChange3 = 0;
    TurtleStep expectedStep3 = new TurtleStep(expectedInitState3, expectedPositionChange3, expectedAngelChange3);
    expectedStepHistory.add(expectedStep3);
    double Xf3 = 100 * Math.sin(55);
    double Yf3 = 50 + 100 * Math.cos(55);
    double finalHeading = 55;
    TurtleState expectedFinalState3 = new TurtleState(new Point(Xf3, Yf3), finalHeading);

    // check TurtleStep history
    for (int i = 0; i < myTurtle.getStepHistory().size(); i++) {
      checkTurtleStep(expectedStepHistory.get(i), myTurtle.getStepHistory().get(i));
    }

    // check final state after step 1
    checkTurtleState(expectedFinalState1, expectedInitState2);

    // check final state after step 2
    checkTurtleState(expectedFinalState2, expectedInitState3);

    // check final state after step 3
    checkTurtleState(expectedFinalState3, myTurtle.getCurrentState());
  }

  @Test
  void testComplexTurtleStep() {
    myTurtle.doStep(50, 0);
    myTurtle.doStep(0, 50);
    myTurtle.doStep(115, 0);
    myTurtle.doStep(0, -36);
    myTurtle.doStep(-75, 0);
    myTurtle.doStep(0, 245);
    myTurtle.doStep(200, 0);
    myTurtle.doStep(-39, 0);
    myTurtle.doStep(0, 10);
    myTurtle.doStep(50, 0);

    double Xf = -138.0833925041;
    double Yf = 19.5555270658;
    double finalHeading = 269;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  @Test
  void testWrapEdge() {
    // step 1: fd 100
    myTurtle.doStep(100, 0);
    // step 2: rt 75
    myTurtle.doStep(0, 75);
    // step 3: fd 400
    TurtleState expectedInitState3 = myTurtle.getCurrentState();
    TurtleStep step3 = myTurtle.doStep(400, 0);
    Vector expectedPositionChange3 = new Vector(400*Math.sin(myTurtle.getCurrentState().heading()), 400*Math.cos(myTurtle.getCurrentState().heading()));
    double expectedAngelChange3 = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState3, expectedPositionChange3, expectedAngelChange3);

    double Xf3 = 400 * Math.sin(75) - 200;
    double Yf3 = 100 + 400 * Math.cos(55);
    double finalHeading = 75;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf3, Yf3), finalHeading);

    // check TurtleStep after step 3
    checkTurtleStep(expectedStep, step3);
    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  @Test
  void testIntermediateStatesSpeed1() {
    myTurtleAnimator.setSpeed(1);

    prepareMove();

    List<TurtleState> expectedInterStates = new ArrayList<>();

    TurtleState state1 = new TurtleState(new Point(0,1),0);
    TurtleState state2 = new TurtleState(new Point(0,2),0);
    TurtleState state3 = new TurtleState(new Point(0,3),0);
    expectedInterStates.add(state1);
    expectedInterStates.add(state2);
    expectedInterStates.add(state3);

    checkInterStates(expectedInterStates);

  }

  @Test
  void testIntermediateStatesSpeedHalf() {
    myTurtleAnimator.setSpeed(0.5);

    prepareMove();

    List<TurtleState> expectedInterStates = new ArrayList<>();

    TurtleState state1 = new TurtleState(new Point(0,0.5),0);
    TurtleState state2 = new TurtleState(new Point(0,1),0);
    TurtleState state3 = new TurtleState(new Point(0,1.5),0);
    TurtleState state4 = new TurtleState(new Point(0,2),0);
    TurtleState state5 = new TurtleState(new Point(0,2.5),0);
    TurtleState state6 = new TurtleState(new Point(0,3),0);
    expectedInterStates.add(state1);
    expectedInterStates.add(state2);
    expectedInterStates.add(state3);
    expectedInterStates.add(state4);
    expectedInterStates.add(state5);
    expectedInterStates.add(state6);

    checkInterStates(expectedInterStates);

  }

  void testIntermediateAnglesSpeed5() {
    myTurtleAnimator.setSpeed(1);

    prepareRotate();

    List<TurtleState> expectedInterStates = new ArrayList<>();

    TurtleState state1 = new TurtleState(new Point(0,0),20);
    TurtleState state2 = new TurtleState(new Point(0,0),40);
    TurtleState state3 = new TurtleState(new Point(0,0),60);
    TurtleState state4 = new TurtleState(new Point(0,0),80);
    TurtleState state5 = new TurtleState(new Point(0,0),100);

    expectedInterStates.add(state1);
    expectedInterStates.add(state2);
    expectedInterStates.add(state3);
    expectedInterStates.add(state4);
    expectedInterStates.add(state5);


    checkInterStates(expectedInterStates);

  }

  @Test
  void testStepForward() {
    // step 1: fd 200
    myTurtle.doStep(200, 0);

    // step 2: rt 150
    myTurtle.doStep(0, 150);

    // step 3: fd -50
    myTurtle.doStep(-50, 0);

    // step 4: rt 25
    myTurtle.doStep(0, 25);

    // step 5: fd 100
    myTurtle.doStep(100, 0);

    myTurtle.stepBack();
    myTurtle.stepBack();
    myTurtle.stepBack();

    TurtleStep forwardStep = myTurtle.stepForward();

    TurtleState expectedInitState = new TurtleState(new Point(0, 200), 150);
    Vector expectedPositionChange = new Vector(-50*Math.sin(30), 50*Math.cos(30));
    double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    double Xf = -25;
    double Yf = 243.3012701892;
    double finalHeading = 150;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check TurtleStep
    checkTurtleStep(expectedStep, forwardStep);
    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());


  }

  @Test
  void testStepBackward() {
    // step 1: fd 200
    myTurtle.doStep(200, 0);

    // step 2: rt 150
    myTurtle.doStep(0, 150);

    // step 3: fd -50
    myTurtle.doStep(-50, 0);

    TurtleStep backwardStep = myTurtle.stepBack();

    TurtleState expectedInitState = new TurtleState(new Point(-25, 243.3012701892), 150);
    Vector expectedPositionChange = new Vector(50*Math.sin(30), -50*Math.cos(30));
    double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    double Xf = 0;
    double Yf = 200;
    double finalHeading = 150;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check TurtleStep
    checkTurtleStep(expectedStep, backwardStep);
    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());

  }

  @Test
  void testSetHeading() {
    myTurtle.doStep(0, 150);
    myTurtle.doStep(0, 85);
    TurtleStep step = myTurtle.setHeading(45);

    TurtleState expectedInitState = new TurtleState(new Point(0, 0), 235);
    Vector expectedPositionChange = new Vector(0,0);
    double expectedAngelChange = -190;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    // check TurtleStep
    checkTurtleStep(expectedStep, expectedStep);
  }

  @Test
  void testSetPosition() {
    myTurtle.doStep(130, 0);
    myTurtle.doStep(-70, 0);
    TurtleStep step = myTurtle.setPosition(new Point(45,45));

    TurtleState expectedInitState = new TurtleState(new Point(0, 60), 0);
    Vector expectedPositionChange = new Vector(45,-15);
    double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    // check TurtleStep
    checkTurtleStep(expectedStep, expectedStep);
  }

  @Test
  void testSetInvalidPosition() {
    assertThrows(InvalidPositionException.class, () -> myTurtle.setPosition(new Point(400,400)));
  }

  @Test
  void testResetTurtle() {
    myTurtle.doStep(50, 0);
    myTurtle.doStep(0, 65);
    myTurtle.doStep(-200, 0);
    List<Turtle> turtles = new ArrayList<>();
    turtles.add(myTurtle);
    TurtleAnimator.resetTurtles(turtles);

    double Xf = 0;
    double Yf = 0;
    double finalHeading = 0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  void prepareMove() {
    Point initPos = new Point(0,0);
    double angle = 0;
    TurtleState initState = new TurtleState(initPos, angle);
    Vector posChange = new Vector(0, 100);
    double angleChange = 0;
    TurtleStep step = new TurtleStep(initState, posChange, angleChange);
    Map<Integer, TurtleStep> turtles = new HashMap<>();
    turtles.put(0, step);
    TurtleAnimator.animateSteps(turtles);
  }

  void prepareRotate() {
    Point initPos = new Point(0,0);
    double angle = 0;
    TurtleState initState = new TurtleState(initPos, angle);
    Vector posChange = new Vector(0, 0);
    double angleChange = 100;
    TurtleStep step = new TurtleStep(initState, posChange, angleChange);
    Map<Integer, TurtleStep> turtles = new HashMap<>();
    turtles.put(0, step);
    TurtleAnimator.animateSteps(turtles);
  }

  void checkInterStates(List<TurtleState> expectedInterStates) {
    for (Integer id: myTurtleAnimator.getIntermediateStates().keySet()) {
      List<TurtleState> states = myTurtleAnimator.getIntermediateStates().get(id);
      Iterator<TurtleState> iter = states.iterator();
      Iterator<TurtleState> expectedIter = expectedInterStates.iterator();
      while (iter.hasNext() && expectedIter.hasNext()) {
        TurtleState state = iter.next();
        TurtleState expected = expectedIter.next();
        assertEquals(state.position(), expected.position());
        assertEquals(state.heading(), expected.heading());
      }
    }

  }

  void checkTurtleStep(TurtleStep expected, TurtleStep step) {
    assertEquals(expected.initialState().position(), step.initialState().position());
    assertEquals(expected.initialState().heading(), step.initialState().heading());
    assertEquals(expected.changeInPosition(), step.changeInPosition());
    assertEquals(expected.changeInAngle(), step.changeInAngle());
  }

  void checkTurtleState(TurtleState expected, TurtleState state) {
    assertEquals(expected.position(), state.position());
    assertEquals(expected.heading(), state.heading());
  }


}
