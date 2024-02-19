//1. SLOGO 76, 77: The user clicks on replay animation and sees the animation resets and reruns its previous series of commands.
public void replay() {
  // get turtle's step history
  List<Step> stepHistory = turtle.getStepHistory();
  // clear animation window
  clearAnimation();

  // redraw turtle animation
  for (Step step: stepHistory) {
    turtle.doStep(step.getLength(), step.getChangeInAngle());
    drawLine(step.getInitialPos(), step.getFinalPos());
    animateTurtleView(step.getFinalPos());
  }
}
//2. SLOGO 75, 77: The user clicks on step back in the animation and sees the animation go back one command.
public void stepBackTurtle() {
    // get turtle's step
    Step step = turtle.stepBack();
    // erase most recent line
    eraseLine(step.getInitialPos(), step.getFinalPos());
    // move turtle back one step in its step history
    turtle.doStep(step.getLength(), step.getChangeInAngle());
    animateTurtleView(step.getInitialPos());
}
//3. SLOGO 75, 77: The user clicks on step forward in the animation when there is no more commands in the turtle's step history.
public void stepFowardTurtle() throws InvalidStepException() {
    // get turtle's step
    Step step = turtle.stepForward();
    // check if step exists
    if (step == null) {
      throw new InvalidStepException("Cannot step forward. No steps left in turtle's step history");
    }
    // draw next line
    drawLine(step.getInitialPos(), step.getFinalPos());
    // move turtle forward one step in its step history
    turtle.doStep(step.getLength(), step.getChangeInAngle());
    animateTurtleView(step.getInitialPos());
}
//4. SLOGO 74-77: The user clicks on clear animation and sees the turtle position resets and the drawing clears.
public void clearAnimation(){
    // reset the turtle to its initial postion
    animateTurtleView(turtle.reset());
    // clear animation window
    clearAnimationWindow()
}