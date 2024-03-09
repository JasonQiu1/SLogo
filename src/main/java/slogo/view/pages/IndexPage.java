package slogo.view.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import slogo.controller.listeners.HelpListener;
import slogo.model.api.Session;
import slogo.view.builders.PageBuilder;
import slogo.view.userinterface.UIElement;

public class IndexPage extends GeneralPage {

  private final Group root;
  private final PageBuilder myPageBuilder;
  private final String helpFile = "data/commands/index_commands.xml";
  private final Session session;

  public IndexPage(Stage stage, Session session) {
    super(stage, new HelpListener(session));
    root = new Group();
    myPageBuilder = new PageBuilder(stage, root);
    this.session = session;
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Collection<UIElement> UIElements = new ArrayList<>(setupTitleText(screenWidth, screenHeight));
    myPageBuilder.styleUIElements(UIElements);
    setUpIndexList(screenWidth, screenHeight);
  }

  @Override
  public Parent getPage() {
    return root;
  }


  private Collection<UIElement> setupTitleText(double screenWidth, double screenHeight) {
    Map<String, double[]> textIDs = new HashMap<>();
    textIDs.put("Index List", new double[]{screenWidth / 4 + 25, screenHeight / 8});
    return createElements(textIDs, "text");
  }

  private void setUpIndexList(double screenWidth, double screenHeight) {
    double[] position = new double[]{screenWidth / 6, screenHeight / 6};
    ObservableList<String> indexList = FXCollections.observableArrayList();
    root.getChildren().add(createListElement("Index List", indexList, position).getElement());
  }
}
