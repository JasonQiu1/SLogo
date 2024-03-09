package slogo.view.pages;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import slogo.view.windows.SlogoWindow;
import util.DukeApplicationTest;

/**
 * Contains JUnit test cases to verify the behavior of the methods in the SplashPage class.
 *
 * @author Jeremyah Flowers
 */
public class SplashPageTest extends DukeApplicationTest {

  private SlogoWindow myView;

  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create application and add scene for testing to given stage
    myView = new SlogoWindow(stage, "SplashPage");
  }


  @Nested
  @DisplayName("Button Tests")
  class ButtonTests {

    @Test
    void testCreateButton() {
      try {
        // Given a create button
        Button createButton = lookup("#Create").query();

        // When clicking on the create button
        clickOn(createButton);

        // Then the next page should be displayed
      } catch (Exception e) {
        // AND an exception should not be thrown
        fail("Exception Thrown:" + e);
      }
    }

    @Test
    void testLoadButton() {
      try {
        // Given a create button
        Button loadButton = lookup("#Load").query();

        // When clicking on the create button
        clickOn(loadButton);

        // Then the next page should be displayed
      } catch (Exception e) {
        // AND an exception should not be thrown
        fail("Exception Thrown:" + e);
      }
    }

    @Test
    void testHelpButton() {
      try {
        // Given a create button
        Button helpButton = lookup("#Help").query();

        // When clicking on the create button
        clickOn(helpButton);

        // Then the next page should be displayed
      } catch (Exception e) {
        // AND an exception should not be thrown
        fail("Exception Thrown:" + e);
      }
    }

    @Test
    void testTurtleSelector() {
      try {
        // Given a turtle selector button
        Button turtleSelector = lookup("#TurtleSelector").query();

        // When clicking on the turtle selector button
        clickOn(turtleSelector);

        // Then the turtle select should be displayed
      } catch (Exception e) {
        // AND an exception should not be thrown
        fail("Exception Thrown:" + e);
      }
    }
  }

  @Nested
  @DisplayName("CheckBox Tests")
  class CheckBoxTests {

    @Test
    void testLightCheckbox() {
      // given a checkbox for "Black and White" theme
      CheckBox checkbox = lookup("Light").query();

      // when clicking on the checkbox
      clickOn(checkbox);

      // then the checkbox should be selected
      assertTrue(checkbox.isSelected());
    }

    @Test
    void testDarkCheckbox() {
      // given a checkbox for "Black and White" theme
      CheckBox checkbox = lookup("Dark").query();

      // when clicking on the checkbox
      clickOn(checkbox);

      // then the checkbox should be selected
      assertTrue(checkbox.isSelected());
    }
  }



}
