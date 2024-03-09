package slogo.view.builders;

import javafx.scene.Group;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIPen;
import slogo.view.userinterface.UITurtle;

public class TurtleBuilder implements UIBuilder {

  private final Group myRoot;

  public TurtleBuilder(Group root) {
    myRoot = root;
  }

  @Override
  public void build(UIElement element) {
    UITurtle turtle = (UITurtle) element;
    turtle.setupTurtle();
    turtle.setPen(new UIPen(myRoot));
  }
}
