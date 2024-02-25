package View.Pages;

import View.UserInterface.UIElement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GraphicsPage extends GeneralPage {

  private static final Font RANDOM_FONT = Font.font("Verdana", FontWeight.BOLD, 35);
  private final Group root;

  public GraphicsPage(Stage stage) {
    super(stage);
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createGUI(screenWidth, screenHeight);
  }

  @Override
  public Parent getPage() {
    return root;
  }

  private void createGUI(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new HashSet<>();

    UIElements.addAll(setupTextField(screenWidth, screenHeight));
    UIElements.addAll(setupButtons(screenWidth, screenHeight));
    UIElements.addAll(setupText(screenWidth, screenHeight));
    UIElements.addAll(setupCheckBoxes(screenWidth, screenHeight));

    styleUI(UIElements, root);
  }

  private Collection<UIElement> setupTextField(double screenWidth, double screenHeight) {
    Map<String, double[]> textFieldIDs = new HashMap<>();
    textFieldIDs.put("Insert Command Here", new double[]{screenWidth / 8, 7 * screenHeight / 8});
    return createElements(textFieldIDs, "textfield");
  }

  private Collection<UIElement> setupCheckBoxes(double screenWidth, double screenHeight) {
    Map<String, double[]> checkboxIDs = new HashMap<>();
    checkboxIDs.put("BK/WH", new double[]{screenWidth / 8 + 30, screenHeight / 8 - 45});
    checkboxIDs.put("GN/BL", new double[]{screenWidth / 8 + 105, screenHeight / 8 - 45});
    checkboxIDs.put("PK/PR", new double[]{screenWidth / 8 + 180, screenHeight / 8 - 45});
    return createElements(checkboxIDs, "checkbox");
  }

  private Collection<UIElement> setupText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Background:", new double[]{screenWidth / 8 - 20, screenHeight / 8 - 24});
    textIDs.put("Speed:", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8 - 10});
    textIDs.put("Pen Colors:", new double[]{6 * screenWidth / 8 - 60, 4 * screenHeight / 8 - 10});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setupButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> buttonIDs = new HashMap<>();
    buttonIDs.put("1x", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8});
    buttonIDs.put("2x", new double[]{6 * screenWidth / 8 - 20, 5 * screenHeight / 8});
    buttonIDs.put("3x", new double[]{6 * screenWidth / 8 + 20, 5 * screenHeight / 8});
    buttonIDs.put("4x", new double[]{6 * screenWidth / 8 + 60, 5 * screenHeight / 8});
    buttonIDs.put("Variables", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8 + 40});
    buttonIDs.put("Play/Pause", new double[]{6 * screenWidth / 8 + 60, 5 * screenHeight / 8 + 40});
    buttonIDs.put("Commands", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8 + 80});
    buttonIDs.put("Help", new double[]{6 * screenWidth / 8 + 60, 5 * screenHeight / 8 + 80});
    buttonIDs.put("Load", new double[]{6 * screenWidth / 8 - 60, 7 * screenHeight / 8});
    buttonIDs.put("Save", new double[]{6 * screenWidth / 8 + 60, 7 * screenHeight / 8});
    buttonIDs.put("R", new double[]{6 * screenWidth / 8 - 60, 4 * screenHeight / 8});
    buttonIDs.put("G", new double[]{6 * screenWidth / 8, 4 * screenHeight / 8});
    buttonIDs.put("B", new double[]{6 * screenWidth / 8 + 60, 4 * screenHeight / 8});
    return createElements(buttonIDs, "button");
  }
}
