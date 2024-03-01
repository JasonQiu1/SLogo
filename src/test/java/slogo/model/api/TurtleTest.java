package slogo.model.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.api.turtle.Vector;
import slogo.model.turtleutil.Turtle;

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
    List<TurtleStep> steps = myTurtle.move(50);
    final TurtleState expectedInitState = new TurtleState(new Point(0,0), 0);
    final Vector expectedPositionChange = new slogo.model.api.turtle.Vector(0, 50);
    final double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);
    double Xf = 0;
    double Yf = 50;
    double finalHeading = 0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    for (TurtleStep step: steps) {
      // check TurtleStep
      checkTurtleStep(expectedStep, step);
    }

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  @Test
  void testNormalTurtleStepRotate() {
    // rt 36
    TurtleStep step = myTurtle.rotate(36);
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

    // step 1: fd 50
    TurtleState expectedInitState1 = new TurtleState(new Point(0,0), 0);
    myTurtle.move(50);
    Vector expectedPositionChange1 = new Vector(0, 50);
    double expectedAngelChange1 = 0;
    TurtleStep expectedStep1 = new TurtleStep(expectedInitState1, expectedPositionChange1, expectedAngelChange1);
    double Xf1 = 0;
    double Yf1 = 50;
    TurtleState expectedFinalState1 = new TurtleState(new Point(Xf1, Yf1), 0);

    // step 2: rt 55
    TurtleState expectedInitState2 = new TurtleState(new Point(0,50),0);
    myTurtle.rotate(55);
    Vector expectedPositionChange2 = new Vector(0, 0);
    double expectedAngelChange2 = 55;
    TurtleStep expectedStep2 = new TurtleStep(expectedInitState2, expectedPositionChange2, expectedAngelChange2);
    TurtleState expectedFinalState2 = new TurtleState(new Point(Xf1, Yf1), 55);

    // step 3: fd 100
    TurtleState expectedInitState3 =new TurtleState(new Point(0,50),55);
    myTurtle.move(100);
    Vector expectedPositionChange3 = new Vector(100*Math.sin(Math.toRadians(55)), 100*Math.cos(Math.toRadians(55)));
    double expectedAngelChange3 = 0;
    TurtleStep expectedStep3 = new TurtleStep(expectedInitState3, expectedPositionChange3, expectedAngelChange3);
    double Xf3 = 100 * Math.sin(Math.toRadians(55));
    double Yf3 = 50 + 100 * Math.cos(Math.toRadians(55));
    double finalHeading = 55;
    TurtleState expectedFinalState3 = new TurtleState(new Point(Xf3, Yf3), finalHeading);

    // check TurtleStep history
    List<TurtleStep> expectedStepHistory = List.of(expectedStep1, expectedStep2, expectedStep3);
    for (int i = 0; i < myTurtle.getStepHistory().size(); i++) {
      checkTurtleStep(expectedStepHistory.get(i), myTurtle.getStepHistory().get(i).turtleStep());
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
    myTurtle.move(50);
    myTurtle.rotate(50);
    myTurtle.move(115);
    myTurtle.rotate(-36);
    myTurtle.move(-75);
    myTurtle.rotate(245);
    myTurtle.move(200);
    myTurtle.move(-39);
    myTurtle.rotate(10);
    myTurtle.move(50);

    double Xf = -138.0833925041861;
    double Yf = 19.55552706576443;
    double finalHeading = 269.0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  @Test
  void testWrapEdge() {
    // step 1: rt 75
    myTurtle.rotate(75);
    // step 2: fd 400
    TurtleState expectedInitState1 = new TurtleState(new Point(0,0), 75);
    List<TurtleStep> steps = myTurtle.move(400);
    Vector expectedPositionChange1 = new Vector(200, 200/Math.sin(Math.toRadians(75)) * Math.cos(Math.toRadians(75)));
    double expectedAngelChange1 = 0;
    TurtleStep expectedStep1 = new TurtleStep(expectedInitState1, expectedPositionChange1, expectedAngelChange1);

    TurtleState expectedInitState2 = new TurtleState(new Point(-200,200/Math.sin(Math.toRadians(75)) * Math.cos(Math.toRadians(75))), 75);
    Vector expectedPositionChange2 = new Vector(400 * Math.sin(Math.toRadians(75)) - 200, 400 * Math.cos(Math.toRadians(75)) - expectedPositionChange1.getDy());
    double expectedAngelChange2 = 0;
    TurtleStep expectedStep2 = new TurtleStep(expectedInitState2, expectedPositionChange2, expectedAngelChange2);
    List<TurtleStep> expectedSteps = List.of(expectedStep1, expectedStep2);

    double Xf3 = 400 * Math.sin(Math.toRadians(75)) - 400;
    double Yf3 = 400 * Math.cos(Math.toRadians(75));
    double finalHeading = 75;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf3, Yf3), finalHeading);

    // check each TurtleStep
    for (int i = 0; i < steps.size(); i++) {
      checkTurtleStep(steps.get(i), expectedSteps.get(i));
    }
    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  @Test
  void testIntermediateStatesSpeedDefault() {
    myTurtleAnimator.setSpeed(1);

    prepareMove();

    List<TurtleState> expectedInterStates = new ArrayList<>();
    for (int i = 1; i <= 24; i++) {
      TurtleState state = new TurtleState(new Point(0,i),0);
      expectedInterStates.add(state);
    }

    checkInterStates(expectedInterStates);

  }

  @Test
  void testIntermediateStatesSpeed4() {
    myTurtleAnimator.setSpeed(4);

    prepareMove();

    List<TurtleState> expectedInterStates = new ArrayList<>();
    for (int i = 1; i <= 6; i++) {
      TurtleState state = new TurtleState(new Point(0,i*4),0);
      expectedInterStates.add(state);
    }

    checkInterStates(expectedInterStates);

  }

  @Test
  void testIntermediateStatesSpeedMin() {
    myTurtleAnimator.setSpeed(0);

    prepareMove();
    List<TurtleState> expectedInterStates = new ArrayList<>();
    checkInterStates(expectedInterStates);

  }

  @Test
  void testIntermediateStatesSpeedMax() {
    myTurtleAnimator.setSpeed(10);

    prepareMove();

    List<TurtleState> expectedInterStates = new ArrayList<>();
    TurtleState state = new TurtleState(new Point(0,24),0);
    expectedInterStates.add(state);


    checkInterStates(expectedInterStates);

  }

  @Test
  void testIntermediateAnglesSpeed6() {
    myTurtleAnimator.setSpeed(6);

    prepareRotate();

    List<TurtleState> expectedInterStates = new ArrayList<>();
    for (int i = 1; i <= 4; i++) {
      TurtleState state = new TurtleState(new Point(0,0),i*6);
      expectedInterStates.add(state);
    }

    checkInterStates(expectedInterStates);

  }

  @Test
  void testStepForward() {
    // step 1: fd 200
    myTurtle.move(175);
//
//    // step 2: rt 150
//    myTurtle.rotate(150);

    // step 3: fd -50
    myTurtle.move(-50);

    // step 4: rt 25
    myTurtle.rotate(25);

    // step 5: fd 100
    myTurtle.move(100);

    myTurtle.stepBack();
    myTurtle.stepBack();
    myTurtle.stepBack();

//    List<TurtleStep> forwardStep = myTurtle.stepForward();
//
//    TurtleState expectedInitState = new TurtleState(new Point(0, 200), 150);
//    Vector expectedPositionChange = new Vector(-50*Math.sin(30), 50*Math.cos(30));
//    double expectedAngelChange = 0;
//    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    double Xf = -25;
    double Yf = -200 + 43.3012701892;
    double finalHeading = 150;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);
//
//    // check TurtleStep
//    checkTurtleStep(expectedStep, forwardStep);
    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());


  }
//
//  @Test
//  void testStepBackward() {
//    // step 1: fd 200
//    myTurtle.doStep(200, 0);
//
//    // step 2: rt 150
//    myTurtle.doStep(0, 150);
//
//    // step 3: fd -50
//    myTurtle.doStep(-50, 0);
//
//    TurtleStep backwardStep = myTurtle.stepBack();
//
//    TurtleState expectedInitState = new TurtleState(new Point(-25, 243.3012701892), 150);
//    slogo.model.api.turtle.Vector expectedPositionChange = new slogo.model.api.turtle.Vector(50*Math.sin(30), -50*Math.cos(30));
//    double expectedAngelChange = 0;
//    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);
//
//    double Xf = 0;
//    double Yf = 200;
//    double finalHeading = 150;
//    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);
//
//    // check TurtleStep
//    checkTurtleStep(expectedStep, backwardStep);
//    // check final state
//    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
//
//  }
//
  @Test
  void testSetHeading() {
    myTurtle.rotate(150);
    myTurtle.rotate(85);
    TurtleStep step = myTurtle.setHeading(45);

    TurtleState expectedInitState = new TurtleState(new Point(0, 0), 235);
    Vector expectedPositionChange = new Vector(0,0);
    double expectedAngelChange = -190;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    // check TurtleStep
    checkTurtleStep(expectedStep, step);
  }

  @Test
  void testSetPosition() {
    myTurtle.move(130);
    myTurtle.move(-70);
    TurtleStep step = myTurtle.setXY(new Point(45,45));

    TurtleState expectedInitState = new TurtleState(new Point(0, 60), 0);
    Vector expectedPositionChange = new Vector(45,-15);
    double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    // check TurtleStep
    checkTurtleStep(expectedStep, step);
  }
//
//  @Test
//  void testSetInvalidPosition() {
//    assertThrows(InvalidPositionException.class, () -> myTurtle.setPosition(new Point(400,400)));
//  }
//
//  @Test
//  void testResetTurtle() {
//    myTurtle.doStep(50, 0);
//    myTurtle.doStep(0, 65);
//    myTurtle.doStep(-200, 0);
//    List<Turtle> turtles = new ArrayList<>();
//    turtles.add(myTurtle);
//    TurtleAnimator.resetTurtles(turtles);
//
//    double Xf = 0;
//    double Yf = 0;
//    double finalHeading = 0;
//    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);
//
//    // check final state
//    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
//  }
//
  void prepareMove() {
    Map<Integer, List<TurtleStep>> turtles = new HashMap<>();
    List<TurtleStep> steps = myTurtle.move(24);
    turtles.put(0, steps);
    myTurtleAnimator.animateStep(turtles);
  }

  void prepareRotate() {
    Map<Integer, List<TurtleStep>> turtles = new HashMap<>();
    TurtleStep step = myTurtle.rotate(24);
    turtles.put(0, List.of(step));
    myTurtleAnimator.animateStep(turtles);
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
