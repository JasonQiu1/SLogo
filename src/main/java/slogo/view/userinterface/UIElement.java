package slogo.view.userinterface;

import javafx.scene.Node;
import slogo.view.listeners.UIListener;


/**
 * Represents a basic UI element in the Slogo user interface.
 *
 * @author Jeremyah Flowers
 */
public class UIElement {

  // Instance Variables
  private final Node myElement;
  private UIListener myListener;
  private String myType;

  /**
   * Constructor for UIElement.
   *
   * @param nodeType The JavaFX node representing the UI element.
   * @param id       The unique identifier for the UI element.
   */
  public UIElement(Node nodeType, String id) {
    myElement = nodeType;
    myType = nodeType.getTypeSelector();
    myElement.setId(id);
  }

  /**
   * Retrieves the JavaFX node representing the UI element.
   *
   * @return The JavaFX node.
   */
  public Node getElement() {
    return myElement;
  }

  /**
   * Retrieves the unique identifier for the UI element.
   *
   * @return The unique identifier.
   */
  public String getID() {
    return myElement.getId();
  }

  /**
   * Retrieves the type of the UI element.
   *
   * @return The type of the UI element.
   */
  public String getType() {
    return myType;
  }

  public void setListener(UIListener listener) {
    myListener = listener;
  }

  protected void sendSignal() {
    myListener.sendSignal(this);
  }

  protected void setSpecialType(String type) {
    myType = type;
  }

  protected void setPosition(double x, double y) {
    myElement.setLayoutX(x - myElement.getBoundsInLocal().getWidth() / 2);
    myElement.setLayoutY(y - myElement.getBoundsInLocal().getHeight() / 2);
  }


}
