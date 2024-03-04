package slogo.view.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.InputMismatchException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.view.userinterface.UIButton;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UITextField;
import slogo.view.userinterface.UITurtle;


/**
 * GraphicsController class implements UIController interface to manage graphical UI elements. It
 * provides functionality to control the appearance of the UI graphics.
 *
 * @author Jeremyah Flowers
 */
public class TurtleController extends UIController {

  public static final String TURTLE_XML = "src/main/resources/selected_turtle.xml";
  private String lastText = "";
  private double nextY, nextHeading;

  @Override
  public void notifyController(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "textfield" -> setMovement((UITextField) element);
      case "button" -> handleButtonInput((UIButton) element);
    }
    updateElements();
  }

  private void setMovement(UITextField textField) {
    String[] newestText = textField.getText();
    for (String newText : newestText) {
      parseTxt(newText);
    }
  }

  private void parseTxt(String text) {
    switch (text) {
      case "fd" -> nextY = 1;
      case "bk" -> nextY = -1;
      case "rt" -> nextHeading = 1;
      case "lt" -> nextHeading = -1;
      default -> setNumeric(text);
    }
    lastText = text;
  }

  private void setNumeric(String text) {
    if (isNumeric(text)) {
      double parsedNumber = Double.parseDouble(text);
      switch (lastText) {
        case "fd", "bk" -> {
          nextY *= parsedNumber;
        }
        case "rt", "lt" -> {
          nextHeading *= parsedNumber;
        }
      }
    } else {
      throw new InputMismatchException(
          "Command not found. Please read help documentation or try again.");
    }
  }

  private boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    try {
      double d = Double.parseDouble(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }


  private void updateElements() {
    Collection<UIElement> allElements = getMyElements();
    allElements.forEach(this::updateElement);
  }

  private void handleButtonInput(UIButton button) {
    if (button.getID().equals("TurtleSelector")) {
      saveTurtleSelection(button.getMyPath());
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
    switch (element.getType().toLowerCase()) {
      case "textfield" -> setMovement((UITextField) element);
      case "turtle" -> changePosition((UITurtle) element);
    }
  }

  private void changePosition(UITurtle turtle) {
    turtle.createAnimation(0, nextY, nextHeading);
    turtle.updatePosition();
  }
}
