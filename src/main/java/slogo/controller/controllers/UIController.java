package slogo.controller.controllers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
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
  protected final XmlConfiguration xmlConfiguration = new XmlConfiguration();
  protected final String helpFile = "data/commands/command_help_basic.xml";

  /**
   * Constructor for UIController.
   */
  public UIController() {
    myElements = new ArrayList<>();
  }

  /**
   * Writes XML data to the provided output stream.
   *
   * @param doc    the XML document to write
   * @param output the output stream to write to
   * @throws TransformerException if an error occurs during transformation
   */
  protected static void writeXml(Document doc, OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);
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

  public void setSession(Session session) {
    this.session = session;
  }
}
