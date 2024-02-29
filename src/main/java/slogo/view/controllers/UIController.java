package slogo.view.controllers;

import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.Node;
import slogo.view.userinterface.UIElement;

/**
 * UIController interface defines methods to manage UI elements and update UI themes.
 *
 * @author Jeremyah Flowers
 */
public abstract class UIController {

  private final Collection<UIElement> myElements;

  public UIController() {
    myElements = new ArrayList<>();
  }

  public void addElement(UIElement element) {
    myElements.add(element);
  }

  public void addAllElements(Collection<UIElement> elements) {
    myElements.addAll(elements);
  }

  /**
   * Notifies the controller about changes in UI elements, updating the UI theme accordingly.
   *
   * @param element the UI element triggering the notification
   */
  public abstract void notifyController(Node element);

  protected Collection<UIElement> getMyElements() {
    return myElements;
  }
}
