package slogo.view.windows;

import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.model.api.Session;
import slogo.view.pages.CommandPage;
import slogo.view.pages.ExpandCommandPage;
import slogo.view.pages.ExpandPage;
import slogo.view.pages.ExpandVariablePage;
import slogo.view.pages.GeneralPage;
import slogo.view.pages.HelpPage;
import slogo.view.pages.HistoryPage;
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
  private GeneralPage currPage;
  private final String expandText;

  /**
   * Constructs a HelpWindow with the specified type of page, used for pre-set pages
   *
   * @param type    the type of page to create
   * @param session the current running session
   */
  public HelpWindow(String type, Session session) {
    this.session = session;
    expandText = null;
    helpStage = new Stage();
    helpStage.setTitle("SLOGO Help");
    createNewWindow(type);
  }

  /**
   * Constructs a HelpWindow with the specified type of page, used for expand pages
   *
   * @param type       the type of page to create
   * @param session    the current running session
   * @param expandText the text to display on the expandPage
   */
  public HelpWindow(String type, Session session, String expandText) {
    this.expandText = expandText;
    this.session = session;
    helpStage = new Stage();
    helpStage.setTitle("SLOGO Help");
    createNewWindow(type);
  }

  private void createNewWindow(String type) {
    currPage = createPageType(type);
    currPage.setPage(WIDTH, HEIGHT);
    helpStage.setScene(new Scene(currPage.getPage(), WIDTH, HEIGHT));
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
      case "expand" -> {
        return new ExpandPage(helpStage, session, expandText);
      }
      case "command expand" -> {
        return new ExpandCommandPage(helpStage, session, expandText);
      }
      case "variable expand" -> {
        return new ExpandVariablePage(helpStage, session, expandText);
      }
      default -> {
        throw new TypeNotPresentException(pageType, new Throwable("Not Found"));
      }
    }
  }

}
