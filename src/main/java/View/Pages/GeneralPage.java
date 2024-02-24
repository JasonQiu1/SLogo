package View.Pages;

import View.UserInterface.UIButton;
import View.UserInterface.UICheckBox;
import View.UserInterface.UIElement;
import View.UserInterface.UIText;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;

public abstract class GeneralPage {

  private final Stage myStage;

  public GeneralPage(Stage stage) {
    myStage = stage;
  }

  protected void styleUI(Collection<UIElement> UIElements, Group root) {
    for (UIElement element : UIElements) {
      switch (element.getType().toLowerCase()) {
        case "button" -> loadButton((UIButton) element, element.getID());
        case "text" -> loadText((UIText) element, element.getID());
        case "checkbox" -> loadBox((UICheckBox) element, element.getID());
        default -> throw new TypeNotPresentException(element.getType(), new Throwable());
      }
      root.getChildren().add(element.getElement());
    }
  }

  private void loadButton(UIButton element, String ID) {
    switch (ID) {
      case "Turtle Selector" -> {
        element.setSelectorClassic();
        element.addFolderOpener(myStage, "turtle_images");
      }
      case "Load" -> {
        element.setMenuClassic();
        element.addFolderOpener(myStage, "saved_files");
      }
      case "Create" -> {
        element.setMenuClassic();
        element.addOpenGUI(myStage);
      }
      case "Help" -> {
        element.setMenuClassic();
        // TODO: MAKE BUTTON JUMP TO SLOGO WIKI
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadBox(UICheckBox element, String ID) {
    switch (ID) {
      case "Light", "Dark" -> {
        element.setCheckbox();
        // TODO: MAKE BUTTON JUMP TO SLOGO WIKI
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadText(UIText element, String ID) {
    switch (ID) {
      case "SLOGO" -> {
        element.setSlogoClassic();
      }
      case "Theme:" -> {
        element.setThemeClassic();
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  protected Collection<UIElement> createElements(Map<String, double[]> IDs, String type) {
    Set<UIElement> elements = new HashSet<>();
    for (String name : IDs.keySet()) {
      double[] position = IDs.get(name);
      UIElement newElement = createElement(type, name, position);
      elements.add(newElement);
    }
    return elements;
  }

  private UIElement createElement(String type, String ID, double[] position) {
    switch (type) {
      case "button" -> {
        return createButton(ID, position);
      }
      case "text" -> {
        return createText(ID, position);
      }
      case "checkbox" -> {
        return createBox(ID, position);
      }
      default -> throw new TypeNotPresentException(type, new Throwable());
    }
  }

  private UIButton createButton(String text, double[] position) {
    return new UIButton(text, position[0], position[1]);
  }

  private UIText createText(String text, double[] position) {
    return new UIText(text, position[0], position[1]);
  }

  private UICheckBox createBox(String text, double[] position) {
    return new UICheckBox(text, position[0], position[1]);
  }

  public abstract void setPage(double screenWidth, double screenHeight);

  public abstract Parent getPage();


}
