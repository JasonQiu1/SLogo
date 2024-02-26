package slogo.view.userinterface;

import slogo.view.SlogoWindow;
import java.io.File;
import java.nio.file.Path;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ExternalButton extends UIButton {

  private static final String HOME_IMG = "/button_images/HomeButton.png";
  private final Button myButton;

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
          new ExtensionFilter("XML Files", "*.xml")
      );
    }

    return fc;
  }

  public void setSelectorClassic() {
    myButton.setShape(new Rectangle(200.0f, 100.0f));
    myButton.setMinSize(200, 100);
    myButton.setMaxSize(400, 200);
  }

  public void setMenuClassic() {
    myButton.setShape(new Ellipse(200.0f, 120.0f, 150.0f, 80.f));
    myButton.setMinSize(15, 8);
    myButton.setMaxSize(150, 80);
  }

  public void setHomeClassic() {
    myButton.setShape(new Circle(15));
    createLogo(HOME_IMG, 15, 15);
  }

  public void setGUIClassic() {
    myButton.setShape(new Rectangle(80.0f, 20.0f));
    myButton.setMinSize(80, 20);
    myButton.setMaxSize(160, 40);
  }

  public void addSaveFolder(Stage stage, String folderName) {
    FileChooser fc = getFileChooser(folderName);
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        File chosenFile = fc.showSaveDialog(stage);
        if (chosenFile != null) {
          System.out.println("File Chosen:" + chosenFile.getPath());
        }
      }
    });
  }

  public void addOpenSplash(Stage stage) {
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        SlogoWindow window = new SlogoWindow(stage, "splash");
      }
    });
  }

  public void addFolderOpener(Stage stage, String folderName) {
    FileChooser fc = getFileChooser(folderName);
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        File chosenFile = fc.showOpenDialog(stage);
        if (chosenFile != null) {
          System.out.println("File Chosen:" + chosenFile.getPath());
        }
      }
    });
  }

  public void addOpenPage(Stage stage, String pageType) {
    myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        SlogoWindow window = new SlogoWindow(stage, pageType);
      }
    });
  }
}