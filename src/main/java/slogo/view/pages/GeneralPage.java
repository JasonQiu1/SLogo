package slogo.view.pages;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.view.controllers.UIController;
import slogo.view.userinterface.ExternalButton;
import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;
import slogo.view.userinterface.UIText;
import slogo.view.userinterface.UITextField;


/**
 * The GeneralPage class represents a generic page in the SLogo application. It provides methods for
 * setting up and managing UI elements on the page.
 *
 * @author Jeremyah Flowers
 */
public abstract class GeneralPage {

  // Instance Variable
  private final Stage myStage;
  private final UIController myController;

  /**
   * Constructs a GeneralPage object with the specified stage.
   *
   * @param stage the JavaFX stage for the page
   */

  public GeneralPage(Stage stage, UIController controller) {
    myStage = stage;
    myController = controller;
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
   * @return a collection of UI elements
   */
  protected Collection<UIElement> createElements(Map<String, double[]> IDs, String type) {
    Set<UIElement> elements = new HashSet<>();
    for (String name : IDs.keySet()) {
      double[] position = IDs.get(name);
      UIElement newElement = createElement(type, name, position);
      newElement.setController(myController);
      elements.add(newElement);
    }
    myController.addAllElements(elements);
    return elements;
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
      case "region" -> {
        return new UIRegion(ID, position[0], position[1], position[2], position[3]);
      }
      default -> throw new TypeNotPresentException(type, new Throwable());
    }
  }
}
