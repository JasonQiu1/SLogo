package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.model.api.Session;
import slogo.view.builders.PageBuilder;
import slogo.view.userinterface.UIElement;

public class ExpandCommandPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final Session session;
  private final String text;

  /**
   * Constructs an ExpandPage object with the specified stage.
   *
   * @param stage The stage for the expand page.
   */
  public ExpandCommandPage(Stage stage, Session session, String text) {
    super(stage, new HelpListener(session));
    root = new Group();
    myPageBuilder = new PageBuilder(stage, root);
    this.session = session;
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
    Collection<UIElement> UIElements = new ArrayList<>(setupRunButton(screenWidth, screenHeight));
    myPageBuilder.styleUIElements(UIElements);
    Text expandText = new Text(screenWidth / 6, screenHeight / 3, text);
    root.getChildren().add(expandText);
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

  private Collection<UIElement> setupRunButton(double screenWidth, double screenHeight) {
    Map<String, double[]> intIDs = new HashMap<>();
    intIDs.put("Run", new double[]{screenWidth / 2 + 60, 3 * screenHeight / 4});

    return createElements(intIDs, "internalbutton");
  }

}
