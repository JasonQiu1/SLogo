package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.view.listeners.HelpListener;
import slogo.view.userinterface.UIElement;

public class HistoryPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;

  /**
   * Constructs a HistoryPage object with the specified stage.
   *
   * @param stage The stage for the help page.
   */
  public HistoryPage(Stage stage) {
    super(stage, new HelpListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
  }

  /**
   * Sets up the history page with title and list view
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
    setUpHistoryList(screenWidth, screenHeight);
  }

  /**
   * Retrieves the parent node containing all elements of the history page.
   *
   * @return The root node of the variable page.
   */
  @Override
  public Parent getPage() {
    return root;
  }


  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Command History", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private void setUpHistoryList(double screenWidth, double screenHeight) {
    List<Map<String, Map<String, String>>> historyList = session.getCommandHistory();
    double[] position = new double[2];
    position[0] = (100);
    position[1] = (100);

    ObservableList<String> commandList = FXCollections.observableArrayList();
    for (Map<String, Map<String, String>> commands : historyList) {
      for (Entry<String, Map<String, String>> commandEntry : commands.entrySet()) {
        String text = commandEntry.getKey();
        commandList.add(text);
      }
    }
    root.getChildren().add(createListElement("History", commandList, position).getElement());

  }

}
