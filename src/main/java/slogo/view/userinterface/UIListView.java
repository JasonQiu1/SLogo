package slogo.view.userinterface;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Represents a scrollable list in the Slogo user interface. Extends the UIElement class.
 *
 * @author Jordan Haytaian
 */
public class UIListView extends UIElement {

  private final ListView<String> myListView;

  /**
   * UIListView Constructor
   * @param text The ID of the list
   * @param options The items in the list
   * @param x The X coordinate of the position
   * @param y THe Y coordinate of the position
   */
  public UIListView(String text, ObservableList<String> options, double x, double y) {
    super(new ListView<>(options), text);
    myListView = (ListView<String>) getElement();
    setListView();
    myListView.toFront();
    styleListView();
    setPosition(x, y);
  }

  private void setListView() {
    myListView.setOnMouseClicked(event -> {
      String selectedCommand = myListView.getSelectionModel().getSelectedItem();
      sendSignal();
      //TODO: Make this display additional help info
    });
  }

  private void styleListView() {
    myListView.setPrefWidth(400);
  }

}
