package slogo.controller.controllers;

import java.util.ArrayList;
import java.util.Collection;
import slogo.model.api.Session;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;

/**
 * UIController interface defines methods to manage UI elements and update UI themes. It provides
 * functionality to handle UI elements and themes. This interface serves as a base for various UI
 * controllers.
 *
 * @author Jeremyah Flowers, Judy He
 */
public abstract class UIController {

  // Instance Variable
  private final Collection<UIElement> myElements;
  private Session session;

  /**
   * Constructor for UIController.
   */
  public UIController() {
    myElements = new ArrayList<>();
  }

  /**
   * Adds a UI element to the collection of managed elements.
   *
   * @param element the UI element to add
   */
  public void addElement(UIElement element) {
    myElements.add(element);
  }

  /**
   * Adds a collection of UI elements to the collection of managed elements.
   *
   * @param elements the collection of UI elements to add
   */
  public void addAllElements(Collection<UIElement> elements) {
    myElements.addAll(elements);
  }

  /**
   * Notifies the controller about changes in UI elements, updating the UI theme accordingly.
   *
   * @param element the UI element triggering the notification
   */
  public abstract void notifyController(UIElement element);

  public void setSession(Session session) {
    this.session = session;
  }

  /**
   * Retrieves the collection of managed UI elements.
   *
   * @return the collection of managed UI elements
   */
  protected Collection<UIElement> getMyElements() {
    return myElements;
  }

  /**
   * Sets the status of a button UI element.
   *
   * @param button the button UI element
   * @param flag   the status flag to set
   */
  protected void selectButton(UIButton button, boolean flag) {
    button.setStatus(flag);
  }

  protected Session getCurrentSession() {
    return session;
  }

}
