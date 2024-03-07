package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.view.userinterface.UIElement;


/**
 * Represents the prompt page of the application. This page prompts the user for a filename to save
 * a session to
 *
 * @author Jordan Haytaian
 */
public class PromptFileNamePage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;

  /**
   * Constructs a PromptFileNamePage object with the specified stage.
   *
   * @param stage The stage for the help page.
   */
  public PromptFileNamePage(Stage stage) {
    super(stage, new HelpListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage);
  }

  /**
   * Sets up the help page with title and list view
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>();
    UIElements.addAll((setupTitleText(screenWidth, screenHeight)));
    UIElements.addAll(setupTextField(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);

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
    textIDs.put("Save Session", new double[]{screenWidth / 2 - 40, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private Collection<UIElement> setupTextField(double screenWidth, double screenHeight) {
    Map<String, double[]> textFieldIDs = new HashMap<>();
    textFieldIDs.put("FileName", new double[]{screenWidth / 2, screenHeight / 2});
    return createElements(textFieldIDs, "textfield");
  }

}
