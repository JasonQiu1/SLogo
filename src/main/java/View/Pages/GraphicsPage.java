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

    Collection<UIElement> UIElements = new HashSet<>();



    textFieldIDs.put("Insert Command Here", new double[]{screenWidth / 8, 7 * screenHeight / 8});
    UIElements.addAll(createElements(textFieldIDs, "textfield"));



    styleUI(UIElements, root);
  }



}
