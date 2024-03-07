package slogo.view.windows;

import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.view.pages.GeneralPage;
import slogo.view.pages.GraphicsPage;
import slogo.view.pages.HelpPage;
import slogo.view.pages.SplashPage;

/**
 * Represents the main window of the Slogo application. Manages the creation of different types of
 * pages within the application. Extends the Stage class.
 *
 * @author Jeremyah Flowers
 */
public class SlogoWindow {

  private static final int HEIGHT = 600;
  private static final int WIDTH = 600;
  private final Stage mainStage;

  /**
   * Constructs a SlogoWindow with the given stage and type.
   *
   * @param stage the JavaFX stage associated with this window
   * @param type  the type of page to create initially
   */
  public SlogoWindow(Stage stage, String type) {
    mainStage = stage;
    mainStage.setTitle("SLOGO");
    createNewWindow(type);
  }

  /**
   * Creates a new window with the specified type of page.
   *
   * @param type the type of page to create
   */
  public void createNewWindow(String type) {
    GeneralPage page = createPageType(type);
    page.setPage(WIDTH, HEIGHT);

    Stage newWindow = new Stage();

    newWindow.setScene(new Scene(page.getPage(), WIDTH, HEIGHT));
    newWindow.show();
  }


  private GeneralPage createPageType(String pageType) {
    switch (pageType) {
      case "GraphicsPage" -> {
        return new GraphicsPage(mainStage);
      }
      case "SplashPage" -> {
        return new SplashPage(mainStage);
      }
      default -> {
        throw new TypeNotPresentException(pageType, new Throwable("Not Found"));
      }
    }
  }

}
