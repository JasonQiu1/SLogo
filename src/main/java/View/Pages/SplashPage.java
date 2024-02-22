package View.Pages;

import View.UIButton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashPage extends GeneralPage {
  private static final Font SLOGO_FONT = Font.font("Verdana", FontWeight.BOLD, 35);
  private Group root;
  private Stage stage;

  public SplashPage(Stage stage) {
    super(stage);
    this.stage = stage;
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createTitle(screenWidth, screenHeight);
    createMenuButtons(screenWidth, screenHeight);
  }
  private void createMenuButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> buttonIDs = new HashMap<>();
    buttonIDs.put("Turtle Selector", new double[]{screenWidth / 2 - 100, screenHeight / 4});
    buttonIDs.put("Create", new double[]{screenWidth / 4, 3 * screenHeight / 4});
    buttonIDs.put("Load", new double[]{2 * screenWidth / 4, 3 * screenHeight / 4});
    buttonIDs.put("Help", new double[]{3 * screenWidth / 4, 3 * screenHeight / 4});

    Collection<UIButton> UIButtons = createButtons(buttonIDs, "button");
    styleUI(UIButtons);

    Map<String, double[]> boxIDs = new HashMap<>();
    boxIDs.put("Light", new double[]{screenWidth / 4 - 40, 7 * screenHeight / 8});
    boxIDs.put("Dark", new double[]{screenWidth / 4 + 40, 7 * screenHeight / 8});

    Collection<UIButton> UIBoxes = createButtons(boxIDs, "checkbox");
    styleUI(UIBoxes);

  }


  private void styleUI(Collection<UIButton> UIButtons) {
    for(UIButton button : UIButtons) {
      switch(button.getID()) {
        case "Turtle Selector" -> {
          button.setSelectorClassic();
          button.addFolderOpener(stage, "turtle_images");
        }
        case "Load" -> {
          button.setMenuClassic();
          button.addFolderOpener(stage, "saved_files");
        }

        case "Create" -> {
          button.setMenuClassic();
          // TODO: add GUI option
        }
        case "Help" -> {
          button.setMenuClassic();
          button.addClickLink();
        }
        case "Light" -> {
          button.setCheckbox();
        }
        case "Dark" -> {
          button.setCheckbox();
        }

        default -> button.setMenuClassic();
      }
      button.addShadow();
      root.getChildren().add(button.getButton());
    }
  }

  private void createTitle(double screenWidth, double screenHeight) {
    Text slogo = new Text("SLOGO");
    slogo.setFont(SLOGO_FONT);
    slogo.setFill(Color.GREEN);
    slogo.setX((screenWidth-slogo.getBoundsInLocal().getWidth())/2);
    slogo.setY(screenHeight/8);
    root.getChildren().add(slogo);
  }

  @Override
  public Parent getPage() {
    return root;
  }
}
