package View.UserInterface;

import View.SlogoWindow;
import java.io.File;
import java.nio.file.Path;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UIElement {

  private static final Font SLOGO_BTN = Font.font("Verdana", FontWeight.MEDIUM, 15);
  private static final Font THEME_TXT = Font.font("Verdana", FontWeight.LIGHT, 15);
  private static final Font BACKGROUND_TXT = Font.font("Verdana", FontWeight.LIGHT, 12);

  private final String myID;
  private final String myType;
  private final Node UIBase;

  public UIElement(Node nodeType, String ID) {
    UIBase = nodeType;
    myType = nodeType.getTypeSelector();
    myID = ID;
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

  public Node getElement() {
    return UIBase;
  }

  public String getID() {
    return myID;
  }

  public String getType() {
    return myType;
  }

  public void addShadow() {
    UIBase.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        UIBase.setEffect(new DropShadow());
      }
    });

    UIBase.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        UIBase.setEffect(null);
      }
    });
  }

  public void addFolderOpener(Stage stage, String folderName) {
    FileChooser fc = getFileChooser(folderName);
    UIBase.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        File chosenFile = fc.showOpenDialog(stage);
        if (chosenFile != null) {
          System.out.println("File Chosen:" + chosenFile.getPath());
        }
      }
    });
  }

  public void addOpenGUI(Stage stage) {
    UIBase.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        SlogoWindow window = new SlogoWindow(stage, "graphics");
      }
    });
  }

  protected Font getButtonFont() {
    return SLOGO_BTN;
  }

  protected Font getThemeFont() {
    return THEME_TXT;
  }

  protected Font getBackgroundFont() {
    return BACKGROUND_TXT;
  }

  protected void setPosition(double x, double y) {
    UIBase.setLayoutX(x - UIBase.getBoundsInLocal().getWidth() / 2);
    UIBase.setLayoutY(y - UIBase.getBoundsInLocal().getHeight() / 2);
  }

  public void addSaveFolder(Stage stage, String folderName) {
    FileChooser fc = getFileChooser(folderName);
    UIBase.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        File chosenFile = fc.showSaveDialog(stage);
        if (chosenFile != null) {
          System.out.println("File Chosen:" + chosenFile.getPath());
        }
      }
    });
  }
}
