package slogo.controller.listeners;

import java.util.Collection;

import slogo.controller.controllers.TurtleController;
import slogo.view.userinterface.UIElement;

/**
 * UIListener interface defines methods to handle UI events and pass UI elements to controllers.
 * It facilitates communication between UI elements and controllers.
 *
 * @author Jeremyah Flowers
 */
public interface UIListener {

    /**
     * Sends a signal to handle a UI element event.
     *
     * @param element the UI element triggering the event
     */
    void sendSignal(UIElement element);

    /**
     * Passes a collection of UI elements to controllers for processing.
     *
     * @param elements the collection of UI elements to pass
     */
    void passElementsToController(Collection<UIElement> elements);
}
