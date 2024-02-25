package View.Pages;

import View.UserInterface.UIElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    createTextBox(screenWidth, screenHeight);
  }

  @Override
  public Parent getPage() {
    return root;
  }

  private void createTextBox(double screenWidth, double screenHeight) {
    Map<String, double[]> textFieldIDs = new HashMap<>();
    Map<String, double[]> buttonIDs = new HashMap<>();
    Map<String, double[]> textIDs = new HashMap<>();

    Collection<UIElement> UIElements = new HashSet<>();

    textFieldIDs.put("Insert Command Here", new double[]{screenWidth / 8, 7 * screenHeight / 8});

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

    textIDs.put("Speed:", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8 - 10});
    textIDs.put("Pen Colors:", new double[]{6 * screenWidth / 8 - 60, 4 * screenHeight / 8 - 10});

    UIElements.addAll(createElements(textFieldIDs, "textfield"));
    UIElements.addAll(createElements(buttonIDs, "button"));
    UIElements.addAll(createElements(textIDs, "text"));

    styleUI(UIElements, root);
  }



}
