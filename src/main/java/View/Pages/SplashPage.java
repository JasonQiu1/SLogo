package View.Pages;

import View.UIButton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashPage extends GeneralPage {
  private static final Font SLOGO_FONT = Font.font("Verdana", FontWeight.BOLD, 35);
  private Group root;

  public SplashPage(Stage stage) {
    super(stage);
    root = new Group();
  }

  @Override
  public void setPage(double screenWidth, double screenHeight) {
    createTitle(screenWidth, screenHeight);
    createMenuButtons(screenWidth, screenHeight);
  }
  private void createMenuButtons(double screenWidth, double screenHeight) {
    Map<String, double[]> buttonIDs = new HashMap<>();
    buttonIDs.put("Turtle Selector", new double[]{screenWidth/2, screenHeight - screenHeight/2});
    buttonIDs.put("Create", new double[]{screenWidth/4, screenHeight - screenHeight/4});
    buttonIDs.put("Load", new double[]{2*screenWidth/4, screenHeight - screenHeight/4});
    buttonIDs.put("Help", new double[]{3*screenWidth/4, screenHeight - screenHeight/4});


    Collection<UIButton> UIButtons = createButtons(buttonIDs);
    styleButtons(UIButtons);
  }

  private void styleButtons(Collection<UIButton> UIButtons) {
    for(UIButton button : UIButtons) {
      switch(button.getID()) {
        case "Turtle Selector" -> button.setSelectorClassic();
        default -> button.setMenuClassic();
      }
      button.addShadow();
      root.getChildren().add(button.getButton());
    }
  }

  private void createTitle(double screenWidth, double screenHeight) {
    Text slogo = new Text("SLOGO");
    slogo.setFont(SLOGO_FONT);
    slogo.setFill(Color.GREEN);
    slogo.setX((screenWidth-  slogo.getBoundsInLocal().getWidth())/2);
    slogo.setY(screenHeight/8);
    root.getChildren().add(slogo);
  }

  @Override
  public Parent getPage() {
    return root;
  }
}
