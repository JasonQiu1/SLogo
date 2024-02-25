package View.Pages;

import View.UserInterface.UIElement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SplashPage extends GeneralPage {


  private final Group root;
  private final Stage stage;

  public SplashPage(Stage stage) {
    super(stage);
    this.stage = stage;
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createMenuButtons(screenWidth, screenHeight);
    createLanguageBox(screenWidth, screenHeight);
  }

  @Override
  public Parent getPage() {
    return root;
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
    Map<String, double[]> boxIDs = new HashMap<>();
    Map<String, double[]> textIDs = new HashMap<>();

    Collection<UIElement> UIElements = new HashSet<>();

    buttonIDs.put("Turtle Selector", new double[]{screenWidth / 2 - 95, 2 * screenHeight / 8});
    buttonIDs.put("Create", new double[]{screenWidth / 4 - 30, 5 * screenHeight / 8});
    buttonIDs.put("Load", new double[]{2 * screenWidth / 4 - 10, 5 * screenHeight / 8});
    buttonIDs.put("Help", new double[]{3 * screenWidth / 4 + 10, 5 * screenHeight / 8});

    boxIDs.put("Light", new double[]{screenWidth / 4 - 40, 7 * screenHeight / 8});
    boxIDs.put("Dark", new double[]{screenWidth / 4 + 40, 7 * screenHeight / 8});

    textIDs.put("SLOGO", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    textIDs.put("Theme:", new double[]{screenWidth / 4 - 100, 7 * screenHeight / 8 + 24});

    UIElements.addAll(createElements(buttonIDs, "button"));
    UIElements.addAll(createElements(boxIDs, "checkbox"));
    UIElements.addAll(createElements(textIDs, "text"));
    styleUI(UIElements, root);
  }

}
