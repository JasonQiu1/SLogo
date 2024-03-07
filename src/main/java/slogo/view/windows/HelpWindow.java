package slogo.view.windows;

import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.model.api.Session;
import slogo.view.pages.CommandPage;
import slogo.view.pages.GeneralPage;
import slogo.view.pages.HelpPage;
import slogo.view.pages.HistoryPage;
import slogo.view.pages.PromptFileNamePage;
import slogo.view.pages.VariablePage;

/**
 * Represents the pop up help windows of the Slogo application. Manages the creation of different
 * types of pages within the application. Extends the Stage class.
 *
 * @author Jordan Haytaian
 */
public class HelpWindow {

  private static final int HEIGHT = 600;
  private static final int WIDTH = 600;
  private final Stage helpStage;
  private final Session session;

  /**
   * Constructs a HelpWindow with the specified type of page
   *
   * @param type the type of page to create
   */
  public HelpWindow(String type, Session session) {
    helpStage = new Stage();
    helpStage.setTitle("SLOGO Help");
    createNewWindow(type);
    this.session = session;
  }

  /**
   * Creates a new window with the specified type of page.
   *
   * @param type the type of page to create
   */
  public void createNewWindow(String type) {
    GeneralPage page = createPageType(type);
    page.setPage(WIDTH, HEIGHT);

    helpStage.setScene(new Scene(page.getPage(), WIDTH, HEIGHT));
    helpStage.show();
  }

  private GeneralPage createPageType(String pageType) {
    switch (pageType.toLowerCase()) {
      case "help" -> {
        return new HelpPage(helpStage);
      }
      case "variables" -> {
        return new VariablePage(helpStage, session);
      }
      case "commands" -> {
        return new CommandPage(helpStage, session);
      }
      case "history" -> {
        return new HistoryPage(helpStage, session);
      }
      case "promptfilenamepage" -> {
        return new PromptFileNamePage(helpStage);
      }
      default -> {
        throw new TypeNotPresentException(pageType, new Throwable("Not Found"));
      }
    }
  }

}
