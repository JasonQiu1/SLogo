package slogo.view.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITurtle;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class TurtleController extends UIController {

  public static final String TURTLE_XML = "src/main/resources/selected_turtle.xml";

  private double x;
  private double y;
  private double rotation;

  @Override
  public void notifyController(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "textField" -> setMovement();
      case "button" -> handleButtonInput((UIButton) element);
    }
    updateElements();
  }

  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void handleButtonInput(UIButton button) {
    switch (button.getID()) {
      case "TurtleSelector" -> saveTurtleSelection(button.getMyPath());
    }
  }

  private void saveTurtleSelection(String path) {

    try {
      FileOutputStream file = new FileOutputStream(TURTLE_XML);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element backgroundTheme = doc.createElement("BackgroundTheme");
      backgroundTheme.setTextContent(path);
      doc.appendChild(backgroundTheme);
      writeXml(doc, file);
    } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateElement(UIElement element) {
    if (element.getType().equalsIgnoreCase("turtle")) {
      changePosition((UITurtle) element);
    }
  }

  private void setMovement() {
    x = 0;
    y = 0;
    rotation = 0;
  }

  private void changePosition(UITurtle turtle) {
    turtle.moveX(x);
    turtle.moveY(y);
    turtle.rotate(rotation);
  }


}
