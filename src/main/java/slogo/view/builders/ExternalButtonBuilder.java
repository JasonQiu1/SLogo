package slogo.view.builders;

import javafx.stage.Stage;
import slogo.view.userinterface.ExternalButton;
import slogo.view.userinterface.UIElement;

public class ExternalButtonBuilder implements UIBuilder {

  private final Stage myStage;

  public ExternalButtonBuilder(Stage stage) {
    myStage = stage;
  }


  @Override
  public void build(UIElement element) {
    ExternalButton button = (ExternalButton) element;
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
}
