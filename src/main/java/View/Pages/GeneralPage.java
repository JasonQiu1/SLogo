package View.Pages;

import View.UIButton;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public abstract class GeneralPage {

  Stage myStage;

  public GeneralPage(Stage stage) {
    myStage = stage;
  }

  protected Collection<UIButton> createButtons(Map<String, double[]> buttonIDs, String typeName) {
    Set<UIButton> buttons = new HashSet<>();
    for (String name : buttonIDs.keySet()) {
      double[] positions = buttonIDs.get(name);
      ButtonBase type = typeName.equals("button") ? new Button() : new CheckBox();
      UIButton newButton = createButton(name, positions[0], positions[1], type);
      buttons.add(newButton);
    }
    return buttons;
  }

  private UIButton createButton(String text, double positionX, double positionY, ButtonBase type) {
    UIButton newButton = new UIButton(text, type, positionX, positionY);
    return newButton;
  }

  public abstract void setPage(double screenWidth, double screenHeight);

  public abstract Parent getPage();


}
