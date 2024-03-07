package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.view.listeners.HelpListener;
import slogo.view.userinterface.UIElement;

/**
 * Represents the help page of the application. This page displays command names and parameters in a
 * click-able list. It extends the GeneralPage class to inherit common page functionalities.
 *
 * @author Jordan Haytaian
 */
public class HelpPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final XmlConfiguration myXmlConfig;
  private final String helpFile = "data/commands/command_help_basic.xml";

  /**
   * Constructs a HelpPage object with the specified stage.
   *
   * @param stage The stage for the help page.
   */
  public HelpPage(Stage stage) {
    super(stage, new HelpListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
    myXmlConfig = new XmlConfiguration();
  }

  /**
   * Sets up the help page with title and list view
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
    setUpCommandList(screenWidth, screenHeight);
  }

  /**
   * Retrieves the parent node containing all elements of the help page.
   *
   * @return The root node of the help page.
   */
  @Override
  public Parent getPage() {
    return root;
  }


  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Help", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setupExceptionText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Error", new double[]{screenWidth / 2 - 40, screenHeight / 2});
    return createElements(textIDs, "text");
  }

  private void setUpCommandList(double screenWidth, double screenHeight) {
    try {
      Map<String, String> helpMap = myXmlConfig.loadHelpFile(helpFile);
      double[] position = new double[2];
      position[0] = (100);
      position[1] = (100);

      ObservableList<String> commands = FXCollections.observableArrayList();
      for (Entry<String, String> command : helpMap.entrySet()) {
        commands.add(command.getKey());
      }
      root.getChildren()
          .add(createListElement("library commands", commands, position).getElement());
    } catch (XmlException e) {
      //TODO: make this cleaner
      Collection<UIElement> UIElements = new ArrayList<>(
          setupExceptionText(screenWidth, screenHeight));
      myPageBuilder.styleUI(UIElements, root);
    }

  }

}
