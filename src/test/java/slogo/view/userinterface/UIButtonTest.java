package slogo.view.userinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Color;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.view.SlogoWindow;
import util.DukeApplicationTest;

class UIButtonTest extends DukeApplicationTest {

  private ExternalButton testExternalButton;
  private InternalButton testInternalButton;
  private String random;
  private Stage stage;
  private double x;
  private double y;

  @Override
  public void start(Stage stage) {
    random = "Random";
    x = 100;
    y = 100;
    testInternalButton = new InternalButton(random, x, y);
    testExternalButton = new ExternalButton(random, x, y);
    Group root = new Group();
    root.getChildren().add(testExternalButton.getElement());
    root.getChildren().add(testInternalButton.getElement());

    stage = new Stage();
    stage.setScene(new Scene(root, 400, 400));
    stage.show();
  }


  @Test
  void setSelectorClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testExternalButton.getElement();
    // When: a user calls the method setSelectorClassic on the UIButton
    testExternalButton.setSelectorClassic();

    // Then: the button should now be a rectangular selector button
    // AND this button should have the default height:100 and width:200.
    double expectedHeight = 100.0f;
    double expectedWidth = 200.0f;
    Rectangle expectedShape = new Rectangle(expectedWidth, expectedHeight);
    Rectangle actualShape = (Rectangle) actual.getShape();

    assertEquals(expectedShape.getWidth(), actualShape.getWidth());
    assertEquals(expectedShape.getHeight(), actualShape.getHeight());
  }

  @Test
  void setMenuClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testExternalButton.getElement();
    // When: a user calls the method setMenuClassic on the UIButton
    testExternalButton.setMenuClassic();

    // Then: the button should now be a ellipse-shaped menu button
    // AND this button should have the default centerX:200, centerY:120
    // AND this button should have the default radiusX: 150, radiusY: 80.
    double expectedCenterX = 200.0f;
    double expectedCenterY = 120.0f;
    double expectedRadiusX = 150.0f;
    double expectedRadiusY = 80.0f;
    Ellipse expectedShape = new Ellipse(expectedCenterX, expectedCenterY,
        expectedRadiusX, expectedRadiusY);
    Ellipse actualShape = (Ellipse) actual.getShape();

    assertEquals(expectedShape.getCenterX(), actualShape.getCenterX());
    assertEquals(expectedShape.getCenterY(), actualShape.getCenterY());
    assertEquals(expectedShape.getRadiusX(), actualShape.getRadiusX());
    assertEquals(expectedShape.getRadiusY(), actualShape.getRadiusY());
  }

  @Test
  void setSpeedClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testInternalButton.getElement();
    // When: a user calls the method setSpeedClassic on the UIButton
    testInternalButton.setSpeedClassic();

    // Then: the button should now be a rectangular selector button
    // AND this button should have the default height:100 and width:200.
    double expectedHeight = 20.0f;
    double expectedWidth = 20.0f;
    Rectangle expectedShape = new Rectangle(expectedWidth, expectedHeight);
    Rectangle actualShape = (Rectangle) actual.getShape();

    assertEquals(expectedShape.getWidth(), actualShape.getWidth());
    assertEquals(expectedShape.getHeight(), actualShape.getHeight());
  }

  @Test
  void setGUIClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testExternalButton.getElement();
    // When: a user calls the method setGUIClassic on the UIButton
    testExternalButton.setGUIClassic();

    // Then: the button should now be a rectangular selector button
    // AND this button should have the default height:100 and width:200.
    double expectedHeight = 20.0f;
    double expectedWidth = 80.0f;
    Rectangle expectedShape = new Rectangle(expectedWidth, expectedHeight);
    Rectangle actualShape = (Rectangle) actual.getShape();

    assertEquals(expectedShape.getWidth(), actualShape.getWidth());
    assertEquals(expectedShape.getHeight(), actualShape.getHeight());
  }

  @Test
  void setPenClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testInternalButton.getElement();
    // When: a user calls the method setPenClassic on the UIButton
    testInternalButton.setPenClassic();

    // Then: the button should now be a circle-shaped menu button
    // AND this button should have default radius: 20.0f.
    double expectedRadius = 20.0f;
    Circle expectedShape = new Circle(expectedRadius);
    Circle actualShape = (Circle) actual.getShape();

    assertEquals(expectedShape.getRadius(), actualShape.getRadius());
  }

  @Test
  void setHomeClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testExternalButton.getElement();
    // When: a user calls the method setHomeClassic on the UIButton
    testExternalButton.setHomeClassic();

    // Then: the button should now be a circle-shaped menu button
    // AND this button should have default radius: 15.0f.
    double expectedRadius = 15.0f;
    Circle expectedShape = new Circle(expectedRadius);
    Circle actualShape = (Circle) actual.getShape();

    assertEquals(expectedShape.getRadius(), actualShape.getRadius());
  }

  @Test
  void setResetClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testInternalButton.getElement();
    // When: a user calls the method setHomeClassic on the UIButton
    testInternalButton.setResetClassic();

    // Then: the button should now be a circle-shaped menu button
    // AND this button should have default radius: 15.0f.
    double expectedRadius = 15.0f;
    Circle expectedShape = new Circle(expectedRadius);
    Circle actualShape = (Circle) actual.getShape();

    assertEquals(expectedShape.getRadius(), actualShape.getRadius());
  }

  @Test
  void setPausePlayClassicTest() {
    // Given: a UI button object
    Button actual = (Button) testInternalButton.getElement();
    // When: a user calls the method setMenuClassic on the UIButton
    testInternalButton.setPausePlayClassic();

    // Then: the button should now be a ellipse-shaped menu button
    // AND this button should have the default radiusX: 90.0f, radiusY: 20.f.
    double expectedRadiusX = 90.0f;
    double expectedRadiusY = 20.0f;
    Ellipse expectedShape = new Ellipse(expectedRadiusX, expectedRadiusY);
    Ellipse actualShape = (Ellipse) actual.getShape();

    assertEquals(expectedShape.getRadiusX(), actualShape.getRadiusX());
    assertEquals(expectedShape.getRadiusY(), actualShape.getRadiusY());
  }

  @Test
  void setStatusTest() {
    testInternalButton.setStatus(true);
    Button button = (Button) testInternalButton.getElement();
    assertFalse(button.isDisabled());
  }

  @Test
  void folderOpenerAndGetPathTest() {
    testExternalButton.addFolderOpener(stage, "");
    Button button = lookup(testExternalButton.getID()).query();
    clickOn(button);
    clickOn("cancel");
    String actual = testExternalButton.getMyPath();
    assertEquals("", actual);
  }
}