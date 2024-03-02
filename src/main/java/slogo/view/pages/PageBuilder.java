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
import slogo.view.userinterface.UITurtle;

/**
 * The PageBuilder class is responsible for styling UI elements and adding them to the root group.
 * It provides methods to style different types of UI elements based on their type and ID.
 *
 * @author Jeremyah Flowers
 */
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
        case "externalbutton" -> loadExternalButton((ExternalButton) element);
        case "internalbutton" -> loadInternalButton((InternalButton) element);
        case "text" -> loadText((UIText) element);
        case "checkbox" -> loadCheckBox((UICheckBox) element);
        case "textfield" -> loadTextField((UITextField) element);
        case "region" -> loadRegion((UIRegion) element);
        case "turtle" -> loadTurtle((UITurtle) element);
        default -> throw new TypeNotPresentException(element.getType(), new Throwable());
      }
      root.getChildren().add(element.getElement());
    }
  }

  private void loadTurtle(UITurtle turtle) {
    turtle.setupTurtle();
  }

  private void loadInternalButton(InternalButton button) {
    switch (button.getID()) {
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
        throw new TypeNotPresentException(button.getID(), new Throwable());
      }
    }
  }

  private void loadRegion(UIRegion box) {
    switch (box.getID()) {
      case "BottomBox", "RightBox" -> {
        box.setupRegion();
      }
      case "BackgroundTheme", "TurtleBox" -> {
        box.setupBackground();
      }
      default -> {
        throw new TypeNotPresentException(box.getID(), new Throwable());
      }
    }
  }

  private void loadExternalButton(ExternalButton button) {
    switch (button.getID()) {
      case "TurtleSelector" -> {
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
        button.addOpenPage(myStage, "splash");
      }
      case "Variables", "Commands", "History" -> {
        button.setGUIClassic();
        // TODO: MAKE BUTTON DISPLAY FOR VARIABLES, COMMANDS, AND HISTORY
      }
      default -> {
        throw new TypeNotPresentException(button.getID(), new Throwable());
      }
    }
  }

  private void loadCheckBox(UICheckBox checkBox) {
    switch (checkBox.getID()) {
      case "Light", "Dark" -> {
        checkBox.setThemeCheckBox();
        // TODO: MAKE BUTTON JUMP TO SLOGO WIKI
      }
      case "BK/WH", "GN/BL", "PK/PR" -> {
        checkBox.setBackgroundCheckBox();
      }
      default -> {
        throw new TypeNotPresentException(checkBox.getID(), new Throwable());
      }
    }
  }

  private void loadText(UIText text) {
    switch (text.getID()) {
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
        throw new TypeNotPresentException(text.getID(), new Throwable());
      }
    }
  }

  private void loadTextField(UITextField textField) {
    if (textField.getID().equals("CommandLine")) {
      textField.setupTextBox();
    } else {
      throw new TypeNotPresentException(textField.getID(), new Throwable());
    }
  }
}
