import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;
import slogo.view.userinterface.ExternalButton;
import util.DukeApplicationTest;

import javafx.scene.Group;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import slogo.view.userinterface.ExternalButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExternalButtonTest extends DukeApplicationTest {

  private ExternalButton externalButton;
  private Stage stage;

  @BeforeEach
  public void setUp() {
    externalButton = new ExternalButton("Test Button", 100, 100);
  }

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    Group root = new Group();
    root.getChildren().add(externalButton.getElement());
    Scene scene = new Scene(root, 600, 400);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testSetSelectorClassic() {
    externalButton.setSelectorClassic();
    assertNotNull(externalButton.getElement());
    assertEquals(Button.class, externalButton.getElement().getClass());
  }

  @Test
  public void testSetMenuClassic() {
    externalButton.setMenuClassic();
    assertNotNull(externalButton.getElement());
    assertEquals(Button.class, externalButton.getElement().getClass());
  }

  @Test
  public void testSetHomeClassic() {
    externalButton.setHomeClassic();
    assertNotNull(externalButton.getElement());
    assertEquals(Button.class, externalButton.getElement().getClass());
  }

  @Test
  public void testSetGUIClassic() {
    externalButton.setGUIClassic();
    assertNotNull(externalButton.getElement());
    assertEquals(Button.class, externalButton.getElement().getClass());
  }

  @Test
  public void testAddSaveFolder() {
    externalButton.addSaveFolder(stage, "");
    clickOn(externalButton.getElement());
    // Verify that the save folder dialog is displayed
    assertTrue(stage.getScene().getWindow().isShowing());
  }

  @Test
  public void testAddFolderOpener() {
    externalButton.addFolderOpener(stage, "");
    clickOn(externalButton.getElement());
    // Verify that the folder opener dialog is displayed
    assertTrue(stage.getScene().getWindow().isShowing());
  }

  @Test
  public void testAddOpenPage() {
    externalButton.addOpenPage( "graphics");
    clickOn(externalButton.getElement());
    // Verify that a new page is opened
    assertNotNull(stage.getScene().getRoot().lookup(".graphics-page")); // Assuming ".graphics-page" is a CSS class or ID used to identify the graphics page
  }
}
