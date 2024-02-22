package View.Pages;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GraphicsPage extends GeneralPage {

  private final Group root;

  public GraphicsPage(Stage stage) {
    super(stage);
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {

  }

  @Override
  public Parent getPage() {
    return root;
  }


}
