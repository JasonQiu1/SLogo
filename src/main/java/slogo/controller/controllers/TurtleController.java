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
    private Timeline animation;
    private static final double SECOND_DELAY = 1;
    private Map<Integer, TurtleState> currentFrame;
    private int numCommands;
//    private String lastText = "";
//    private double nextY, nextHeading;

    /**
     * Notifies the turtle controller about changes in UI elements.
     *
     * @param element the UI element that triggered the notification
     */
    @Override
    public void notifyController(UIElement element) {
        switch (element.getType().toLowerCase()) {
            case "textfield" -> runCommands((UITextField) element);
            case "button" -> handleButtonInput((UIButton) element);
        }
        updateElements();
    }

    // Private helper methods
    private void setAnimation() {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        double frameDuration = 1 / (this.getTurtleAnimator().STANDARD_FPS); // Calculate the duration for the KeyFrame

        animation.getKeyFrames()
            .add(new KeyFrame(Duration.seconds(frameDuration), e -> animateTurtleViews()));
        animation.play();
    }

    private void runCommands(UITextField textFieldView) {
        this.startNewSession();
        String commands = textFieldView.getTextCommands();
        // this.numCommands = this.getCurrentSession().run(commands);
        this.numCommands = 6;
        this.getCurrentSession().run(commands);
    }

    private void updateElements() {
        Collection<UIElement> allElements = getMyElements();
        allElements.forEach(this::updateElement);
    }

    private void handleButtonInput(UIButton button) {
        if (button.getID().equals("TurtleSelector")) {
            saveTurtleSelection(button.getMyPath());
        }
    }

    private void saveTurtleSelection(String path) {
        try {
            FileOutputStream file = new FileOutputStream(TURTLE_XML);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element backgroundTheme = doc.createElement("BackgroundTheme");
            backgroundTheme.setTextContent(path);
            doc.appendChild(backgroundTheme);
            writeXml(doc, file);
        } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateElement(UIElement element) {
        switch (element.getType().toLowerCase()) {
            case "textfield" -> resetTextField((UITextField) element);
            case "turtle" -> animateTurtleViews((UITurtle) element);
        }
    }

    private void animateTurtleViews(UITurtle turtleView) {
        addTurtleView(turtleView);
        updateTurtleViews();
        setAnimation();
    }

    private void resetTextField(UITextField textFieldView) {
        textFieldView.reset();
    }

    private void addTurtleView(UITurtle turtleView) {
        this.TURTLE_VIEWS.put(turtleView.getID(), turtleView);
    }

    private void updateTurtleViews() {
        Map<Integer, List<TurtleStep>> totalSteps = this.getCurrentSession().getTurtlesStepHistories(numCommands);
        this.getTurtleAnimator().animateStep(totalSteps);
        this.currentFrame = this.getTurtleAnimator().nextFrame();
    }

    private void animateTurtleViews() {
        if (this.currentFrame.isEmpty()) {
            return;
        }
        for (Integer turtleId : this.currentFrame.keySet()) {
            TurtleState state = this.currentFrame.get(turtleId);
            UITurtle turtleView = this.TURTLE_VIEWS.get(String.valueOf(turtleId));
            turtleView.updateState(state.position().getX(), state.position().getY(), state.heading());
        }
        this.currentFrame = this.getTurtleAnimator().nextFrame();
    }

}