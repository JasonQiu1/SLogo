package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.model.api.Session;
import slogo.model.api.XmlConfiguration;
import slogo.model.api.exception.XmlException;
import slogo.view.userinterface.UIElement;
import slogo.view.windows.HelpWindow;

public class HelpExpandPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final Session session;
  private final XmlConfiguration myXmlConfig;
  private final String text;
  private static final String FONT_FAMILY = "Verdana";
  private static final Font MESSAGE_FONT = Font.font(FONT_FAMILY, FontWeight.MEDIUM, 10);
  private final String helpFile = "data/commands/command_help_basic.xml";


  /**
   * Constructs a Help Expand Page object with the specified stage.
   *
   * @param stage The stage for the expand page.
   */
  public HelpExpandPage(Stage stage, Session session, String text) {
    super(stage, new HelpListener());
    myPageBuilder = new PageBuilder(stage);
    myXmlConfig = new XmlConfiguration();
    root = new Group();
    this.text = text;
    this.session = session;
  }

  /**
   * Sets up the expand page with title and Text
   *
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   */
  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUI(UIElements, root);
    Text messageText = new Text(50, screenHeight / 3, text);
    messageText.setFont(MESSAGE_FONT);
    root.getChildren().add(messageText);
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
