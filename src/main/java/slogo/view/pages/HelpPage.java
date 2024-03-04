package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.model.api.XmlConfiguration;
import slogo.view.userinterface.UIElement;

public class HelpPage extends GeneralPage {

  // Root group for all UI elements
  private final Group root;

  // PageBuilder to build page
  private final PageBuilder myPageBuilder;
  private final XmlConfiguration myXmlConfig;
  private final Map<String, String> helpMap;


  /**
   * Constructs a HelpPage object with the specified stage.
   *
   * @param stage The stage for the help page.
   */
  public HelpPage(Stage stage) {
    //super(stage, new SplashListener());
    //myListener = (SplashListener) getListener();
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
    myXmlConfig = new XmlConfiguration();
    helpMap = myXmlConfig.loadHelpFile();

  }

  /**
   * Sets up the splash page with menu buttons and language selection box.
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createCommandButtons(screenWidth, screenHeight);
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

  private void createCommandButtons(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();
    UIElements.addAll(setupExternalButtons(screenWidth, screenHeight));
    UIElements.addAll(setupText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
  }

  private Collection<UIElement> setupText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Help", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setupExternalButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> extIDs = new HashMap<>();
    for (Entry<String, String> command : helpMap.entrySet()) {
      extIDs.put(command.getKey(), new double[]{screenWidth});
    }
    return createElements(extIDs, "externalbutton");
  }

}
