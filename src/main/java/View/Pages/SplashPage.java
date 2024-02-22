package View.Pages;

import View.UIButton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashPage extends GeneralPage {

  private static final Font SLOGO_FONT = Font.font("Verdana", FontWeight.BOLD, 35);
  private static final Font THEME_FONT = Font.font("Verdana", FontWeight.MEDIUM, 15);
  private final Group root;
  private final Stage stage;

  public SplashPage(Stage stage) {
    super(stage);
    this.stage = stage;
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createTitle(screenWidth, screenHeight);
    createThemeText(screenWidth, screenHeight);
    createMenuButtons(screenWidth, screenHeight);
    createLanguageBox(screenWidth, screenHeight);
  }

  private void createLanguageBox(double screenWidth, double screenHeight) {
    ObservableList<String> languageOptions =
        FXCollections.observableArrayList("English", "Spanish", "French");
    ComboBox<String> languages = new ComboBox<>(languageOptions);
    languages.setLayoutX(3 * screenWidth / 4 - 40);
    languages.setLayoutY(7 * screenHeight / 8);
    languages.setPromptText("Languages");
    root.getChildren().add(languages);
  }

  private void createMenuButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> buttonIDs = new HashMap<>();
    buttonIDs.put("Turtle Selector", new double[]{screenWidth / 2 - 100, screenHeight / 4});
    buttonIDs.put("Create", new double[]{screenWidth / 4, 3 * screenHeight / 4 - 50});
    buttonIDs.put("Load", new double[]{2 * screenWidth / 4, 3 * screenHeight / 4 - 50});
    buttonIDs.put("Help", new double[]{3 * screenWidth / 4, 3 * screenHeight / 4 - 50});

    Collection<UIButton> UIButtons = createButtons(buttonIDs, "button");
    styleUI(UIButtons);

    buttonIDs.clear();
    buttonIDs.put("Light", new double[]{screenWidth / 4 - 40, 7 * screenHeight / 8});
    buttonIDs.put("Dark", new double[]{screenWidth / 4 + 40, 7 * screenHeight / 8});

    Collection<UIButton> UIBoxes = createButtons(buttonIDs, "checkbox");
    styleUI(UIBoxes);
  }

  private void styleUI(Collection<UIButton> UIButtons) {
    for (UIButton button : UIButtons) {
      switch (button.getID()) {
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
          button.addOpenGUI(stage);
        }
        case "Help" -> {
          button.setMenuClassic();
          // TODO: MAKE BUTTON JUMP TO SLOGO WIKI
          button.addClickLink();
        }
        case "Light", "Dark" -> {
          button.setCheckbox();
        }

        default -> button.setMenuClassic();
      }
      root.getChildren().add(button.getButton());
    }
  }

  private void createTitle(double screenWidth, double screenHeight) {
    Text slogo = new Text("SLOGO");
    slogo.setFont(SLOGO_FONT);
    slogo.setFill(Color.GREEN);
    slogo.setX((screenWidth - slogo.getBoundsInLocal().getWidth()) / 2);
    slogo.setY(screenHeight / 8);
    root.getChildren().add(slogo);
  }

  private void createThemeText(double screenWidth, double screenHeight) {
    Text theme = new Text("Theme:");
    theme.setFont(THEME_FONT);
    theme.setFill(Color.GREEN);
    theme.setX((screenWidth - theme.getBoundsInLocal().getWidth()) / 4 - 100);
    theme.setY(7 * screenHeight / 8 + 16);
    root.getChildren().add(theme);
  }

  @Override
  public Parent getPage() {
    return root;
  }
}
