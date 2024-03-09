package slogo.model.api;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.model.math.Point;
import slogo.model.math.Vector;
import slogo.model.session.SessionImplementation;

class SessionTest {

  Session _session;

  @BeforeEach
  void setUp() {
    _session = new SessionImplementation();
  }

  void assertEquals(Point expected, Point actual) {
    Assertions.assertEquals(expected.getX(), actual.getX(), Math.abs(expected.getX()) / 1e3);
    Assertions.assertEquals(expected.getY(), actual.getY(), Math.abs(expected.getY()) / 1e3);
  }

  void assertEquals(Vector expected, Vector actual) {
    Assertions.assertEquals(expected.dx(), actual.dx(), Math.abs(expected.dx()) / 1e3);
    Assertions.assertEquals(expected.dy(), actual.dy(), Math.abs(expected.dy()) / 1e3);
  }

  void assertEquals(TurtleState expected, TurtleState actual) {
    assertEquals(expected.position(), actual.position());
    Assertions.assertEquals(expected.heading(), actual.heading(),
        Math.abs(expected.heading()) / 1e3);
  }

  void assertEquals(TurtleStep expected, TurtleStep actual) {
    assertEquals(expected.initialState(), actual.initialState());
    assertEquals(expected.changeInPosition(), actual.changeInPosition());
    Assertions.assertEquals(expected.changeInAngle(), actual.changeInAngle(),
        Math.abs(expected.changeInAngle()) / 1e3);
  }

  void assertStatesEqual(Map<Integer, TurtleState> expectedStates,
      Map<Integer, TurtleState> actualStates) {
    Assertions.assertEquals(expectedStates.keySet(), actualStates.keySet());
    for (int id : expectedStates.keySet()) {
      System.out.println(id);
      assertEquals(expectedStates.get(id), actualStates.get(id));
    }
  }

  void assertStepsEqual(Map<Integer, List<TurtleStep>> expectedSteps,
      Map<Integer, List<TurtleStep>> actualSteps) {
    Assertions.assertEquals(expectedSteps.keySet(), actualSteps.keySet());
    for (int id : expectedSteps.keySet()) {
      List<TurtleStep> expected = expectedSteps.get(id);
      List<TurtleStep> actual = actualSteps.get(id);
      Assertions.assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
        assertEquals(expected.get(i), actual.get(i));
      }
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"fd 50 rt 90", "fd 50 fd 0 rt 90", "fd 5 fd 2 fd 43 rt 2 rt 88"})
  void run_SimpleCommands(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {"make :cheese 50 fd :cheese rt :cheese + 40", "rt [ make :x 50 fd :x ] + 40",
          "make :x 90 if lessequal? 5 5 [ make :x 50 fd :x ] rt :x"})
  void run_Make(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"make :x 90 to myfd [ :x :y ] [ fd :x - :y ] myfd :x 40 rt :x"})
  void run_To(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"for [ :i 1 11 1 ] [ fd 5 ] rt 90", "dotimes [ :i 10 ] [ fd 5 ] rt 90",
      "repeat 10 [ fd :repcount ] fd -5 rt 90"})
  void run_Loops(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"if notequal? 1 0 [ fd 50 rt 90 ]",
      "ifelse greater? 0 0 [ fd 50000 ] [ fd 50 rt 90 ]",
      "fd 50 if notequal? id -1 [ fd home ] ifelse equal? xcor 5 [ lt 50 ] [ rt 90 ]"})
  void run_Conditionals(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"if or notequal? 1 0 lessequal? 5 6 [ fd 50 rt 90 ]",
      "ifelse not not greater? 0 0 [ fd 50000 ] [ fd 50 rt 90 ]",
      "fd 50 if and notequal? id -1 pow 2 2 == 4 [ fd home ] ifelse and equal? xcor 5 not log 2 !="
          + " 1 [ lt 50 ] [ rt 90 ]"})
  void run_BooleanComparisons(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"fd 1 + random 100 / remainder 51 5 fd 50", "fd randr 50 100"})
  void run_Random(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
  }

  // !!! Atan does not pass due to floating point error, but it is correct
  @ParameterizedTest
  @ValueSource(
      strings = {"fd sine 90", "fd tan 45", "fd atan 1", "fd [ sqrt 4 ] - 1"})
  void run_Trig(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 1), 0));
    assertStatesEqual(expected, actual);
  }

  @Test
  void run_PI() {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run("fd pi");
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, Math.PI), 0));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"fd sum 2 48 rt 91 - difference 2 1",
      "fd ~ product cos 180 50 rt remainder 181 91"})
  void run_JavaCommands(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"fd 50 rt 90", "fd 50 fd 0 rt 90", "fd 5 fd 2 fd 43 rt 2 rt 88"})
  void run_MismatchedArity(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {"fd 100 bk 50 rt 90",
      "lt 270 rt 270 fd 500 bk 450 fd -50 lt 90 rt 90 fd 50 rt 90"})
  void run_MultipleCommands(String command) {
//  GIVEN one turtle at (0,0) heading 0deg
//  WHEN run(command)
    _session.run(command);
    Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();

//  THEN one turtle at (0,50) heading 90deg
    Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 90));
    assertStatesEqual(expected, actual);
  }

  @Nested
  @DisplayName("getVariables Tests")
  class Test_getVariables {

    @Test
    void nonEmpty() {
//  GIVEN only one variable :distance is defined with value 10
      _session.run("make :distance 10");

//  WHEN getVariables()
      Map<String, Double> actual = _session.getVariables();

//  THEN {"distance": 10}
      Map<String, Double> expected = Map.of("distance", 10.0);
      Assertions.assertEquals(expected, actual);
    }

    @Test
    void empty() {
//  GIVEN no variables defined

//  WHEN getVariables()
      Map<String, Double> actual = _session.getVariables();

//  THEN {}
      Map<String, Double> expected = Map.of();
      Assertions.assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("getUserDefinedCommands Tests")
  class Test_getUserDefinedCommands {

    @Test
    void empty() {
//  GIVEN no user commands defined

//  WHEN getUserDefinedCommands()
      Map<String, Map<String, String>> actual = _session.getUserDefinedCommands();

//  THEN {}
      Map<String, Map<String, String>> expected = Map.of();
      Assertions.assertEquals(expected, actual);
    }

    @Test
    void single() {
//  GIVEN only one user command is defined: MYfd with two parameters :x :y and body fd :x * 2 bk :y
      _session.run("to MYfd [ :x :y ] [ fd :x * 2 bk :y ]");

//  WHEN getUserDefinedCommands()
      Map<String, Map<String, String>> actual = _session.getUserDefinedCommands();

//  THEN {"MYfd": {"arity": "2", "parameter1": "x", "parameter2": "y", "body": "fd :x * 2 bk
//  :y"}}  }
      Map<String, Map<String, String>> expected = Map.of("MYfd",
          Map.of("arity", "2", "parameter1", "x", "parameter2", "y", "body", "fd :x * 2 bk :y"));
      Assertions.assertEquals(expected, actual);
    }
  }

  @Test
  void getLibraryCommands_Empty() {
//  GIVEN all library commands implemented

//  WHEN getLibraryCommands()
    Map<String, Map<String, String>> actual = _session.getLibraryCommands();

//  THEN {"commandName": {"alias":"aliasName", "arity": "#", "parameter1": "par1", "parameter2":
//  "par2"...}}
    Assertions.assertEquals(0, actual.size());
  }

  @Nested
  @DisplayName("getCommandHistory Tests")
  class Test_getCommandHistory {

    @Test
    void empty() {
//    GIVEN no commands have been executed

//    WHEN `getCommandHistory(1)`
      List<Map<String, Map<String, String>>> actual = _session.getCommandHistory(1);
//
//    THEN `{"fd 50": {"successful": "true"}}`
      List<Map<String, Map<String, String>>> expected = List.of();
      Assertions.assertEquals(expected, actual);
    }

    @Test
    void single() {
//    GIVEN only two commands have been executed: "bke 50", "fd 50"
      try {
        _session.run("bke 50");
      } catch (RunCodeError e) {

      }

      _session.run("fd 50");

//    WHEN `getCommandHistory(1)`
      List<Map<String, Map<String, String>>> actual = _session.getCommandHistory(1);
//
//    THEN `{"fd 50": {"successful": "true"}}`
      List<Map<String, Map<String, String>>> expected =
          List.of(Map.of("fd 50", Map.of("successful", "true")));
      Assertions.assertEquals(expected, actual);
    }

    @Test
    void all() {
//    GIVEN only two commands have been executed: "bke 50", "fd 50"
      try {
        _session.run("bke 50");
      } catch (RunCodeError e) {

      }
      _session.run("fd 50");

//    WHEN `getCommandHistory(0)`
      List<Map<String, Map<String, String>>> actual = _session.getCommandHistory(Integer.MAX_VALUE);
//
//    THEN `{"bke 50": {"successful": "false"}}, "fd 50": {"successful": "true"}`
      List<Map<String, Map<String, String>>> expected =
          List.of(Map.of("bke 50", Map.of("successful", "false")),
              Map.of("fd 50", Map.of("successful", "true")));
      Assertions.assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("getTurtlesCurrentStates Tests")
  class Test_getTurtlesCurrentStates {

    @Test
    void initial() {
//      GIVEN no commands have been executed

//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,0), 0)}`
      Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 0), 0));
      assertStatesEqual(expected, actual);
    }

    @Test
    void afterMovements() {
//      GIVEN "fd 50", "rt 51" has been executed
      _session.run("fd 50 rt 51");
//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,50), 51)}`
      Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 51));
      assertStatesEqual(expected, actual);
    }
  }

  @Nested
  @DisplayName("getTurtlesStepHistories Tests")
  class Test_getTurtlesStepHistories {

    @ParameterizedTest
    @ValueSource(ints = {0, 9})
    void empty(int length) {
//    GIVEN no commands have been executed

//    WHEN `getTurtlesStepHistory(0 OR 9)`
      Map<Integer, List<TurtleStep>> actual = _session.getTurtlesStepHistories(length);

//    THEN {}
      Map<Integer, List<TurtleStep>> expected = Map.of(1, List.of());
      assertStepsEqual(expected, actual);
    }

    @Test
    void single() {
//    GIVEN only two commands have been executed: "fd 50" and "bk 50"
      _session.run("fd 50");
      _session.run("bk 50");

//    WHEN `getTurtlesStepHistory(1)`
      Map<Integer, List<TurtleStep>> actual = _session.getTurtlesStepHistories(1);

//    THEN `{0:{new TurtleStep(new TurtleState(new Position(0, 0), 0), new Vector(0, 50), 0)}}`
      Map<Integer, List<TurtleStep>> expected = Map.of(1,
          List.of(new TurtleStep(new TurtleState(new Point(0, 50), 0), new Vector(0, -50), 0)));
      assertStepsEqual(expected, actual);
    }

    @Test
    void all() {
//    GIVEN only two commands have been executed: "fd 50" and "bk 50"
      _session.run("fd 50");
      _session.run("bk 50");

//    WHEN `getTurtlesStepHistory(0)`
      Map<Integer, List<TurtleStep>> actual = _session.getTurtlesStepHistories(Integer.MAX_VALUE);

//      THEN `{0:{new TurtleStep(new TurtleState(new Position(0, 0), 0), new Vector(0, 50), 0),
//          new TurtleStep(new TurtleState(new Position(0, 50), 0), new Vector(0, -50), 0)}}`
      Map<Integer, List<TurtleStep>> expected = Map.of(1,
          List.of(new TurtleStep(new TurtleState(new Point(0, 0), 0), new Vector(0, 50), 0),
              new TurtleStep(new TurtleState(new Point(0, 50), 0), new Vector(0, -50), 0)));
      assertStepsEqual(expected, actual);
    }
  }

//  @Nested
//  @DisplayName("redo Tests")
//  class Test_redo {
//
//    @Test
//    void empty() {
////      GIVEN no commands have been executed
////      WHEN `redo(1)`
//      _session.redo(1);
////      THEN `getCommandHistory(Integer.MAX_VALUE) == {}`
//      List<Map<String, Map<String, String>>> actualCommandHistory =
//          _session.getCommandHistory(Integer.MAX_VALUE);
//      List<Map<String, Map<String, String>>> expectedCommandHistory = List.of();
//      Assertions.assertEquals(expectedCommandHistory, actualCommandHistory);
////      AND `getTurtlesStepHistory(Integer.MAXVALUE) == {}`
//      Map<Integer, List<TurtleStep>> actualStepHistories =
//          _session.getTurtlesStepHistories(Integer.MAX_VALUE);
//      Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of();
//      assertStepsEqual(expectedStepHistories, actualStepHistories);
//    }
//
//    @Test
//    void single() {
////      GIVEN two commands "fd 50" have been executed
//      _session.run("fd 50");
////      AND `undo(1)` has been executed
//      _session.undo(1);
////      WHEN `redo(1)`
//      _session.redo(1);
////      THEN `getCommandHistory(Integer.MAX_VALUE) == {"fd 50": {"successful":"true"}}`
//      List<Map<String, Map<String, String>>> actualCommandHistory =
//          _session.getCommandHistory(Integer.MAX_VALUE);
//      List<Map<String, Map<String, String>>> expectedCommandHistory =
//          List.of(Map.of("fd 50", Map.of("successful", "true")));
//      Assertions.assertEquals(expectedCommandHistory, actualCommandHistory);
////      AND `getTurtlesStepHistory(Integer.MAX_VALUE) == {0:{new TurtleStep(new Position(0, 0),
////      0, new Vector(0,
////      50), 0)}}`
//      Map<Integer, List<TurtleStep>> actualStepHistories = _session.getTurtlesStepHistories(1);
//      Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of(1,
//          List.of(new TurtleStep(new TurtleState(new Point(0, 50), 0), new Vector(0, -50), 0)));
//      assertStepsEqual(expectedStepHistories, actualStepHistories);
//    }
//
//    @Test
//    void max() {
////      GIVEN two commands "fd 50", "bk 50" have been executed
////      AND `undo(Integer.MAX_VALUE)` has been executed
//      _session.run("fd 50");
//      _session.run("bk 50");
////      WHEN `redo(Integer.MAX_VALUE)`
//      _session.redo(Integer.MAX_VALUE);
////      THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}, "bk 50":
////      {"successful":"true"}`
//      List<Map<String, Map<String, String>>> actual = _session.getCommandHistory(0);
////
////    THEN `{"bk 50": {"successful": "true"}}, "fd 50": {"successful": "true"}`
//      List<Map<String, Map<String, String>>> expected =
//          List.of(Map.of("bk 50", Map.of("successful", "true")),
//              Map.of("fd 50", Map.of("successful", "true")));
//      Assertions.assertEquals(expected, actual);
////      AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0,
////      50), 0)},
////      new TurtleStep(new Position(0, 50), 0, new Vector(0, -50), 0)}}`
//      Map<Integer, List<TurtleStep>> actualStepHistories = _session.getTurtlesStepHistories(0);
//      Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of(1,
//          List.of(new TurtleStep(new TurtleState(new Point(0, 0), 0), new Vector(0, 50), 0),
//              new TurtleStep(new TurtleState(new Point(0, 50), 0), new Vector(0, -50), 0)));
//      assertStepsEqual(expectedStepHistories, actualStepHistories);
//    }
//
//    @Test
//    void over() {
////      GIVEN two commands "fd 50", "bk 50" have been executed
////      AND `undo(Integer.MAX_VALUE)` has been executed
//      _session.run("fd 50");
//      _session.run("bk 50");
////      WHEN `redo(5)`
//      _session.redo(5);
////      THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}, "bk 50":
////      {"successful":"true"}`
//      List<Map<String, Map<String, String>>> actual = _session.getCommandHistory(0);
////
////    THEN `{"bk 50": {"successful": "true"}}, "fd 50": {"successful": "true"}`
//      List<Map<String, Map<String, String>>> expected =
//          List.of(Map.of("bk 50", Map.of("successful", "true")),
//              Map.of("fd 50", Map.of("successful", "true")));
//      Assertions.assertEquals(expected, actual);
////      AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0,
////      50), 0)},
////      new TurtleStep(new Position(0, 50), 0, new Vector(0, -50), 0)}}`
//      Map<Integer, List<TurtleStep>> actualStepHistories = _session.getTurtlesStepHistories(0);
//      Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of(1,
//          List.of(new TurtleStep(new TurtleState(new Point(0, 0), 0), new Vector(0, 50), 0),
//              new TurtleStep(new TurtleState(new Point(0, 50), 0), new Vector(0, -50), 0)));
//      assertStepsEqual(expectedStepHistories, actualStepHistories);
//    }
//  }

  @Test
  void reset_simple() {
//    GIVEN one turtle at (0,50) heading 274, with command history "goto 0 50" and "seth 274"
    _session.run("goto 0 50 seth 274");
//    WHEN `reset()`
    _session.reset();
//    THEN one turtle at (0,0) heading 0
    Map<Integer, TurtleState> actualTurtles = _session.getTurtlesCurrentStates();
    Map<Integer, TurtleState> expectedTurtles = Map.of(1, new TurtleState(new Point(0, 0), 0));
    assertStatesEqual(expectedTurtles, actualTurtles);
//    AND `getCommandHistory(0) == {}`
    List<Map<String, Map<String, String>>> actualCommandHistories = _session.getCommandHistory(1);
    List<Map<String, Map<String, String>>> expectedCommandHistories = List.of();
    Assertions.assertEquals(expectedCommandHistories, actualCommandHistories);
//    AND `getTurtlesStepHistory(0) == {}`
    Map<Integer, List<TurtleStep>> actualStepHistories = _session.getTurtlesStepHistories(0);
    Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of(1, List.of());
    assertStepsEqual(expectedStepHistories, actualStepHistories);
  }

  @Nested
  class run_CountSteps {

    @Test
    void single() {
      int actual = _session.run("fd 50");
      Assertions.assertEquals(1, actual);
    }

    @Test
    void single_two_turtles() {
      int actual = _session.run("tell [ 1 2] fd 50");
      Assertions.assertEquals(1, actual);
    }

    @Test
    void multiple_two_turtles() {
      int actual = _session.run("tell [ 1 2] fd 50 bk 50 rt 50 lt heading fd home ");
      Assertions.assertEquals(6, actual);
    }

    @Test
    void loop() {
      int actual = _session.run("repeat 1800 [fd 10 rt :repcount + 0.1]");
      Assertions.assertEquals(3600, actual);
    }
  }

//    @Nested
//    @DisplayName("undo Tests")
//    class Test_undo {
//
//      @Test
//      void empty() {
////      GIVEN only one command "fd 50" has been executed
//        _session.run("fd 50");
////      WHEN `undo(1)`
//        _session.undo(1);
////      THEN `getCommandHistory(Integer.MAXVALUE) == {}`
//        List<Map<String, Map<String, String>>> actualCommandHistory =
//            _session.getCommandHistory(Integer.MAX_VALUE);
//        List<Map<String, Map<String, String>>> expectedCommandHistory = List.of();
//        Assertions.assertEquals(expectedCommandHistory, actualCommandHistory);
//
////      AND `getTurtlesStepHistory(Integer.MAXVALUE) == {}`
//        Map<Integer, List<TurtleStep>> actualStepHistories =
//            _session.getTurtlesStepHistories(Integer.MAX_VALUE);
//        Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of();
//        assertStepsEqual(expectedStepHistories, actualStepHistories);
//      }
//
//      @Test
//      void single() {
////      GIVEN two commands "fd 50", "bk 50" have been executed
//        _session.run("fd 50");
//        _session.run("bk 50");
////      WHEN `undo(1)`
//        _session.undo(1);
////      THEN `getCommandHistory(Integer.MAXVALUE) == {"fd 50": {"successful":"true"}}`
//        List<Map<String, Map<String, String>>> actualCommandHistories =
//            _session.getCommandHistory(Integer.MAX_VALUE);
//        List<Map<String, Map<String, String>>> expectedCommandHistories =
//            List.of(Map.of("fd 50", Map.of("successful", "true")));
//        Assertions.assertEquals(expectedCommandHistories, actualCommandHistories);
////      AND `getTurtlesStepHistory(Integer.MAXVALUE) == {0:{new TurtleStep(new Position(0, 0), 0,
////      new Vector(0,
////      50), 0)}}`
//        Map<Integer, List<TurtleStep>> actualStepHistories =
//            _session.getTurtlesStepHistories(Integer.MAX_VALUE);
//        Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of(1,
//            List.of(new TurtleStep(new TurtleState(new Point(0, 50), 0), new Vector(0, -50), 0)));
//        assertStepsEqual(expectedStepHistories, actualStepHistories);
//      }
//
//      @Test
//      void max() {
////      GIVEN only two commands have been executed: "fd 50" and "bk 50"
//        _session.run("fd 50");
//        _session.run("bk 50");
////      WHEN `undo(Integer.MAX_VALUE)`
//        _session.undo(Integer.MAX_VALUE);
////      THEN `getCommandHistory(Integer.MAX_VALUE) == {}`
//        List<Map<String, Map<String, String>>> actualCommandHistory =
//            _session.getCommandHistory(Integer.MAX_VALUE);
//        List<Map<String, Map<String, String>>> expectedCommandHistory = List.of();
//        Assertions.assertEquals(expectedCommandHistory, actualCommandHistory);
////      AND `getTurtlesStepHistory(Integer.MAXVALUE) == {}`
//        Map<Integer, List<TurtleStep>> actualStepHistories =
//            _session.getTurtlesStepHistories(Integer.MAX_VALUE);
//        Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of();
//        assertStepsEqual(expectedStepHistories, actualStepHistories);
//      }
//
//      @Test
//      void over() {
////      GIVEN only one command "fd 50" has been executed
//        _session.run("fd 50");
////      WHEN `undo(Integer.MAX_VALUE)`
//        _session.undo(3);
////      THEN `getCommandHistory(Integer.MAX_VALUE) == {}`
//        List<Map<String, Map<String, String>>> actualCommandHistory =
//            _session.getCommandHistory(Integer.MAX_VALUE);
//        List<Map<String, Map<String, String>>> expectedCommandHistory = List.of();
//        Assertions.assertEquals(expectedCommandHistory, actualCommandHistory);
////      AND `getTurtlesStepHistory(Integer.MAXVALUE) == {}`
//        Map<Integer, List<TurtleStep>> actualStepHistories =
//            _session.getTurtlesStepHistories(Integer.MAX_VALUE);
//        Map<Integer, List<TurtleStep>> expectedStepHistories = Map.of();
//        assertStepsEqual(expectedStepHistories, actualStepHistories);
//      }
//    }
//  }

  @Nested
  class run_MultipleTurtles {

    @BeforeEach
    void setup() {
      _session.run("tell [ 1 5 ]");
    }

    @Test
    void initial() {
//      GIVEN no commands have been executed

//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,0), 0)}`
      Map<Integer, TurtleState> expected =
          Map.of(1, new TurtleState(new Point(0, 0), 0), 5, new TurtleState(new Point(0, 0), 0));
      assertStatesEqual(expected, actual);
    }

    @Test
    void move_together() {
//      GIVEN "fd 50", "rt 51" has been executed
      _session.run("fd 50 rt 51");
//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,50), 51)}`
      Map<Integer, TurtleState> expected = Map.of(1, new TurtleState(new Point(0, 50), 51), 5,
          new TurtleState(new Point(0, 50), 51));
      assertStatesEqual(expected, actual);
    }

    @Test
    void move_separately() {
      _session.run("fd 50 rt 51 tell [ 1 ] lt 51");
//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,50), 51)}`
      Map<Integer, TurtleState> expected =
          Map.of(1, new TurtleState(new Point(0, 50), 0), 5, new TurtleState(new Point(0, 50), 51));
      assertStatesEqual(expected, actual);
    }

    @Test
    void ask() {
      _session.run("fd 50 ask [ 1 3 ] [ fd 50 ] bk 101");
//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,50), 51)}`
      Map<Integer, TurtleState> expected =
          Map.of(1, new TurtleState(new Point(0, -1), 0), 5, new TurtleState(new Point(0, -51), 0),
              3, new TurtleState(new Point(0, 50), 0));
      assertStatesEqual(expected, actual);
    }

    @Test
    void ask_with() {
      _session.run("fd 50 ask [ 1 3 ] [ fd 50 ] bk 101 askwith [ ycor == 50 ] [ rt 90 ] fd 51");
//      WHEN `getTurtlesCurrentStates()`
      Map<Integer, TurtleState> actual = _session.getTurtlesCurrentStates();
//      THEN `{0: new TurtleState(new Position(0,50), 51)}`
      Map<Integer, TurtleState> expected =
          Map.of(1, new TurtleState(new Point(0, 50), 0), 5, new TurtleState(new Point(0, 0), 0), 3,
              new TurtleState(new Point(0, 50), 90));
      assertStatesEqual(expected, actual);
    }
  }

  @Nested
  class RunErrors {

    void assertEquals(RunCodeError expected, RunCodeError actual) {
      Assertions.assertEquals(expected.getErrorType(), actual.getErrorType());
      Assertions.assertEquals(expected.getErrorMessageKey(), actual.getErrorMessageKey());
      Assertions.assertEquals(expected.getLineNumber(), actual.getLineNumber());
      Assertions.assertEquals(expected.getLine(), actual.getLine());
    }

    @Test
    void token() {
      RunCodeError actual = Assertions.assertThrows(RunCodeError.class, () -> {
        _session.run("::");
      });
      RunCodeError expected = new RunCodeError(ErrorType.TOKENIZE, "invalidToken", 0, "::");
      assertEquals(expected, actual);
    }

    @Test
    void parse() {
      RunCodeError actual = Assertions.assertThrows(RunCodeError.class, () -> {
        _session.run("[ 5 6");
      });
      RunCodeError expected = new RunCodeError(ErrorType.PARSE, "expectedDifferentToken", 1, "[ 5 6");
      assertEquals(expected, actual);
    }

    @Test
    void interpret() {
      RunCodeError actual = Assertions.assertThrows(RunCodeError.class, () -> {
        _session.run("fd 50 / 0");
      });
      RunCodeError expected = new RunCodeError(ErrorType.INTERPRET, "divideByZero", 1, "fd 50 / 0");
      assertEquals(expected, actual);
    }

    @Test
    void runtime() {
      RunCodeError actual = Assertions.assertThrows(RunCodeError.class, () -> {
        _session.run("+");
      });
      RunCodeError expected = new RunCodeError(ErrorType.RUNTIME, "nullExpression", -1, null);
      assertEquals(expected, actual);
    }
  }
}