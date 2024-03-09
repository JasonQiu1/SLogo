package slogo.view.builders;

import javafx.scene.Group;
import slogo.view.userinterface.UIElement;
import slogo.view.userinterface.UIPen;
import slogo.view.userinterface.UITurtle;

/**
 * Constructs turtle UI elements.
 *
 * @author Jeremyah Flowers
 */
public class TurtleBuilder implements UIBuilder {

  private final Group myRoot;

  /**
   * Constructs a TurtleBuilder with the specified root group.
   *
   * @param root The root group to which turtle elements will be added.
   */
  public TurtleBuilder(Group root) {
    myRoot = root;
  }

  /**
   * Constructs a turtle UI element.
   *
   * @param element The UI element to be constructed.
   */
  @Override
  public void build(UIElement element) {
    UITurtle turtle = (UITurtle) element;
    turtle.setupTurtle();
    turtle.setPen(new UIPen(myRoot));
  }
}
