package slogo.controller.controllers;

import java.util.Collection;

import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;

/**
 * SpeedController class implements UIController interface to manage speed UI elements. It
 * provides functionality to control the speed of the animation.
 *
 * @author Jeremyah Flowers, Judy He
 */
public class SpeedController extends UIController {

    // Instance Variables
    private boolean halfFlag = false;
    private boolean oneFlag = true;
    private boolean twoFlag = false;
    private boolean fourFlag = false;

    /**
     * Notifies the speed controller about changes in UI elements.
     *
     * @param element the UI element that triggered the notification
     */
    @Override
    public void notifyController(UIElement element) {
        switch (element.getID()) {
            case "0.5x" -> setSpeedHalf();
            case "1x" -> setSpeedOne();
            case "2x" -> setSpeedTwo();
            case "4x" -> setSpeedFour();
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
            case "0.5x" -> {
                selectButton((UIButton) element, halfFlag);
            }
            case "1x" -> {
                selectButton((UIButton) element, oneFlag);
            }
            case "2x" -> {
                selectButton((UIButton) element, twoFlag);
            }
            case "4x" -> {
                selectButton((UIButton) element, fourFlag);
            }
        }
    }

    private void setSpeedFour() {
        this.getTurtleAnimator().setSpeed(4);
        halfFlag = false;
        oneFlag = false;
        twoFlag = false;
        fourFlag = true;
    }

    private void setSpeedTwo() {
        this.getTurtleAnimator().setSpeed(2);
        halfFlag = false;
        oneFlag = false;
        twoFlag = true;
        fourFlag = false;
    }

    private void setSpeedOne() {
        this.getTurtleAnimator().setSpeed(1);
        halfFlag = false;
        oneFlag = true;
        twoFlag = false;
        fourFlag = false;
    }

    private void setSpeedHalf() {
        this.getTurtleAnimator().setSpeed(0.5);
        halfFlag = true;
        oneFlag = false;
        twoFlag = false;
        fourFlag = false;
    }
}
