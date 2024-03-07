package slogo.controller.controllers;

import java.util.Collection;

import javafx.scene.paint.Color;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITurtle;

/**
 * PenController class implements UIController interface to manage pen color UI elements. It
 * provides functionality to control the color of the pen.
 *
 * @author Jeremyah Flowers
 */
public class PenController extends UIController {

    // Instance Variables
    private boolean redFlag = true;
    private boolean greenFlag = true;
    private boolean blueFlag = true;
    private final int RED_INDEX = 1;
    private final int GREEN_INDEX = 2;
    private final int BLUE_INDEX = 3;


    /**
     * Notifies the pen controller about changes in UI elements.
     *
     * @param element the UI element that triggered the notification
     */
    @Override
    public void notifyController(UIElement element) {
        switch (element.getID()) {
            case "R" -> setPenR();
            case "G" -> setPenG();
            case "B" -> setPenB();
        }
        updateElements();
    }

    // Private helper methods

    private void updateElements() {
        Collection<UIElement> allElements = getMyElements();
        allElements.forEach(this::updateElement);
    }

    private void updateElement(UIElement element) {
        switch (element.getID()) {
            case "R" -> {
                selectButton((UIButton) element, redFlag);
            }
            case "G" -> {
                selectButton((UIButton) element, greenFlag);
            }
            case "B" -> {
                selectButton((UIButton) element, blueFlag);
            }
            case "Turtle" -> {
                setTurtlePen((UITurtle) element);
            }
        }
    }

    private void setTurtlePen(UITurtle turtle) {
        // Any option: equivalent to pen down
        if(isBlack()) turtle.setPenColor(Color.BLACK);
        else if(isTeal()) turtle.setPenColor(Color.TEAL);
        else if(isBrown()) turtle.setPenColor(Color.BROWN);
        else if(isPurple()) turtle.setPenColor(Color.PURPLE);
        else if(redFlag) turtle.setPenColor(Color.RED);
        else if(greenFlag) turtle.setPenColor(Color.GREEN);
        else if(blueFlag) turtle.setPenColor(Color.BLUE);

        // Equivalent to pen up
        else turtle.setPenColor(Color.TRANSPARENT);
    }

    private void clearLastTurtleLine(UITurtle turtle) {
        turtle.clearLastLine();
    }
    private void clearTurtleDrawing(UITurtle turtle) {
        turtle.clearScreen();
    }

    private boolean isPurple() {
        return redFlag && blueFlag;
    }

    private boolean isBrown() {
        return redFlag && greenFlag;
    }

    private boolean isTeal() {
        return greenFlag && blueFlag;
    }

    private boolean isBlack() {
        return redFlag && greenFlag && blueFlag;
    }

    private void setPenR() {
        redFlag = !redFlag;
    }

    private void setPenG() {
        greenFlag = !greenFlag;
    }

    private void setPenB() {
        blueFlag = !blueFlag;
    }


}
