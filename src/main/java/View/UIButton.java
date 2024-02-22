package View;

import java.io.File;
import java.nio.file.Path;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UIButton {

  private static final Font SLOGO_BUTTON = Font.font("Verdana", FontWeight.MEDIUM, 15);
  private final String ID;
  private final ButtonBase button;

  public UIButton(String text, ButtonBase buttonType, double x, double y) {
    ID = text;
    button = buttonType;
    button.setText(text);
    button.setFont(SLOGO_BUTTON);
    button.setTextFill(Color.GREEN);
    button.setLayoutX(x);
    button.setLayoutY(y);
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

  public ButtonBase getButton() {
    return (button);
  }

  public String getID() {
    return (ID);
  }

  public void setSelectorClassic() {
    button.setShape(new Rectangle(200.0f, 100.0f));
    button.setMinSize(200, 100);
    button.setMaxSize(400, 200);
    addShadow();
  }

  public void setMenuClassic() {
    button.setMinSize(15, 8);
    button.setMaxSize(150, 80);
    button.setShape(new Ellipse(200.0f, 120.0f, 150.0f, 80.f));

    addShadow();
  }

  public void setCheckbox() {
    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        button.arm();
        System.out.println(button.isArmed());
      }
    });
  }

  public void addShadow() {
    button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        button.setEffect(new DropShadow());
      }
    });

    button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        button.setEffect(null);
      }
    });
  }

  public void addFolderOpener(Stage stage, String folderName) {
    FileChooser fc = getFileChooser(folderName);
    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        File chosenFile = fc.showOpenDialog(stage);
        if (chosenFile != null) {
          System.out.println("File Chosen:" + chosenFile.getPath());
        }
      }
    });
  }

  public void addClickLink() {
    button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        button.setEffect(new DropShadow());
      }
    });

    button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        button.setEffect(null);
      }
    });
  }

  public void addOpenGUI(Stage stage) {
    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        SlogoWindow window = new SlogoWindow(stage, "graphics");
      }
    });

  }
}
