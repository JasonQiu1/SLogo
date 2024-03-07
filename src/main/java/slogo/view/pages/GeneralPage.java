package slogo.view.pages;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.controller.listeners.UIListener;
import slogo.view.userinterface.ExternalButton;
import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIDropDown;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIListView;
import slogo.view.userinterface.UIRegion;
import slogo.view.userinterface.UIText;
import slogo.view.userinterface.UITextField;
import slogo.view.userinterface.UITurtle;

/**
 * The GeneralPage class represents a generic page in the SLogo application. It provides methods for
 * setting up and managing UI elements on the page. It serves as a base class for specific page
 * implementations.
 *
 * @author Jeremyah Flowers, Jordan Haytaian
 */
public abstract class GeneralPage {
  private static final String PAGE_XML = "src/main/resources/ViewConfigurations/lastpage.xml";

  // Instance Variable
  private final Stage myStage;
  private final UIListener myListener;

  /**
   * Constructs a GeneralPage object with the specified stage and UI listener.
   *
   * @param stage    the JavaFX stage for the page
   * @param listener the UI listener for handling UI events
   */
  public GeneralPage(Stage stage, UIListener listener) {
    myStage = stage;
    myListener = listener;
    setLastPage(this.getClass().getName());
  }


  /**
   * Sets up the page layout based on the screen width and height.
   *
   * @param screenWidth  the width of the screen
   * @param screenHeight the height of the screen
   */
  public abstract void setPage(double screenWidth, double screenHeight);

  /**
   * Retrieves the JavaFX parent node representing the page.
   *
   * @return the parent node of the page
   */
  public abstract Parent getPage();

  /**
   * Creates UI elements based on the provided IDs and type.
   *
   * @param IDs  a map containing element IDs and their positions
   * @param type the type of UI element to create
   * @return a collection of created UI elements
   */
  protected Collection<UIElement> createElements(Map<String, double[]> IDs, String type) {
    Set<UIElement> elements = new HashSet<>();
    for (String name : IDs.keySet()) {
      double[] position = IDs.get(name);
      UIElement newElement = createElement(type, name, position);
      newElement.setListener(myListener);
      elements.add(newElement);
    }
    myListener.passElementsToController(elements);
    return elements;
  }

  /**
   * Creates UI List Elements based on the ID
   *
   * @param ID       Identifying tag of list element
   * @param options  Options to be included in list
   * @param position X and Y coordinates
   * @return Created UI Element
   */
  protected UIElement createListElement(String ID, ObservableList<String> options,
      double[] position) {
    switch (ID.toLowerCase()) {
      case "languages/idiomas/langues" -> {
        UIDropDown dropDown = new UIDropDown(ID, options, position[0], position[1]);
        dropDown.setListener(myListener);
        return dropDown;
      }
      case "commands" -> {
        UIListView listView = new UIListView(ID, options, position[0], position[1]);
        listView.setListener(myListener);
        return listView;
      }
      default -> throw new TypeNotPresentException(ID, new Throwable());
    }
  }

  private UIElement createElement(String type, String ID, double[] position) {
    switch (type) {
      case "internalbutton" -> {
        return new InternalButton(ID, position[0], position[1]);
      }
      case "externalbutton" -> {
        return new ExternalButton(ID, position[0], position[1]);
      }
      case "text" -> {
        return new UIText(ID, position[0], position[1]);
      }
      case "checkbox" -> {
        return new UICheckBox(ID, position[0], position[1]);
      }
      case "textfield" -> {
        return new UITextField(ID, position[0], position[1]);
      }
      case "turtle" -> {
        return new UITurtle(ID, position[0], position[1]);
      }
      case "region" -> {
        return new UIRegion(ID, position[0], position[1], position[2], position[3]);
      }
      default -> throw new TypeNotPresentException(type, new Throwable());
    }
  }

  private void setLastPage(String name) {
    try {
      FileOutputStream file = new FileOutputStream(PAGE_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element backgroundTheme = doc.createElement("BackgroundTheme");
      backgroundTheme.setTextContent(name);
      doc.appendChild(backgroundTheme);
      writeXml(doc, file);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  protected static void writeXml(Document doc, OutputStream output) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);
  }
}
