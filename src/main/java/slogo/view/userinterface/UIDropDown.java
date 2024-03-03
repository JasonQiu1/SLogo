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
    myComboBox.setOnAction(event -> {
      sendSignal();
    });
  }

  /**
   * Sets text of drop down menu and options
   *
   * @param text       display text of drop down menu
   * @param newOptions display text of drop down options
   */
  public void setText(String text, List<String> newOptions) {
    myComboBox.setPromptText(text);
    ObservableList<String> options = myComboBox.getItems();
    options.clear();
    options.addAll(newOptions);
  }

}
