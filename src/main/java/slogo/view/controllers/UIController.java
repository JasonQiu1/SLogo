package slogo.view.controllers;

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
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;

/**
 * UIController interface defines methods to manage UI elements and update UI themes.
 *
 * @author Jeremyah Flowers
 */
public abstract class UIController {

  // Instance Variable
  private final Collection<UIElement> myElements;

  public UIController() {
    myElements = new ArrayList<>();
  }

  protected static void writeXml(Document doc, OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);

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
  public abstract void notifyController(UIElement element);

  protected Collection<UIElement> getMyElements() {
    return myElements;
  }

  protected void selectButton(UIButton button, boolean flag) {
    button.setStatus(flag);
  }
}
