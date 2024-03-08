package slogo.model.turtleutil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

import slogo.model.api.exception.turtle.InvalidPositionException;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.api.turtle.Vector;

public class TurtleTest {
  private Turtle myTurtle;
  @BeforeEach
  void setup () {
    myTurtle = new Turtle(1);
  }

  @Test
  void testNormalTurtleStepForward() {
    // fd 50
    List<TurtleStep> steps = myTurtle.move(50);
    final TurtleState expectedInitState = new TurtleState(new Point(0,0), 0);
    final Vector expectedPositionChange = new Vector(0, 50);
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
  void testTurtleStepHistoryGivenMaxLength() {
    myTurtle.move(50);
    myTurtle.rotate(30);
    myTurtle.move(100);

    TurtleState expectedInitState3 = new TurtleState(new Point(0, 0), 0);
    Vector expectedPositionChange3 = new Vector(0, 50);
    double expectedAngelChange3 = 0;
    TurtleStep expectedStep3 = new TurtleStep(expectedInitState3, expectedPositionChange3, expectedAngelChange3);

    TurtleState expectedInitState4 = new TurtleState(new Point(0, 50), 0);
    Vector expectedPositionChange4 = new Vector(0, 0);
    double expectedAngelChange4 = 30;
    TurtleStep expectedStep4 = new TurtleStep(expectedInitState4, expectedPositionChange4, expectedAngelChange4);

    TurtleState expectedInitState5 = new TurtleState(new Point(0, 50), 30);
    Vector expectedPositionChange5 = new Vector(100*Math.sin(Math.toRadians(30)), 100*Math.cos(Math.toRadians(30)));
    double expectedAngelChange5 = 0;
    TurtleStep expectedStep5 = new TurtleStep(expectedInitState5, expectedPositionChange5, expectedAngelChange5);

    // check TurtleStep history
    List<TurtleStep> expectedStepHistory = List.of(expectedStep3, expectedStep4, expectedStep5);
    List<TurtleStep> stepHistory = myTurtle.getStepHistory(3);
    checkTurtleStepHistory(expectedStepHistory, stepHistory);
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
    List<TurtleStep> stepHistory = myTurtle.getStepHistory(Integer.MAX_VALUE);
    checkTurtleStepHistory(expectedStepHistory, stepHistory);

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
    // step 2: fd 200
    TurtleState expectedInitState1 = new TurtleState(new Point(0,0), 75);
    List<TurtleStep> steps = myTurtle.move(200);
    Vector expectedPositionChange1 = new Vector(150, 150/Math.sin(Math.toRadians(75)) * Math.cos(Math.toRadians(75)));
    double expectedAngelChange1 = 0;
    TurtleStep expectedStep1 = new TurtleStep(expectedInitState1, expectedPositionChange1, expectedAngelChange1);

    TurtleState expectedInitState2 = new TurtleState(new Point(-150,150/Math.sin(Math.toRadians(75)) * Math.cos(Math.toRadians(75))), 75);
    Vector expectedPositionChange2 = new Vector(200 * Math.sin(Math.toRadians(75)) - 150, 200 * Math.cos(Math.toRadians(75)) - expectedPositionChange1.getDy());
    double expectedAngelChange2 = 0;
    TurtleStep expectedStep2 = new TurtleStep(expectedInitState2, expectedPositionChange2, expectedAngelChange2);
    List<TurtleStep> expectedSteps = List.of(expectedStep1, expectedStep2);

    double Xf3 = -150 + 200 * Math.sin(Math.toRadians(75)) - 150;
    double Yf3 = 200 * Math.cos(Math.toRadians(75));
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
  void testTurnTowards() {
    // rt 45
    myTurtle.rotate(45);

    Point point = new Point(-100, 75);

    TurtleStep step = myTurtle.turnTowards(point);

    TurtleState expectedInitState = new TurtleState(new Point(0,0), 45);
    Vector expectedPositionChange = new Vector(0, 0);
    double expectedAngelChange = - 53.13010235415598 - 45;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);
    double Xf = 0;
    double Yf = 0;
    double finalHeading = -53.13010235415598;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check TurtleStep
    checkTurtleStep(expectedStep, step);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());

  }

  @Test
  void testStepForward() {
    // step 1: fd 175
    myTurtle.move(175);

    // step 2: fd 200
    myTurtle.move(200);

    // step 3: rt 25
    myTurtle.rotate(25);

    // step 4: fd 100
    myTurtle.move(100);

    // step 5: bk 50
    myTurtle.move(-50);

    // take 5 steps back
    myTurtle.stepBack();
    myTurtle.stepBack();
    myTurtle.stepBack();
    myTurtle.stepBack();
    myTurtle.stepBack();

    // take 2 steps forward
    myTurtle.stepForward();
    List<TurtleStep> forwardSteps = myTurtle.stepForward();

    TurtleState expectedInitState = new TurtleState(new Point(0, -125), 0);
    Vector expectedPositionChange = new Vector(0, 200);
    double expectedAngelChange = 0;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    double Xf = 0;
    double Yf = 75;
    double finalHeading = 0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    for (TurtleStep step: forwardSteps) {
      // check TurtleStep
      checkTurtleStep(expectedStep, step);
    }

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());


  }

  @Test
  void testStepBackward() {
    // step 1: fd 200
    myTurtle.move(200);

    // step 2: rt 75
    myTurtle.rotate(75);

    List<TurtleStep> backwardSteps = myTurtle.stepBack();

    TurtleState expectedInitState = new TurtleState(new Point(0, -100), 75);
    Vector expectedPositionChange = new Vector(0, 0);
    double expectedAngelChange = -75;
    TurtleStep expectedStep = new TurtleStep(expectedInitState, expectedPositionChange, expectedAngelChange);

    double Xf = 0;
    double Yf = -100;
    double finalHeading = 0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    for (TurtleStep step: backwardSteps) {
      // check TurtleStep
      checkTurtleStep(expectedStep, step);
    }
    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());

  }

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

  @Test
  void testSetInvalidPosition() {
    assertThrows(InvalidPositionException.class, () -> myTurtle.setXY(new Point(400,400)));
  }

  @Test
  void testResetTurtle() {
    myTurtle.move(50);
    myTurtle.rotate(65);
    myTurtle.move(-200);
    myTurtle.reset(TurtleAnimator.getInitialTurtleState());

    double Xf = 0;
    double Yf = 0;
    double finalHeading = 0;
    TurtleState expectedFinalState = new TurtleState(new Point(Xf, Yf), finalHeading);

    // check final state
    checkTurtleState(expectedFinalState, myTurtle.getCurrentState());
  }

  private void checkTurtleStep(TurtleStep expected, TurtleStep step) {
    assertEquals(expected.initialState().position(), step.initialState().position());
    assertEquals(expected.initialState().heading(), step.initialState().heading());
    assertEquals(expected.changeInPosition(), step.changeInPosition());
    assertEquals(expected.changeInAngle(), step.changeInAngle());
  }

  private void checkTurtleState(TurtleState expected, TurtleState state) {
    assertEquals(expected.position(), state.position());
    assertEquals(expected.heading(), state.heading());
  }

  private void checkTurtleStepHistory(List<TurtleStep> expected, List<TurtleStep> stepHistory) {
    for (int i = 0; i < stepHistory.size(); i++) {
      checkTurtleStep(expected.get(i), stepHistory.get(i));
    }
  }


}
