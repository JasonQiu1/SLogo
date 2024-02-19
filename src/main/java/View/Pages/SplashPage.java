package View.Pages;

import java.awt.Rectangle;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class SplashPage extends GeneralPage {
  Group root;
  public SplashPage(Stage stage) {
    super(stage);
    root = new Group();
  }

  @Override
  protected void setPage(Stage stage) {
  }

  @Override
  public Parent getPage() {
    return root;
  }
}
