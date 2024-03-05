package slogo.controller.listeners;

import java.util.Collection;

import slogo.view.userinterface.UIElement;

public class HelpListener implements UIListener {

    @Override
    public void sendSignal(UIElement element) {

    }

    /**
     * Passes UI elements to their respective controllers for processing.
     *
     * @param elements the collection of UI elements to pass
     */
    @Override
    public void passElementsToController(Collection<UIElement> elements) {
    }

}
