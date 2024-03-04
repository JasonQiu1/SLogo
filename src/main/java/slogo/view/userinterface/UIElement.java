package slogo.view.userinterface;

import static slogo.view.controllers.LanguageController.LANGUAGE_TAG;
import static slogo.view.controllers.LanguageController.LANGUAGE_XML;

import java.io.File;
import javafx.scene.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import slogo.view.listeners.UIListener;

/**
 * Represents a basic UI element in the Slogo user interface. It encapsulates methods to retrieve
 * information about the UI element, set up listeners, and send signals to listeners.
 *
 * @author Jeremyah Flowers, Jordan Haytaian
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

  /**
   * Sets the listener for the UI element.
   *
   * @param listener The listener to set.
   */
  public void setListener(UIListener listener) {
    myListener = listener;
  }

  /**
   * Sends a signal to the listener associated with the UI element.
   */
  protected void sendSignal() {
    myListener.sendSignal(this);
  }

  /**
   * Sets the special type of the UI element.
   *
   * @param type The special type to set.
   */
  protected void setSpecialType(String type) {
    myType = type;
  }

  /**
   * Sets the position of the UI element.
   *
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   */
  protected void setPosition(double x, double y) {
    myElement.setLayoutX(x - myElement.getBoundsInLocal().getWidth() / 2);
    myElement.setLayoutY(y - myElement.getBoundsInLocal().getHeight() / 2);
  }

  /**
   * Gets the current user-selected language of the application with english as default
   *
   * @return the current user-selected language
   */
  protected String getLanguage() {
    try {
      File file = new File(LANGUAGE_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
      return doc.getElementsByTagName(LANGUAGE_TAG).item(0).getTextContent();

    } catch (Exception e) {
      return "english";
    }
  }
}
