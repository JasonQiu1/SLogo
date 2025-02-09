package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.controller.listeners.SplashListener;
import slogo.view.userinterface.UIElement;

/**
 * Represents the splash page of the application. This page serves as the initial screen displayed
 * to the user upon launching the application. It contains menu buttons and a language selection
 * box. It encapsulates methods to set up menu buttons, language selection box, and other UI
 * elements.
 *
 * @author Jeremyah Flowers, Jordan Haytaian
 */
public class SplashPage extends GeneralPage {

  // Root group for all UI elements
  private final Group root;

  // PageBuilder to build page
  private final PageBuilder myPageBuilder;


  /**
   * Constructs a SplashPage object with the specified stage.
   *
   * @param stage The stage for the splash page.
   */
  public SplashPage(Stage stage) {
    super(stage, new SplashListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
  }

  /**
   * Sets up the splash page with menu buttons and language selection box.
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createMenuButtons(screenWidth, screenHeight);
    createLanguageDropDown(screenWidth, screenHeight);
  }

  /**
   * Retrieves the parent node containing all elements of the splash page.
   *
   * @return The root node of the splash page.
   */
  @Override
  public Parent getPage() {
    return root;
  }

  private void createMenuButtons(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();
    UIElements.addAll(setupBoxes(screenWidth, screenHeight));
    UIElements.addAll(setupExternalButtons(screenWidth, screenHeight));
    UIElements.addAll(setupCheckBoxes(screenWidth, screenHeight));
    UIElements.addAll(setupText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
  }

  private void createLanguageDropDown(double screenWidth, double screenHeight) {
    ObservableList<String> languageOptions =
        FXCollections.observableArrayList("English/Inglés/Anglais",
            "Spanish/Española/Espagnol", "French/Francés/Français");
    double[] position = new double[2];
    position[0] = (3 * screenWidth / 4 - 80);
    position[1] = (7 * screenHeight / 8);

    root.getChildren()
        .add(
            createListElement("Languages/Idiomas/Langues", languageOptions, position).getElement());
  }

  private Collection<UIElement> setupBoxes(double screenWidth, double screenHeight) {
    Map<String, double[]> boxIDs = new HashMap<>();
    boxIDs.put("BackgroundTheme", new double[]{
        screenWidth,
        screenHeight,
        0,
        0});
    return createElements(boxIDs, "region");
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
    extIDs.put("TurtleSelector", new double[]{screenWidth / 2 - 95, 2 * screenHeight / 8});
    extIDs.put("Create", new double[]{screenWidth / 4 - 30, 5 * screenHeight / 8});
    extIDs.put("Load File", new double[]{screenWidth / 3, 5 * screenHeight / 8});
    extIDs.put("Load Preferences", new double[]{screenWidth / 2, 5 * screenHeight / 8});
    extIDs.put("Help", new double[]{3 * screenWidth / 4 + 10, 5 * screenHeight / 8});
    return createElements(extIDs, "externalbutton");
  }
}