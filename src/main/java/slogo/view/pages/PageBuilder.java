package slogo.view.pages;

import java.util.Collection;
import javafx.scene.Group;
import javafx.stage.Stage;
import slogo.view.userinterface.ExternalButton;
import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIRegion;
import slogo.view.userinterface.UIText;
import slogo.view.userinterface.UITextField;

public class PageBuilder {

  private final Stage myStage;

  public PageBuilder(Stage stage) {
    myStage = stage;
  }

  /**
   * Styles UI elements and adds them to the root group.
   *
   * @param UIElements a collection of UI elements to style
   * @param root       the root group to add elements to
   */
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
        button.addOpenPage(myStage, "graphics");
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
}
