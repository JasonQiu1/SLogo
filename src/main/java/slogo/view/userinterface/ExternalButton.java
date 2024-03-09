package slogo.view.userinterface;

import java.io.File;
import java.nio.file.Path;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.view.windows.HelpWindow;
import slogo.view.windows.SlogoWindow;

/**
 * Represents a button with external functionalities (usually a link to some another area) for the
 * Slogo user interface. Extends the UIButton class. It encapsulates methods to set up various types
 * of external buttons with specific appearances and functionalities.
 *
 * @author Jeremyah Flowers
 */
public class ExternalButton extends UIButton {

  // Constants
  private static final String HOME_IMG = "/button_images/HomeButton.png";
  // Instance Variables
  private final Button myButton;

  /**
   * Constructor for ExternalButton.
   *
   * @param text The text to be displayed on the button.
   * @param x    The x-coordinate of the button's position.
   * @param y    The y-coordinate of the button's position.
   */
  public ExternalButton(String text, double x, double y) {
    super(text, x, y);
    myButton = (Button) getElement();
    setSpecialType("externalbutton");
  }

  private static FileChooser getFileChooser(String folderName) {
    FileChooser fc = new FileChooser();
    fc.setTitle("Open Resource File");
    String folder = Path.of("data" + File.separator + folderName).toString();
    fc.setInitialDirectory(new File(folder));

    if (folderName.equals("turtle_images")) {
      fc.getExtensionFilters().addAll(
          new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
      );
    } else if (folderName.equals("saved_files")) {
      fc.getExtensionFilters().addAll(
          new ExtensionFilter("SLOGO File", "*.slogo")
      );
    }

    return fc;
  }

  /**
   * Sets the button to have a classic selector appearance.
   */
  public void setSelectorClassic() {
    myButton.setShape(new Rectangle(200.0f, 100.0f));
    myButton.setMinSize(200, 100);
    myButton.setMaxSize(400, 200);
  }

  /**
   * Sets the button to have a classic menu appearance.
   */
  public void setMenuClassic() {
    myButton.setShape(new Ellipse(200.0f, 120.0f, 150.0f, 80.f));
    myButton.setMinSize(15, 8);
    myButton.setMaxSize(150, 80);
  }

  /**
   * Sets the button to have a classic home appearance.
   */
  public void setHomeClassic() {
    myButton.setShape(new Circle(15));
    createLogo(HOME_IMG, 15, 15);
  }

  /**
   * Sets the button to have a classic GUI appearance.
   */
  public void setGUIClassic() {
    myButton.setShape(new Rectangle(80.0f, 20.0f));
    myButton.setMinSize(80, 20);
    myButton.setMaxSize(160, 40);
  }

  /**
   * Adds functionality to save files to a specified folder.
   *
   * @param stage      The stage where the button is placed.
   * @param folderName The name of the folder where files will be saved.
   */
  public void addSaveFolder(Stage stage, String folderName) {
    FileChooser fc = getFileChooser(folderName);
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      String filePath = fc.showSaveDialog(stage).getPath();
      setMyPath(filePath);
      sendSignal();
    });
  }

  /**
   * Adds functionality to open files from a specified folder.
   *
   * @param stage      The stage where the button is placed.
   * @param folderName The name of the folder from which files will be opened.
   */
  public void addFolderOpener(Stage stage, String folderName) {
    //FileChooser fc = getFileChooser(folderName);
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("SLogo Files", "*.slogo"));
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      String filePath = fc.showOpenDialog(stage).getPath();
      setMyPath(filePath);
      sendSignal();
    });
  }

  /**
   * Adds functionality to open a specific page.
   *
   * @param pageType The type of page to be opened.
   */
  public void addOpenPage(String pageType) {
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, c -> new SlogoWindow(new Stage(), pageType));
  }

  public void addOpenHelpWindow(String pageType) {
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, c -> sendSignal());
  }
}
