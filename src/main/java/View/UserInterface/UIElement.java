package View.UserInterface;

import javafx.scene.Node;

public class UIElement {

  private final Node UIBase;
  private final String myID;
  private String myType;

  public UIElement(Node nodeType, String ID) {
    UIBase = nodeType;
    myType = nodeType.getTypeSelector();
    myID = ID;
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

  protected void setSpecialType(String type) {
    myType = type;
  }

  protected void setPosition(double x, double y) {
    UIBase.setLayoutX(x - UIBase.getBoundsInLocal().getWidth() / 2);
    UIBase.setLayoutY(y - UIBase.getBoundsInLocal().getHeight() / 2);
  }
}
