package slogo.view.userinterface;

import javafx.scene.Node;


/**
 * Represents a basic UI element in the Slogo user interface.
 *
 * @author Jeremyah Flowers
 */
public class UIElement {

  // Instance Variables
  private final Node UIBase;
  private final String myID;
  private String myType;


  /**
   * Constructor for UIElement.
   * @param nodeType The JavaFX node representing the UI element.
   * @param ID The unique identifier for the UI element.
   */
  public UIElement(Node nodeType, String ID) {
    UIBase = nodeType;
    myType = nodeType.getTypeSelector();
    myID = ID;
  }

  /**
   * Retrieves the JavaFX node representing the UI element.
   * @return The JavaFX node.
   */
  public Node getElement() {
    return UIBase;
  }

  /**
   * Retrieves the unique identifier for the UI element.
   * @return The unique identifier.
   */
  public String getID() {
    return myID;
  }

  /**
   * Retrieves the type of the UI element.
   * @return The type of the UI element.
   */
  public String getType() {
    return myType;
  }

  protected void setSpecialType(String type) {
    myType = type;
  }

  protected void setPosition(double x, double y) {
    UIBase.setLayoutX(x - UIBase.getBoundsInLocal().getWidth() / 2);
    UIBase.setLayoutY(y - UIBase.getBoundsInLocal().getHeight() / 2);
  }
}
