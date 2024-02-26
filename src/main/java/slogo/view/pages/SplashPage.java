package slogo.view.pages;

import slogo.view.userinterface.UIElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SplashPage extends GeneralPage {

  private final Group root;

  public SplashPage(Stage stage) {
    super(stage);
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

  private Collection<UIElement> setupText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("SLOGO", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    textIDs.put("Theme:", new double[]{screenWidth / 4 - 100, 7 * screenHeight / 8 + 24});
    return createElements(textIDs, "text");
  }


  private Collection<UIElement> setupCheckBoxes(double screenWidth, double screenHeight) {
    Map<String, double[]> checkboxIDs = new HashMap<>();
    checkboxIDs.put("Light", new double[]{screenWidth / 4 - 40, 7 * screenHeight / 8});
    checkboxIDs.put("Dark", new double[]{screenWidth / 4 + 40, 7 * screenHeight / 8});
    return createElements(checkboxIDs, "checkbox");
  }

  private Collection<UIElement> setupExternalButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> extIDs = new HashMap<>();
    extIDs.put("Turtle Selector", new double[]{screenWidth / 2 - 95, 2 * screenHeight / 8});
    extIDs.put("Create", new double[]{screenWidth / 4 - 30, 5 * screenHeight / 8});
    extIDs.put("Load", new double[]{2 * screenWidth / 4 - 10, 5 * screenHeight / 8});
    extIDs.put("Help", new double[]{3 * screenWidth / 4 + 10, 5 * screenHeight / 8});
    return createElements(extIDs, "externalbutton");
  }

  private void createMenuButtons(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();
    UIElements.addAll(setupExternalButtons(screenWidth, screenHeight));
    UIElements.addAll(setupCheckBoxes(screenWidth, screenHeight));
    UIElements.addAll(setupText(screenWidth, screenHeight));
    styleUI(UIElements, root);
  }

}
