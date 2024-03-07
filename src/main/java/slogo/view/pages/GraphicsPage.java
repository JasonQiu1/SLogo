package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.controller.listeners.GraphicsListener;
import slogo.view.userinterface.UIElement;

/**
 * Represents the graphics page of the application. This page displays graphical elements and user
 * interface controls for graphics-related functionality. It extends the GeneralPage class to
 * inherit common page functionalities.
 *
 * @author Jeremyah Flowers
 */
public class GraphicsPage extends GeneralPage {

  // Root group for all UI elements
  private final Group root;

  // PageBuilder to build page
  private final PageBuilder myPageBuilder;

  /**
   * Constructs a GraphicsPage object with the specified stage.
   *
   * @param stage The stage for the graphics page.
   */
  public GraphicsPage(Stage stage) {
    super(stage, new GraphicsListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
  }

  /**
   * Sets up the graphics page with graphical elements and user interface controls.
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createGUI(screenWidth, screenHeight);
  }

  /**
   * Retrieves the parent node containing all elements of the graphics page.
   *
   * @return The root node of the graphics page.
   */
  @Override
  public Parent getPage() {
    return root;
  }


  private void createGUI(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();

    UIElements.addAll(setupBackground(screenWidth, screenHeight));
    UIElements.addAll(setupBoxes(screenWidth, screenHeight));
    UIElements.addAll(setupTextField(screenWidth, screenHeight));
    UIElements.addAll(setupInternalButtons(screenWidth, screenHeight));
    UIElements.addAll(setupExternalButtons(screenWidth, screenHeight));
    UIElements.addAll(setupText(screenWidth, screenHeight));
    UIElements.addAll(setupCheckBoxes(screenWidth, screenHeight));
    UIElements.addAll(setupTurtle(screenWidth, screenHeight));

    myPageBuilder.styleUI(UIElements, root);
  }

  private Collection<UIElement> setupBackground(double screenWidth, double screenHeight) {
    Map<String, double[]> boxIDs = new HashMap<>();
    boxIDs.put("BackgroundTheme", new double[]{
        screenWidth,
        screenHeight,
        0,
        0});
    return createElements(boxIDs, "region");
  }

  private Collection<UIElement> setupBoxes(double screenWidth, double screenHeight) {
    Map<String, double[]> boxIDs = new HashMap<>();
    boxIDs.put("BottomBox", new double[]{
        screenWidth,
        7 * screenHeight / 8 - 20,
        0,
        7 * screenHeight / 8 - 20});
    boxIDs.put("RightBox", new double[]{
        3 * screenWidth / 8 + 20,
        screenHeight,
        5 * screenWidth / 8 - 20,
        0});
    boxIDs.put("TurtleBox", new double[]{
        300,
        300,
        screenWidth / 8 - 50,
        2 * screenHeight / 8 - 15});
    return createElements(boxIDs, "region");
  }

  private Collection<UIElement> setupTextField(double screenWidth, double screenHeight) {
    Map<String, double[]> textFieldIDs = new HashMap<>();
    textFieldIDs.put("CommandLine", new double[]{screenWidth / 8, 7 * screenHeight / 8});
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
    textIDs.put("Speed:", new double[]{6 * screenWidth / 8 - 40, 5 * screenHeight / 8 - 10});
    textIDs.put("Pen Colors:", new double[]{6 * screenWidth / 8 - 30, 4 * screenHeight / 8 - 10});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setupInternalButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> intIDs = new HashMap<>();
    intIDs.put(".5x", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8});
    intIDs.put("1x", new double[]{6 * screenWidth / 8 - 15, 5 * screenHeight / 8});
    intIDs.put("2x", new double[]{6 * screenWidth / 8 + 30, 5 * screenHeight / 8});
    intIDs.put("4x", new double[]{6 * screenWidth / 8 + 75, 5 * screenHeight / 8});
    intIDs.put("Play/Pause", new double[]{6 * screenWidth / 8 + 45, 5 * screenHeight / 8 + 40});
    intIDs.put("Step", new double[]{6 * screenWidth / 8 + 90, 5 * screenHeight / 8 + 40});
    intIDs.put("Reset", new double[]{6 * screenWidth / 8 + 60, screenHeight / 8 - 60});
    intIDs.put("R", new double[]{6 * screenWidth / 8 - 60, 4 * screenHeight / 8});
    intIDs.put("G", new double[]{6 * screenWidth / 8, 4 * screenHeight / 8});
    intIDs.put("B", new double[]{6 * screenWidth / 8 + 60, 4 * screenHeight / 8});

    return createElements(intIDs, "internalbutton");
  }

  private Collection<UIElement> setupExternalButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> extIDs = new HashMap<>();
    extIDs.put("Help", new double[]{6 * screenWidth / 8 + 60, 5 * screenHeight / 8 + 80});
    extIDs.put("Load", new double[]{6 * screenWidth / 8 - 60, 7 * screenHeight / 8});
    extIDs.put("Save", new double[]{6 * screenWidth / 8 + 60, 7 * screenHeight / 8});
    extIDs.put("Commands", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8 + 80});
    extIDs.put("History", new double[]{6 * screenWidth / 8 - 20, 1 * screenHeight / 8});
    extIDs.put("Variables", new double[]{6 * screenWidth / 8 - 60, 5 * screenHeight / 8 + 40});
    extIDs.put("Home", new double[]{6 * screenWidth / 8 - 60, screenHeight / 8 - 60});
    return createElements(extIDs, "externalbutton");
  }

  private Collection<UIElement> setupTurtle(double screenWidth, double screenHeight) {
    Map<String, double[]> turtleIDs = new HashMap<>();
    turtleIDs.put("Turtle", new double[]{screenWidth / 8 + 100, 4 * screenHeight / 8});
    return createElements(turtleIDs, "turtle");
  }
}
