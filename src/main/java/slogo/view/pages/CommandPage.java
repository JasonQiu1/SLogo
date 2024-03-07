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
import slogo.model.api.Session;
import slogo.controller.listeners.HelpListener;
import slogo.view.userinterface.UIElement;

/**
 * Represents the user defined command page of the application. This page displays command names and
 * parameters in a click-able list. It extends the GeneralPage class to inherit common page
 * functionalities.
 *
 * @author Jordan Haytaian
 */
public class CommandPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final Session session;

  /**
   * Constructs a CommandPage object with the specified stage.
   *
   * @param stage The stage for the help page.
   */
  public CommandPage(Stage stage, Session session) {
    super(stage, new HelpListener(session));
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
    this.session = session;
  }

  /**
   * Sets up the command page with title and list view
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
   * Retrieves the parent node containing all elements of the command page.
   *
   * @return The root node of the command page.
   */
  @Override
  public Parent getPage() {
    return root;
  }


  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("User-Defined Commands", new double[]{screenWidth / 4 - 20, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private void setUpCommandList(double screenWidth, double screenHeight) {
    Map<String, Map<String, String>> commandMap = session.getUserDefinedCommands();
    double[] position = new double[2];
    position[0] = (100);
    position[1] = (100);

    ObservableList<String> commands = FXCollections.observableArrayList();
    for (Entry<String, Map<String, String>> entry : commandMap.entrySet()) {
      int arity = Integer.parseInt(entry.getValue().get("arity"));
      String text = "Command: " + entry.getKey() + "\nParameter(s): ";
      if (arity == 0) {
        text = text + "none";
      } else {
        for (int currParam = 0; currParam < arity; currParam++) {
          text = text + entry.getValue().get("parameter" + (currParam + 1));
          if (currParam != arity - 1) {
            text = text + ",";
          }
        }
      }
      commands.add(text);
    }
    root.getChildren().add(createListElement("User Def Commands", commands, position).getElement());

  }

}
