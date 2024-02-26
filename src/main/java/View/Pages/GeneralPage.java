package View.Pages;

import View.UserInterface.ExternalButton;
import View.UserInterface.InternalButton;
import View.UserInterface.UICheckBox;
import View.UserInterface.UIElement;
import View.UserInterface.UIRegion;
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
        case "externalbutton" -> loadExternalButton((ExternalButton) element, element.getID());
        case "internalbutton" -> loadInternalButton((InternalButton) element, element.getID());
        case "text" -> loadText((UIText) element, element.getID());
        case "checkbox" -> loadCheckBox((UICheckBox) element, element.getID());
        case "textfield" -> loadTextField((UITextField) element, element.getID());
        case "region" -> loadRegion((UIRegion) element, element.getID());
        default -> throw new TypeNotPresentException(element.getType(), new Throwable());
      }
      root.getChildren().add(element.getElement());
    }
  }

  private void loadInternalButton(InternalButton button, String ID) {
    switch (ID) {
      case "Play/Pause" -> {
        button.setPausePlayClassic();
        // TODO: MAKE BUTTON PLAY/PAUSE SIMULATION
      }
      case "Reset" -> {
        button.setResetClassic();
        // TODO: MAKE BUTTON RESET SIMULATION
      }
      case "1x", "2x", "3x", "4x" -> {
        button.setSpeedClassic();
        // TODO: MAKE BUTTON CHANGE SIMULATION SPEED
      }
      case "R", "G", "B" -> {
        button.setPenClassic();
        // TODO: MAKE BUTTON CHANGE SIMULATION COLOR
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadRegion(UIRegion box, String ID) {
    switch (ID) {
      case "BottomBox", "TurtleBox", "RightBox" -> {
        box.setBackgroundClassic();
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadExternalButton(ExternalButton button, String ID) {
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
      case "Home" -> {
        button.setHomeClassic();
        button.addOpenSplash(myStage);
      }
      case "Variables", "Commands", "History" -> {
        button.setGUIClassic();
        // TODO: MAKE BUTTON DISPLAY FOR VARIABLES, COMMANDS, AND HISTORY
      }
      default -> {
        throw new TypeNotPresentException(ID, new Throwable());
      }
    }
  }

  private void loadCheckBox(UICheckBox checkBox, String ID) {
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
    if (ID.equals("Insert Command Here")) {
      textField.setupTextBox();
    } else {
      throw new TypeNotPresentException(ID, new Throwable());
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
      case "region" -> {
        return new UIRegion(ID, position[0], position[1], position[2], position[3]);
      }
      default -> throw new TypeNotPresentException(type, new Throwable());
    }
  }
}
