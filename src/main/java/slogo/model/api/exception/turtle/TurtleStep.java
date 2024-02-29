<<<<<<<< Updated upstream:src/main/java/slogo/model/api/turtle/TurtleStep.java
package slogo.model.api.turtle;
========
package slogo.exception.model.turtle;
>>>>>>>> Stashed changes:src/main/java/slogo/model/api/exception/turtle/TurtleStep.java

public record TurtleStep(TurtleState initialState, Vector changeInPosition, double changeInAngle) { }
