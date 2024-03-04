package slogo.view.userinterface;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UIListView extends UIElement {

  private final ListView<String> myListView;

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
    });
  }

  private void styleListView() {
    myListView.setPrefWidth(400);
  }

}
