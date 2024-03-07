package slogo.controller.listeners;

import java.util.Collection;
import slogo.controller.controllers.HelpController;
import slogo.controller.controllers.XmlController;
import slogo.view.userinterface.UIElement;

public class HelpListener implements UIListener{

    HelpController myHelpController = new HelpController();
    XmlController myXmlController = new XmlController();

    @Override
    public void sendSignal(UIElement element) {
        switch (element.getID().toLowerCase()) {
            case "library commands" -> myXmlController.notifyController(element);
        }

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
