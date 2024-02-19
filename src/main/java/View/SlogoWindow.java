package View;

import View.Pages.GeneralPage;
import View.Pages.GraphicsPage;
import View.Pages.SplashPage;
import java.awt.Graphics;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SlogoWindow {

  private static final int height = 600;
  private static final int width = 600;
  public Stage stage;

  public SlogoWindow(Stage stage) {
    this.stage = stage;
    createNewWindow(stage);
  }

  public void createNewWindow(Stage stage) {
    GeneralPage page = new SplashPage(stage);

    stage.setScene(new Scene(page.getPage(), width, height));
    stage.show();
  }
}