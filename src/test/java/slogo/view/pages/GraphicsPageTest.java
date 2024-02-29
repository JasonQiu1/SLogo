package slogo.view.pages;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import slogo.view.SlogoWindow;
import util.DukeApplicationTest;

class GraphicsPageTest extends DukeApplicationTest {

  // keep only if needed to call application methods in tests
  private SlogoWindow myView;
  // keep GUI components used in multiple tests
  private Labeled myLabel;
  private TextInputControl myTextField;


  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create application and add scene for testing to given stage
    myView = new SlogoWindow(stage, "graphics");
  }


  @Nested
  @DisplayName("Internal Button Tests")
  class InternalButtonTests {

    @Test
    void testRedPen() {
      try {
        Button loadButton = lookup("#R").query();
        clickOn(loadButton);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testBluePen() {
      try {
        Button loadButton = lookup("#B").query();
        clickOn(loadButton);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testGreenPen() {
      try {
        Button loadButton = lookup("#G").query();
        clickOn(loadButton);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }
  }

  @Nested
  @DisplayName("External Button Tests")
  class ExternalButtonTests {

    @Test
    void testLoadButton() {
      try {
        Button loadButton = lookup("#Load").query();
        clickOn(loadButton);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testSaveButton() {
      try {
        Button loadButton = lookup("#Save").query();
        clickOn(loadButton);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testHomeButton() {
      try {
        Button homeButton = lookup("#Home").query();
        clickOn(homeButton);
      }
      catch(Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }
  }


  @Nested
  @DisplayName("Unsuccessful Tests")
  class unsuccessfulTests {

    @Test
    void testCreateButton() {
      try {
        Button createButton = lookup("#Create").query();
        clickOn(createButton);
        fail("no Exception Thrown");
      } catch (Exception e) {
        // success because no create button exists on GUI.
      }
    }
  }
}