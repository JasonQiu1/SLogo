package View.Pages;

import View.UserInterface.UIButton;
import View.UserInterface.UICheckBox;
import View.UserInterface.UIElement;
import View.UserInterface.UIText;
import View.UserInterface.UITextField;
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

  public abstract void setPage(double screenWidth, double screenHeight);

  public abstract Parent getPage();

  protected Collection<UIElement> createElements(Map<String, double[]> IDs, String type) {
    Set<UIElement> elements = new HashSet<>();
    for (String name : IDs.keySet()) {
      double[] position = IDs.get(name);
      UIElement newElement = createElement(type, name, position);
      elements.add(newElement);
    }
    return elements;
  }

  protected void styleUI(Collection<UIElement> UIElements, Group root) {
    for (UIElement element : UIElements) {
      switch (element.getType().toLowerCase()) {
        case "button" -> loadButton((UIButton) element, element.getID());
        case "text" -> loadText((UIText) element, element.getID());
        case "checkbox" -> loadBox((UICheckBox) element, element.getID());
        case "textfield" -> loadTextField((UITextField) element, element.getID());
        default -> throw new TypeNotPresentException(element.getType(), new Throwable());
      }
      root.getChildren().add(element.getElement());
    }
  }

  private void loadButton(UIButton button, String ID) {
    switch (ID) {
      case "Turtle Selector" -> {
        button.setSelectorClassic();
        button.addFolderOpener(myStage, "turtle_images");
      }
      case "Load" -> {
        button.setMenuClassic();
        button.addFolderOpener(myStage, "saved_files");
      }
      case "Save" -> {
        button.setMenuClassic();
        button.addSaveFolder(myStage, "saved_files");
      }
      case "Create" -> {
        button.setMenuClassic();
        button.addOpenGUI(myStage);
      }
      case "Help" -> {
        button.setMenuClassic();
        // TODO: MAKE BUTTON JUMP TO HELP PAGE
      }
      case "Play/Pause" -> {
        button.setPausePlayClassic();
        // TODO: MAKE BUTTON PLAY/PAUSE SIMULATION
      }
      case "1x", "2x", "3x", "4x" -> {
        button.setSpeedClassic();
      }
      case "R", "G", "B" -> {
        button.setPenClassic();
      }
      case "Variables", "Commands" -> {
        button.setGUIClassic();
        // TODO: MAKE BUTTON DISPLAY VARIABLES AND COMMAND
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadBox(UICheckBox checkBox, String ID) {
    switch (ID) {
      case "Light", "Dark" -> {
        checkBox.setThemeCheckBox();
        // TODO: MAKE BUTTON JUMP TO SLOGO WIKI
      }
      case "BK/WH", "GN/BL", "PK/PR" -> {
        checkBox.setBackgroundCheckBox();
      }

      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadText(UIText text, String ID) {
    switch (ID) {
      case "SLOGO" -> {
        text.setSlogoClassic();
      }
      case "Theme:", "Pen Colors:", "Speed:" -> {
        text.setRegularClassic();
      }
      case "Background:" -> {
        text.setSmallerClassic();
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadTextField(UITextField textField, String ID) {
    switch (ID) {
      case "Insert Command Here" -> {
        textField.setupTextBox();
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private UIElement createElement(String type, String ID, double[] position) {
    switch (type) {
      case "button" -> {
        return new UIButton(ID, position[0], position[1]);
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
      default -> throw new TypeNotPresentException(type, new Throwable());
    }
  }
}
