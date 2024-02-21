//1. SLOGO 76, 77: The user clicks on replay animation and sees the animation resets and reruns its previous series of commands.
public void replay() {
  // get current animation's turtles
  List<Turtle> turtles = session.getTurtles();
  Map<Integer, List<TurtleStep>> map = new HashMap<>():
  for (Turtle turtle: turtles) {
    // get turtle's step history
    List<TurtleStep> stepHistory = turtle.getStepHistory();
    map.put(turtle.getId(), stepHistory);
  }
  // reset animation window
  resetAnimation();

  // calculate intermediate turtle states needed to smoothly animate each turtle
  TurtleAnimator.animateSteps(map);

  // get list of intermediate turtle states needed to smoothly animate each turtle
  Map<Integer, List<TurtleState>> animateMap = TurtleAnimator.stepForward():
  // redraw turtle animation
  while (!animateMap.isEmpty()) {
    for (int turtleId: animationMap.keySet()) {
      List<TurtleState> inbetweenStates = animationMap.get(turtleId);
      for (int i = 0; i < inbetweenStates.size(); i+=2) {
        drawLine(inbetweenStates.get(i-1), inbetweenStates.get(i));
        updateTurtleView(turtleId, inbetweenStates.get(i-1));
        updateTurtleView(turtleId, inbetweenStates.get(i));
      }
    }
    animateMap = TurtleAnimator.stepForward():
  }
}
//2. SLOGO 75, 77: The user clicks on step back in the animation and sees the animation go back one command.
public void stepBackTurtle() {
    // get list of intermediate turtle states needed to smoothly animate each turtle one step in its step history
    Map<Integer, List<TurtleState>> animateMap = TurtleAnimator.stepBackward():

    for (int turtleId: animationMap.keySet()) {
      List<TurtleState> inbetweenStates = animationMap.get(turtleId);
      for (int i = 0; i < inbetweenStates.size(); i+=2) {
          // erase line
          eraseLine(inbetweenStates.get(i-1), inbetweenStates.get(i));
          updateTurtleView(turtleId, inbetweenStates.get(i-1));
          updateTurtleView(turtleId, inbetweenStates.get(i));
      }
    }
}
//3. SLOGO 75, 77: The user clicks on step forward in the animation when there is no more commands in the turtle's step history.
public void stepFowardTurtle() throws InvalidStepException() {
    // get list of intermediate turtle states needed to smoothly animate each turtle one step in its step history
    Map<Integer, List<TurtleState>> animateMap = TurtleAnimator.stepForward():
    // check if can go forward
    if (animateMap.isEmpty()) {
      throw new InvalidStepException("Cannot step forward. No steps left in step history for any turtle");
    }
    for (int turtleId: animationMap.keySet()) {
      List<TurtleState> inbetweenStates = animationMap.get(turtleId);
      for (int i = 0; i < inbetweenStates.size(); i+=2) {
        // draw line
        drawLine(inbetweenStates.get(i-1), inbetweenStates.get(i));
        updateTurtleView(turtleId, inbetweenStates.get(i-1));
        updateTurtleView(turtleId, inbetweenStates.get(i));
      }
    }
}
//4. SLOGO 74-77: The user clicks on clear animation and sees the turtle position resets and the drawing clears.
public void resetAnimation(){
    // reset the turtle to its initial postion
    TurtleAnimator.resetTurtles(session.getTurtles());
    // clear animation window
    resetAnimationWindow()
}