package slogo.controller.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.model.api.turtle.TurtleState;
import slogo.model.api.turtle.TurtleStep;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;
import slogo.view.userinterface.UITurtle;

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
    private Timeline animation = new Timeline();;
    private double speed;
    private Map<Integer, TurtleState> currentFrame;
    private int framesRan;
    private int numCommands;
    private boolean animationOnPause;

    /**
     * Notifies the turtle controller about changes in UI elements.
     *
     * @param element the UI element that triggered the notification
     */
    @Override
    public void notifyController(UIElement element) {
        switch (element.getType().toLowerCase()) {
            case "textfield" -> {
                runCommands((UITextField) element);
                updateElements();
            }
            case "internalbutton" -> handleButtonInput((UIButton) element);
        }
    }

    // Private helper methods
    private void setAnimation() {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        double frameDuration = 1.0 / (this.getTurtleAnimator().getSpeed() * this.getTurtleAnimator().STANDARD_FPS); // Calculate the duration for the KeyFrame
        animation.getKeyFrames()
            .add(new KeyFrame(Duration.seconds(frameDuration), e -> animateNextFrame()));
        animation.play();
    }

    private void runCommands(UITextField textFieldView) {
        String commands = textFieldView.getTextCommands();
        // this.numCommands = this.getCurrentSession().run(commands);
        this.numCommands = 2;
        this.getCurrentSession().run(commands);
    }

    private void updateElements() {
        Collection<UIElement> allElements = getMyElements();
        allElements.forEach(this::updateElement);
    }

    private void handleButtonInput(UIButton button) {
        switch (button.getID()) {
            case "TurtleSelector" -> saveTurtleSelection(button.getMyPath());
            case "Play/Pause" -> pausePlayAnimation();
            case "0.5x", "1x", "2x", "4x"-> updateAnimationSpeed();
            case "Reset" -> replayAnimation();
        }
    }

    private void saveTurtleSelection(String path) {
        try {
            FileOutputStream file = new FileOutputStream(TURTLE_XML);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element turtleSelect = doc.createElement("SelectedTurtle");
            turtleSelect.setTextContent(path);
            doc.appendChild(turtleSelect);
            writeXml(doc, file);
        } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
        this.TURTLE_VIEWS.put(turtleView.getID() + ((int) TURTLE_VIEWS.size() + 1), turtleView);
    }

    private void updateTurtleViews() {
        framesRan = 0;
        Map<Integer, List<TurtleStep>> totalSteps = this.getCurrentSession().getTurtlesStepHistories(numCommands);
        this.getTurtleAnimator().animateStep(totalSteps);
        this.currentFrame = this.getTurtleAnimator().nextFrame();
    }

    private void pausePlayAnimation() {
        animationOnPause = !animationOnPause;
    }

    private void updateAnimationSpeed() {
        double frameDuration = 1.0 / (this.getTurtleAnimator().getSpeed() * this.getTurtleAnimator().STANDARD_FPS); // Calculate the duration for the KeyFrame
        animation.setDelay(Duration.seconds(frameDuration));
        animation.setRate(this.getTurtleAnimator().getSpeed());
    }

    private void animateNextFrame() {
        if (!animationOnPause && !this.currentFrame.isEmpty()) {
            for (Integer turtleId : this.currentFrame.keySet()) {
                TurtleState state = this.currentFrame.get(turtleId);
                UITurtle turtleView = this.TURTLE_VIEWS.get(String.valueOf(turtleId));
                turtleView.updateState(state.position().getX(), state.position().getY(),
                    state.heading());
            }
            framesRan++;
            this.currentFrame = this.getTurtleAnimator().nextFrame();
        }
    }

    private void replayAnimation() {
        this.currentFrame = this.getTurtleAnimator().resetFrame(framesRan);
        framesRan = 0;
        animation.play();
        for (Integer turtleId : this.currentFrame.keySet()) {
            TurtleState state = this.currentFrame.get(turtleId);
            UITurtle turtleView = this.TURTLE_VIEWS.get("Turtle" + turtleId);
            turtleView.updateState(state.position().getX(), state.position().getY(), state.heading());
        }
        this.currentFrame = this.getTurtleAnimator().nextFrame();
    }


}