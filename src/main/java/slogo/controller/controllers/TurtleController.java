package slogo.controller.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.model.api.turtle.TurtleAnimator;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;
import slogo.view.userinterface.UITurtle;
import slogo.view.windows.HelpWindow;
import tool.XmlHelper;

/**
 * TurtleController class implements UIController interface to manage turtle UI elements. It
 * provides functionality to control the movement and appearance of the turtle.
 *
 * @author Jeremyah Flowers, Judy He
 */
public class TurtleController extends UIController {

  // Instance Variables
  public static final String TURTLE_XML = "src/main/resources/selected_turtle.xml";
  private final Map<String, UITurtle> TURTLE_VIEWS = new HashMap<>();
  private final XmlConfiguration myXmlConfig = new XmlConfiguration();
  private final TurtleAnimator myTurtleAnimator;
  private Timeline animation = new Timeline();
  private Map<Integer, TurtleState> currentFrame;
  private int framesRan;
  private int numCommands;
  private boolean animationOnPause;

  public TurtleController() {
    myTurtleAnimator = new TurtleAnimator();
  }

  /**
   * Notifies the turtle controller about changes in UI elements.
   *
   * @param element the UI element that triggered the notification
   */
  @Override
  public void notifyController(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "textfield" -> {
        if (element.getID().equalsIgnoreCase("CommandLine")) {
          runCommands((UITextField) element);
        }
        updateElements();
      }
      case "internalbutton" -> handleButtonInput((UIButton) element);
      case "listview" -> updateElements();
      case "externalbutton" -> {
        loadCommands(element);
        updateElements();
      }
    }
  }

  // Private helper methods
  private void setAnimation() {
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    double frameDuration = 1.0 / (myTurtleAnimator.getSpeed()
        * this.myTurtleAnimator.STANDARD_FPS); // Calculate the duration for the KeyFrame
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(frameDuration), e -> step()));
    animation.play();
  }

  private void runCommands(UITextField textFieldView) {
    String commands = textFieldView.getTextCommands();
    this.numCommands = this.getCurrentSession().run(commands);
  }

  private void loadCommands(UIElement element) {
    String filePath = ((UIButton) element).getMyPath();
    try {
      List<String> commands = myXmlConfig.loadSessionFromFile(filePath);
      String allCommands = String.join(" ", commands);
      this.numCommands = getCurrentSession().run(allCommands);
    } catch (XmlException e) {
      new HelpWindow("error", getCurrentSession(), "unable to load file");
    }
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void handleButtonInput(UIButton button) {
    switch (button.getID()) {
      case "TurtleSelector" -> saveTurtleSelection(button.getMyPath());
      case "Play/Pause" -> pausePlayAnimation();
      case "Step" -> manualStep();
      case "0.5x" -> updateAnimationSpeed(0.5);
      case "1x" -> updateAnimationSpeed(1);
      case "2x" -> updateAnimationSpeed(2);
      case "4x" -> updateAnimationSpeed(4);
      case "Reset" -> replayAnimation();
    }
  }

  private void saveTurtleSelection(String path) {
    new XmlHelper().updateFile(path, "SelectedTurtle", TURTLE_XML);
  }

  private void updateElement(UIElement element) {
    if (element.getType().equalsIgnoreCase("turtle")) {
      animateTurtleViews((UITurtle) element);
    }
  }

  private void animateTurtleViews(UITurtle turtleView) {
    addTurtleView(turtleView);
    updateTurtleViews();
    setAnimation();
  }

  private void addTurtleView(UITurtle turtleView) {
    this.TURTLE_VIEWS.put(turtleView.getID() + (TURTLE_VIEWS.size() + 1), turtleView);
  }

  private void updateTurtleViews() {
    framesRan = 0;
    Map<Integer, List<TurtleStep>> totalSteps = this.getCurrentSession()
        .getTurtlesStepHistories(numCommands);
    myTurtleAnimator.animateStep(totalSteps);
    this.currentFrame = myTurtleAnimator.nextFrame();
  }

  private void pausePlayAnimation() {
    animationOnPause = !animationOnPause;
  }

  private void updateAnimationSpeed(double speed) {
    myTurtleAnimator.setSpeed(speed);
    double frameDuration = 1.0 / (myTurtleAnimator.getSpeed()
        * myTurtleAnimator.STANDARD_FPS); // Calculate the duration for the KeyFrame
    animation.setDelay(Duration.seconds(frameDuration));
    animation.setRate(myTurtleAnimator.getSpeed());
  }

  private void step() {
    if (!animationOnPause && !this.currentFrame.isEmpty()) {
      animateNextFrame();
    }
  }

  private void manualStep() {
    if (animationOnPause) {
      animateNextFrame();
    }
  }

  private void animateNextFrame() {
    for (Integer turtleId : this.currentFrame.keySet()) {
      TurtleState state = this.currentFrame.get(turtleId);
      UITurtle turtleView = this.TURTLE_VIEWS.get("Turtle" + turtleId);
      if (turtleView.isShowing()) {
        turtleView.setPenDown(true); // turn on pen
        turtleView.updateState(state.position().getX(), state.position().getY(),
            state.heading());
      }
    }
    framesRan++;
    this.currentFrame = myTurtleAnimator.nextFrame();
  }

  private void replayAnimation() {
    this.currentFrame = myTurtleAnimator.resetFrame(framesRan);
    for (Integer turtleId : this.currentFrame.keySet()) {
      TurtleState state = this.currentFrame.get(turtleId);
      UITurtle turtleView = this.TURTLE_VIEWS.get("Turtle" + turtleId);
      for (int i = 0; i < framesRan - 2; i++) {
        turtleView.clearLastLine();
      }
      // turn off pen
      turtleView.setPenDown(false);
      // reset turtle position
      turtleView.updateState(state.position().getX(), state.position().getY(), state.heading());
      // reset frames ran
      framesRan = 0;
      // turn on pen
      turtleView.setPenDown(true);
    }
  }
}