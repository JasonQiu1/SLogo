package slogo.model.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.api.turtle.Point;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.turtleutil.Turtle;

public class TurtleAnimatorTest {
  private Turtle myTurtle;
  private TurtleAnimator myTurtleAnimator;

  @BeforeEach
  void setup () {
    myTurtle = new Turtle(1);
    myTurtleAnimator = new TurtleAnimator();
  }

  @Test
  void testIntermediateStatesSpeedDefault() {
    prepareMove();

    List<TurtleState> expectedInterStates = new ArrayList<>();
    for (double i = 1.0; i <= 24.0; i++) {
      TurtleState state = new TurtleState(new Point(0.0,round(50.0 / 24.0 * i, 10)),0.0);
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
      TurtleState state = new TurtleState(new Point(0,round(50.0 / 24.0 * i * 4, 10)),0);
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
    TurtleState state = new TurtleState(new Point(0,50),0);
    expectedInterStates.add(state);


    checkInterStates(expectedInterStates);

  }

  @Test
  void testIntermediateAnglesSpeed6() {
    myTurtleAnimator.setSpeed(6);

    prepareRotate();

    List<TurtleState> expectedInterStates = new ArrayList<>();
    for (int i = 1; i <= 4; i++) {
      TurtleState state = new TurtleState(new Point(0,0), 50.0 / 24.0 * i * 6);
      expectedInterStates.add(state);
    }

    checkInterStates(expectedInterStates);

  }

  @Test
  void testNextFrame() {
    prepareMove();
    Map<Integer, TurtleState> nextFrame = myTurtleAnimator.nextFrame();
    TurtleState expectedNextFrame = new TurtleState(new Point(0.0,round(50.0 / 24.0, 10)),0);
    Map<Integer, TurtleState> expectedFrames = new HashMap<>();
    expectedFrames.put(0,expectedNextFrame);

    checkFrame(nextFrame, expectedFrames);

  }

  @Test
  void testPreviousFrame() {
    prepareMove();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    Map<Integer, TurtleState> nextFrame = myTurtleAnimator.previousFrame();
    TurtleState expectedNextFrame = new TurtleState(new Point(0.0,round(50.0 / 24.0 * 3, 10)),0);
    Map<Integer, TurtleState> expectedFrames = new HashMap<>();
    expectedFrames.put(0,expectedNextFrame);

    checkFrame(nextFrame, expectedFrames);

  }

  @Test
  void resetFrameToBeginning() {
    prepareMove();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    Map<Integer, TurtleState> nextFrame = myTurtleAnimator.resetFrame();
    TurtleState expectedNextFrame = new TurtleState(new Point(0.0,round(50.0 / 24.0, 10)),0);
    Map<Integer, TurtleState> expectedFrames = new HashMap<>();
    expectedFrames.put(0,expectedNextFrame);

    checkFrame(nextFrame, expectedFrames);

  }

  @Test
  void resetFrameGivenFramesBack() {
    prepareMove();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    myTurtleAnimator.nextFrame();
    Map<Integer, TurtleState> nextFrame = myTurtleAnimator.resetFrame(3);
    TurtleState expectedNextFrame = new TurtleState(new Point(0.0,round(50.0 / 24.0 * 2, 10)),0);
    Map<Integer, TurtleState> expectedFrames = new HashMap<>();
    expectedFrames.put(0,expectedNextFrame);

    checkFrame(nextFrame, expectedFrames);

  }


  private void prepareMove() {
    Map<Integer, List<TurtleStep>> turtles = new HashMap<>();
    List<TurtleStep> steps = myTurtle.move(50);
    turtles.put(0, steps);
    myTurtleAnimator.animateStep(turtles);
  }

  private void prepareRotate() {
    Map<Integer, List<TurtleStep>> turtles = new HashMap<>();
    TurtleStep step = myTurtle.rotate(50);
    turtles.put(0, List.of(step));
    myTurtleAnimator.animateStep(turtles);
  }

  private void checkInterStates(List<TurtleState> expectedInterStates) {
    for (Integer id: myTurtleAnimator.getIntermediateStates().keySet()) {
      List<TurtleState> states = myTurtleAnimator.getIntermediateStates().get(id);
      Iterator<TurtleState> iter = states.iterator();
      Iterator<TurtleState> expectedIter = expectedInterStates.iterator();
      while (iter.hasNext() && expectedIter.hasNext()) {
        TurtleState state = iter.next();
        state.position().setX(round(state.position().getX(), 10));
        state.position().setY(round(state.position().getY(), 10));
        TurtleState expected = expectedIter.next();
        assertEquals(expected.position(), state.position());
        assertEquals(expected.heading(), state.heading());
      }
    }

  }

  private void checkFrame(Map<Integer, TurtleState> states, Map<Integer, TurtleState> expected) {
    for (Integer turtleId: states.keySet()) {
      TurtleState state = states.get(turtleId);
      state.position().setX(round(state.position().getX(), 10));
      state.position().setY(round(state.position().getY(), 10));
      assertEquals(expected.get(turtleId).position(), states.get(turtleId).position());
      assertEquals(expected.get(turtleId).heading(), states.get(turtleId).heading());
    }

  }

  private static double round(double value, int precision) {
    int scale = (int) Math.pow(10, precision);
    return (double) Math.round(value * scale) / scale;
  }

}
