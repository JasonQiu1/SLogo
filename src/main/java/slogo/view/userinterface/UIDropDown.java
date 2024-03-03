package slogo.view.userinterface;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class UIDropDown extends UIElement {

  private final ComboBox<String> myComboBox;

  public UIDropDown(String text, ObservableList<String> options, double x, double y) {
    super(new ComboBox<>(options), text);
    myComboBox = (ComboBox<String>) getElement();
    setDropDown();
    myComboBox.setPromptText(text);
    myComboBox.toFront();
    setPosition(x, y);
  }

  public String getValue() {
    return myComboBox.getValue();
  }

  private void setDropDown() {
    myComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        sendSignal();
      }
    });
  }

}
