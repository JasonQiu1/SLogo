package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.model.api.Session;
import slogo.view.builders.PageBuilder;
import slogo.view.userinterface.UIElement;

/**
 * Page to view additional library command info
 *
 * @author Jordan Haytaian
 */
public class ExpandHelpPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final String text;


  /**
   * Constructs a Help Expand Page object with the specified stage.
   *
   * @param stage The stage for the expand page.
   */
  public ExpandHelpPage(Stage stage, Session session, String text) {
    super(stage, new HelpListener(session));
    root = new Group();
    myPageBuilder = new PageBuilder(stage, root);
    this.text = text;
  }

  /**
   * Sets up the expand page with title and Text
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();
    UIElements.addAll(setupTitleText(screenWidth, screenHeight));
    UIElements.addAll(setUpCommandText(screenWidth, screenHeight));
    myPageBuilder.styleUIElements(UIElements);
  }

  private Collection<UIElement> setUpCommandText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put(text, new double[]{screenWidth / 2, screenHeight / 3});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Help", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    return createElements(textIDs, "text");
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
