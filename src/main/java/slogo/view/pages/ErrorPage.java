package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.view.builders.PageBuilder;
import slogo.view.userinterface.UIElement;

/**
 * Page to display error messages
 *
 * @author Jordan Haytaian, Jason Qiu
 */
public class ErrorPage extends GeneralPage {

  private static final String FONT_FAMILY = "Verdana";
  private static final Font MESSAGE_FONT = Font.font(FONT_FAMILY, FontWeight.MEDIUM, 12);
  private final Group root;
  private final PageBuilder myPageBuilder;
  private final String errorMessage;

  /**
   * Constructor for ErrorPage
   *
   * @param stage        stage page is shown on
   * @param errorMessage error message to be displayed
   */
  public ErrorPage(Stage stage, String errorMessage) {
    super(stage, new HelpListener());
    root = new Group();
    myPageBuilder = new PageBuilder(stage, root);
    this.errorMessage = errorMessage;
  }

  /**
   * Sets up the error page with title and message
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUIElements(UIElements);
    Text messageText = new Text(screenWidth / 2 - 100, screenHeight / 2, errorMessage);
    messageText.setWrappingWidth(100);
    messageText.setFont(MESSAGE_FONT);
    ScrollPane container = new ScrollPane(new TextFlow(messageText));
    container.setLayoutY(100);
    container.setFitToWidth(true);
    container.setPrefHeight(600);
    root.getChildren().add(container);
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
    textIDs.put("Error", new double[]{screenWidth / 2 - 50, screenHeight / 8});
    return createElements(textIDs, "text");
  }

}
