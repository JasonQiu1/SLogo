package View.Pages;

import javafx.scene.Parent;
import javafx.stage.Stage;

public abstract class GeneralPage {
  Stage myStage;
  public GeneralPage(Stage stage) {
    myStage = stage;
  }
  public GeneralPage getType(String pageType) throws TypeNotPresentException {
    switch (pageType.toLowerCase()) {
      case "graphics" -> {
        return new GraphicsPage(myStage);
      }
      case "splash" -> {
        return new SplashPage(myStage);
      }
    }

    return null;
  }

  protected abstract void setPage(Stage stage);
  public abstract Parent getPage();
}
