package View.Pages;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphicsPage extends GeneralPage {

  private static final Font RANDOM_FONT = Font.font("Verdana", FontWeight.BOLD, 35);
  private final Group root;
  private boolean controlPressed = false;
  private int indexTracker = 0;

  public GraphicsPage(Stage stage) {
    super(stage);
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    Text placeHolder = new Text("INSERT GUI HERE:");
    placeHolder.setFont(RANDOM_FONT);
    placeHolder.setFill(Color.GREEN);
    placeHolder.setX((screenWidth - placeHolder.getBoundsInLocal().getWidth()) / 2);
    placeHolder.setY(screenHeight / 8);
    root.getChildren().add(placeHolder);
    createTextBox(screenWidth, screenHeight);
  }

  // TODO: Abstract this method away from GUI Class
  private void createTextBox(double screenWidth, double screenHeight) {
    TextField textBox = new TextField("Enter Commands Here");
    ArrayList<String> textCollector = new ArrayList<>();
    textBox.setAlignment(Pos.BASELINE_LEFT);
    textBox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.CONTROL) || e.getCode().equals(KeyCode.COMMAND)) {
          controlPressed = true;
        } else if (e.getCode().equals(KeyCode.R) && controlPressed) {
          System.out.println(textCollector);
          textCollector.clear();
          indexTracker = 0;
        } else if (e.getCode().equals(KeyCode.ENTER)) {
          textCollector.add(indexTracker, textBox.getText());
          indexTracker++;
          textBox.clear();
        } else if (e.getCode().equals(KeyCode.UP) && !textCollector.isEmpty() && indexTracker > 0) {
          --indexTracker;
          textBox.setText(textCollector.get(indexTracker));
        } else if (e.getCode().equals(KeyCode.DOWN)) {
          if (indexTracker < textCollector.size() - 1) {
            ++indexTracker;
            textBox.setText(textCollector.get(indexTracker));
          } else {
            textBox.setText("");
          }
        }
      }
    });

    textBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.CONTROL) || e.getCode().equals(KeyCode.COMMAND)) {
          controlPressed = false;
        }
      }
    });

    // when enter is pressed
    textBox.setLayoutX(screenWidth / 8);
    textBox.setLayoutY(7 * screenHeight / 8);
    root.getChildren().add(textBox);
  }

  @Override
  public Parent getPage() {
    return root;
  }


}
