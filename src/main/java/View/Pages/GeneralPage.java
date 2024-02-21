package View.Pages;

import View.UIButton;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

public abstract class GeneralPage {
  Stage myStage;
  public GeneralPage(Stage stage) {
    myStage = stage;
  }

  private GeneralPage getType(String pageType) throws TypeNotPresentException {
    switch (pageType.toLowerCase()) {
      case "graphics" -> {
        return new GraphicsPage(myStage);
      }
      case "splash" -> {
        return new SplashPage(myStage);
      }
    }

    return null;
  }

  public abstract void setPage(double screenWidth, double screenHeight);
  public abstract Parent getPage();

  public Collection<UIButton> createButtons(Map<String, double[]> buttonNamesAndPositions) {
    Set<UIButton> buttons = new HashSet<>();
    for(String name : buttonNamesAndPositions.keySet()) {
      double[] positions = buttonNamesAndPositions.get(name);
      UIButton newButton = createButton(name, positions[0], positions[1]);
      buttons.add(newButton);
    }
    return buttons;
  }

  private UIButton createButton(String text, double positionX, double positionY) {
    return new UIButton(text, positionX, positionY);
  }
}
