package slogo.view.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import slogo.view.windows.SlogoWindow;
import util.DukeApplicationTest;

/**
 * Contains JUnit test cases to verify the behavior of the methods in the GraphicsPage class.
 *
 * @author Jeremyah Flowers
 */

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
    myView = new SlogoWindow(stage, "GraphicsPage");
  }


  @BeforeEach
  public void setup() {
    myTextField = lookup("#CommandLine").query();
    myTextField.clear();
  }

  @Nested
  @DisplayName("Text Field Tests")
  class TextFieldTests {

    @Test
    void testWriteSingleLine() {
      // given a text field with no text
      // when writing a single line of text "Spaghetti"
      String expected1 = "Spaghetti";

      // when
      writeInputTo(myTextField, expected1);

      // then the text field should contain "Spaghetti"
      assertEquals(expected1, myTextField.getText());

      // when pressing Command + R (to clear the text field)
      press(KeyCode.COMMAND, KeyCode.R);
      type(KeyCode.COMMAND, KeyCode.R);
      press(KeyCode.COMMAND, KeyCode.R);
      type(KeyCode.COMMAND, KeyCode.R);

      // then the text field should be empty
      String expected2 = "";
      assertEquals(expected2, myTextField.getText());
    }

    @Test
    void testWriteMultipleLines() {
      // given a text field with no text
      // when writing a single line of text "Spaghetti"
      String expected1 = "Spaghetti";

      // when
      writeInputTo(myTextField, expected1);

      // then the text field should contain "Spaghetti"
      assertEquals(expected1, myTextField.getText());

      // when pressing the DOWN arrow key (to move to the next line)
      type(KeyCode.DOWN);

      // given the text field contains "Spaghetti"
      // when writing another line of text "Meatballs"
      String expected2 = "Meatballs";

      // when
      writeInputTo(myTextField, expected2);

      // then the text field should contain "Meatballs"
      assertEquals(expected2, myTextField.getText());

      // when pressing the DOWN arrow key (to move to the next line)
      type(KeyCode.DOWN);

      // then the text field should be empty
      String expected3 = "";
      assertEquals(expected3, myTextField.getText());
    }

    @Test
    void testBasicCommands() {
      // given a text field with no text
      // when writing a single line of text "fd 50"
      myTextField.clear();
      try {
        String expected1 = "fd 50";
        // when
        writeInputTo(myTextField, expected1);

        //then
        assertEquals(expected1, myTextField.getText());
        press(KeyCode.COMMAND, KeyCode.R);
      } catch (Exception e) {
        fail();
      }

      // given a text field with no text
      // when writing a single line of text "rt 90"
      myTextField.clear();
      try {
        String expected1 = "rt 90";
        // when
        writeInputTo(myTextField, expected1);

        //then
        assertEquals(expected1, myTextField.getText());
        press(KeyCode.COMMAND, KeyCode.R);
      } catch (Exception e) {
        fail();
      }

      myTextField.clear();
      try {
        String expected1 = "bk 20";
        // when
        writeInputTo(myTextField, expected1);

        // then
        assertEquals(expected1, myTextField.getText());
        press(KeyCode.COMMAND, KeyCode.R);
      } catch (Exception e) {
        fail();
      }
    }
  }

  @Nested
  @DisplayName("CheckBox Tests")
  class CheckBoxTests {

    @Test
    void testBlackAndWhite() {
      // given a checkbox for "Black and White" theme
      CheckBox checkbox1 = lookup("BK/WH").query();
      CheckBox checkbox2 = lookup("GN/BL").query();
      CheckBox checkbox3 = lookup("PK/PR").query();

      // when clicking on the checkbox
      clickOn(checkbox1);

      // then the checkbox should be selected
      assertTrue(checkbox1.isSelected());
      assertFalse(checkbox2.isSelected());
      assertFalse(checkbox3.isSelected());
    }

    @Test
    void testGreenAndBlue() {
      // given a checkbox for "Green and Blue" theme
      CheckBox checkbox1 = lookup("BK/WH").query();
      CheckBox checkbox2 = lookup("GN/BL").query();
      CheckBox checkbox3 = lookup("PK/PR").query();

      // when clicking on the checkbox
      clickOn(checkbox2);

      // then the checkbox should be selected
      assertFalse(checkbox1.isSelected());
      assertTrue(checkbox2.isSelected());
      assertFalse(checkbox3.isSelected());
    }

    @Test
    void testPinkAndPurple() {
      // given a checkbox for "Pink and Purple" theme
      CheckBox checkbox1 = lookup("BK/WH").query();
      CheckBox checkbox2 = lookup("GN/BL").query();
      CheckBox checkbox3 = lookup("PK/PR").query();

      // when clicking on the checkbox
      clickOn(checkbox3);

      // then the checkbox should be selected
      assertFalse(checkbox1.isSelected());
      assertFalse(checkbox2.isSelected());
      assertTrue(checkbox3.isSelected());
    }
  }

  @Nested
  @DisplayName("Internal Button Tests")
  class InternalButtonTests {

    @Test
    void testSpeeds() {
      // given a speed button
      // when clicking on the speed button
      // then the animation should reflect this change
      // (for now just asserts the button still works)
      try {
        Button speedOne = lookup("#1x").query();
        clickOn(speedOne);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }

      try {
        Button speedTwo = lookup("#2x").query();
        clickOn(speedTwo);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }

      try {
        Button speedThree = lookup("#3x").query();
        clickOn(speedThree);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }

      try {
        Button speedFour = lookup("#4x").query();
        clickOn(speedFour);
      } catch (Exception e) {
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testVariables() {
      try {
        // given a variables button
        Button variablesButton = lookup("#Variables").query();
        // when clicking on the variables button
        clickOn(variablesButton);
        // then the page should display the user defined variables
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testPausePlay() {
      try {
        // given a pause/play button with the current status being play
        Button pausePlayButton = lookup("#Play/Pause").query();
        // when clicking on the pause/play button
        clickOn(pausePlayButton);
        // then the page should pause
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }


    @Test
    void testReset() {
      try {
        // given a reset button
        Button resetButton = lookup("#Reset").query();
        // when clicking on the button
        clickOn(resetButton);
        // then the page should reset the display entirely
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testCommands() {
      try {
        // given a commands button
        Button commandsButton = lookup("#Commands").query();
        // when clicking on the commands button
        clickOn(commandsButton);
        // then the page should display the user defined commands
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testHistory() {
      try {
        // given a history button
        Button historyButton = lookup("#History").query();
        // when clicking on the history button
        clickOn(historyButton);
        // then the page should display the user command history
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testRedPen() {
      try {
        // given a red pen button
        Button redButton = lookup("#R").query();
        // when clicking on the button
        clickOn(redButton);
        // then the pen color should change to red
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testBluePen() {
      try {
        // given a blue pen button
        Button blueButton = lookup("#B").query();
        // when clicking on the button
        clickOn(blueButton);
        // then the pen color should change to blue
      } catch (Exception e) {
        // (for now just asserts the button still works)
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testGreenPen() {
      try {
        // given a green pen button
        Button greenButton = lookup("#G").query();
        // when clicking on the button
        clickOn(greenButton);
        // then the pen color should change to green
      } catch (Exception e) {
        // (for now just asserts the button still works)
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
        // Given a load button
        Button loadButton = lookup("#Load").query();

        // When clicking on the load button
        clickOn(loadButton);

        // Then the load operation should be initiated
      } catch (Exception e) {
        // AND no exception should be thrown
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testSaveButton() {
      try {
        // Given a save button
        Button saveButton = lookup("#Save").query();

        // When clicking on the save button
        clickOn(saveButton);

        // Then the save operation should be initiated
      } catch (Exception e) {
        // AND no exception should be thrown
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testHomeButton() {
      try {
        // Given a home button
        Button homeButton = lookup("#Home").query();

        // When clicking on the home button
        clickOn(homeButton);

        // Then the home page should be displayed
      } catch (Exception e) {
        // AND no exception should be thrown
        fail("Unexpected Exception Thrown:" + e);
      }
    }

    @Test
    void testHelpButton() {
      try {
        // Given a help button
        Button helpButton = lookup("#Help").query();

        // When clicking on the help button
        clickOn(helpButton);

        // Then the help page should be displayed
      } catch (Exception e) {
        // AND no exception should be thrown
        fail("Unexpected Exception Thrown:" + e);
      }
    }
  }

  @Nested
  @DisplayName("Unsuccessful Tests")
  class UnsuccessfulTests {

    @Test
    void testCreateButton() {
      try {
        // Given a create button
        Button createButton = lookup("#Create").query();

        // When clicking on the create button
        clickOn(createButton);

        // Then an exception should be thrown
        fail("no Exception Thrown");
      } catch (Exception e) {
        // Success because no create button exists on GUI
      }
    }

    @Test
    void testTurtleSelector() {
      try {
        // Given a turtle selector button
        Button turtleSelector = lookup("#Turtle Selector").query();

        // When clicking on the turtle selector button
        clickOn(turtleSelector);

        // Then an exception should be thrown
        fail("no Exception Thrown");
      } catch (Exception e) {
        // Success because no turtle selector button exists on GUI
      }
    }
  }
}