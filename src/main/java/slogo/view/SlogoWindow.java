package slogo.view;

import slogo.view.pages.GeneralPage;
import slogo.view.pages.GraphicsPage;
import slogo.view.pages.SplashPage;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SlogoWindow {

  private static final int HEIGHT = 600;
  private static final int WIDTH = 600;
  private final Stage stage;

  public SlogoWindow(Stage stage, String type) {
    this.stage = stage;
    stage.setTitle("SLOGO");
    createNewWindow(type);
  }

  public void createNewWindow(String type) {
    GeneralPage page = createPageType(type);
    page.setPage(WIDTH, HEIGHT);

    stage.setScene(new Scene(page.getPage(), WIDTH, HEIGHT));
    stage.show();
  }

  private GeneralPage createPageType(String pageType) {
    switch (pageType.toLowerCase()) {
      case "graphics" -> {
        return new GraphicsPage(stage);
      }
      case "splash" -> {
        return new SplashPage(stage);
      }
      default -> {
        throw new TypeNotPresentException(pageType, new Throwable("Not Found"));
      }
    }
  }
}