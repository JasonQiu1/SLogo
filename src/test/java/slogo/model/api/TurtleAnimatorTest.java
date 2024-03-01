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
    myTurtle = new Turtle();
    myTurtleAnimator = new TurtleAnimator();
  }

  @Test
  void testIntermediateStatesSpeedDefault() {
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
  void testNextFrame() {
    prepareMove();
    Map<Integer, TurtleState> nextFrame = myTurtleAnimator.nextFrame();
    TurtleState expectedNextFrame = new TurtleState(new Point(0,1),0);
    Map<Integer, TurtleState> expectedFrames = new HashMap<>();
    expectedFrames.put(0,expectedNextFrame);

    checkFrame(nextFrame, expectedFrames);

  }



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

  void checkFrame(Map<Integer, TurtleState> states, Map<Integer, TurtleState> expected) {
    for (Integer turtleId: states.keySet()) {
      assertEquals(states.get(turtleId).position(), expected.get(turtleId).position());
      assertEquals(states.get(turtleId).heading(), expected.get(turtleId).heading());
    }

  }

}
