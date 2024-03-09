package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.model.api.Session;
import slogo.view.userinterface.UIElement;

/**
 * Page to view additional user def command info and input parameters to run command
 *
 * @author Jordan Haytaian
 */
public class ExpandCommandPage extends GeneralPage {

  private static final String FONT_FAMILY = "Verdana";
  private static final Font INFO_FONT = Font.font(FONT_FAMILY, FontWeight.MEDIUM, 20);
  private final Group root;
  private final PageBuilder myPageBuilder;
  private final String text;

  /**
   * Constructs an ExpandPage object with the specified stage.
   *
   * @param stage The stage for the expand page.
   */
  public ExpandCommandPage(Stage stage, Session session, String text) {
    super(stage, new HelpListener(session));
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
    this.text = text;
  }

  /**
   * Sets up the expand page with title and list view
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();
    UIElements.addAll(setUpTextField(screenWidth, screenHeight));
    UIElements.addAll(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
    setUpText(screenWidth, screenHeight);
  }

  private void setUpText(double screenWidth, double screenHeight) {
    Text commandInfo = new Text(screenWidth / 3, screenHeight / 2, text);
    commandInfo.setFont(INFO_FONT);
    root.getChildren().add(commandInfo);
  }

  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Set Parameters", new double[]{screenWidth / 2 - 100, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setUpTextField(double screenWidth, double screenHeight) {
    Map<String, double[]> textFieldIDs = new HashMap<>();
    String command = getCommandName();
    textFieldIDs.put(command, new double[]{screenWidth / 2 - 60, screenHeight / 8});
    return createElements(textFieldIDs, "textfield");
  }

  private String getCommandName() {
    String[] parts = text.split("\n");
    String[] commandParts = parts[0].split(":");
    return commandParts[1].trim();
  }

  /**
   * Retrieves the parent node containing all elements of the expand page.
   *
   * @return The root node of the expand page.
   */
  @Override
  public Parent getPage() {
    return root;
  }


}
