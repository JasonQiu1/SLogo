package View;

import View.Pages.GeneralPage;
import View.Pages.GraphicsPage;
import View.Pages.SplashPage;
import java.awt.Graphics;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SlogoWindow {

  private static final int HEIGHT = 600;
  private static final int WIDTH = 600;

  public SlogoWindow(Stage stage) {
    stage.setTitle("SLOGO");
    createNewWindow(stage);
  }

  public void createNewWindow(Stage stage) {
    GeneralPage page = new SplashPage(stage);
    page.setPage(WIDTH, HEIGHT);

    stage.setScene(new Scene(page.getPage(), WIDTH, HEIGHT));
    stage.show();
  }
}