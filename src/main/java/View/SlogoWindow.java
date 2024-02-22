package View;

import View.Pages.GeneralPage;
import View.Pages.GraphicsPage;
import View.Pages.SplashPage;
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

  public GeneralPage createPageType(String pageType) throws TypeNotPresentException {
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