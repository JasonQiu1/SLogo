package slogo.view.pages;

import java.util.Collection;
import javafx.scene.Group;
import javafx.stage.Stage;
import slogo.view.userinterface.ExternalButton;
import slogo.view.userinterface.InternalButton;
import slogo.view.userinterface.UICheckBox;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIPen;
import slogo.view.userinterface.UIRegion;
import slogo.view.userinterface.UIText;
import slogo.view.userinterface.UITextField;
import slogo.view.userinterface.UITurtle;

/**
 * The PageBuilder class is responsible for styling UI elements and adding them to the root group.
 * It provides methods to style different types of UI elements based on their type and ID. It
 * encapsulates the logic for setting up various UI elements and their functionalities.
 *
 * @author Jeremyah Flowers
 */
public class PageBuilder {

  private final Stage myStage;

  /**
   * Constructs a PageBuilder object with the specified stage.
   *
   * @param stage The stage for the page builder.
   */
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
        case "turtle" -> loadTurtle((UITurtle) element, root);
        default -> throw new TypeNotPresentException(element.getType(), new Throwable());
      }
      root.getChildren().add(element.getElement());
    }
  }

  private void loadTurtle(UITurtle turtle, Group root) {
    turtle.setupTurtle();
    turtle.setPen(new UIPen(root));
  }

  private void loadInternalButton(InternalButton button) {
    switch (button.getID()) {
      case "Play/Pause" -> {
        button.setPausePlayClassic();
      }
      case "Step" -> {
        button.setStepClassic();
        // TODO: MAKE BUTTON STEP THROUGH SIMULATION
      }
      case "Reset" -> {
        button.setResetClassic();
      }

      case ".5x", "1x", "2x", "3x", "4x" -> {
        button.setSpeedClassic();
      }
      case "R", "G", "B" -> {
        button.setPenClassic();
      }
      default -> {
        throw new TypeNotPresentException(button.getID(), new Throwable());
      }
    }
  }

  private void loadRegion(UIRegion box) {
    switch (box.getID()) {
      case "BackgroundTheme", "TurtleBox", "BottomBox", "RightBox" -> {
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
      case "Load File" -> {
        button.setMenuClassic();
        button.addOpenPage("GraphicsPage");
        button.addFolderOpener(myStage, "saved_files");
      }
      case "Load Preferences" -> {
        button.setMenuClassic();
        button.addFolderOpener(myStage, "preferences");
      }
      case "Save" -> {
        button.setMenuClassic();
        button.addSaveFolder(myStage, "saved_files");
      }
      case "Create" -> {
        button.setMenuClassic();
        button.addOpenPage("GraphicsPage");
      }
      case "Help" -> {
        button.setMenuClassic();
        button.addOpenHelpWindow("help");
      }
      case "Home" -> {
        button.setHomeClassic();
        button.addOpenPage("SplashPage");
      }
      case "Variables", "Commands", "History" -> {
        button.setGUIClassic();

        button.addOpenHelpWindow(button.getID().toLowerCase());
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
      case "SLOGO", "Help", "Command History", "User-Defined Variables", "User-Defined "
          + "Commands", "Save Session", "Set Value", "Set Parameters" -> {
        text.setSlogoClassic();
      }
      case "Theme:", "Pen Colors:", "Speed:" -> {
        text.setRegularClassic();
      }
      case "Background:" -> {
        text.setSmallerClassic();
      }
      case "Error" -> {
        text.setErrorClassic();
      }
      default -> {
        text.setSmallerClassic();
        text.setClickable();
      }
    }
  }

  private void loadTextField(UITextField textField) {
    textField.setupTextBox();
  }
}
