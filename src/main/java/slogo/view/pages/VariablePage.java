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
import slogo.view.listeners.HelpListener;
import slogo.view.userinterface.UIElement;


/**
 * Represents the variable page of the application. This page displays variable names and values in
 * a click-able list. It extends the GeneralPage class to inherit common page functionalities.
 *
 * @author Jordan Haytaian
 */
public class VariablePage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final Session session;

  /**
   * Constructs a VariablePage object with the specified stage.
   *
   * @param stage The stage for the help page.
   */
  public VariablePage(Stage stage, Session session) {
    super(stage, new HelpListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
    this.session = session;
  }

  /**
   * Sets up the variable page with title and list view
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
    setUpVariableList(screenWidth, screenHeight);
  }

  /**
   * Retrieves the parent node containing all elements of the variable page.
   *
   * @return The root node of the variable page.
   */
  @Override
  public Parent getPage() {
    return root;
  }


  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("User-Defined Variables", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private void setUpVariableList(double screenWidth, double screenHeight) {
    Map<String, Double> varMap = session.getVariables();
    double[] position = new double[2];
    position[0] = (100);
    position[1] = (100);

    ObservableList<String> variables = FXCollections.observableArrayList();
    for (Entry<String, Double> entry : varMap.entrySet()) {
      String text = "Name: " + entry.getKey() + "\nValue: " + entry.getValue();
      variables.add(text);
    }
    root.getChildren().add(createListElement("variables", variables, position).getElement());

  }

}
