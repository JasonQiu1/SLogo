package slogo.view.builders;

import java.util.Collection;
import javafx.scene.Group;
import javafx.stage.Stage;
import slogo.view.userinterface.UIElement;

/**
 * The PageBuilder class is responsible for styling UI elements and adding them to the root group.
 * It encapsulates the logic for setting up various UI elements and their functionalities.
 *
 * @author Jeremyah Flowers
 */
public class PageBuilder {

  private final ExternalButtonBuilder myExternalButtonBuilder;
  private final InternalButtonBuilder myInternalButtonBuilder;
  private final CheckBoxBuilder myCheckBoxBuilder;
  private final RegionBuilder myRegionBuilder;
  private final TextFieldBuilder myTextFieldBuilder;
  private final TurtleBuilder myTurtleBuilder;
  private final TextBuilder myTextBuilder;
  private final Group myRoot;

  /**
   * Constructs a PageBuilder object with the specified stage and root group.
   *
   * @param stage The stage for the page builder.
   * @param root  The root group to which UI elements will be added.
   */
  public PageBuilder(Stage stage, Group root) {
    myExternalButtonBuilder = new ExternalButtonBuilder(stage);
    myInternalButtonBuilder = new InternalButtonBuilder();
    myCheckBoxBuilder = new CheckBoxBuilder();
    myRegionBuilder = new RegionBuilder();
    myTextFieldBuilder = new TextFieldBuilder();
    myTurtleBuilder = new TurtleBuilder(root);
    myTextBuilder = new TextBuilder();

    myRoot = root;
  }

  /**
   * Styles a collection of UI elements and adds them to the root group.
   *
   * @param UIElements A collection of UI elements to style and add.
   */
  public void styleUIElements(Collection<UIElement> UIElements) {
    for (UIElement element : UIElements) {
      styleUIElement(element);
    }
  }

  /**
   * Styles a single UI element and adds it to the root group.
   *
   * @param element The UI element to style and add.
   */
  public void styleUIElement(UIElement element) {
    switch (element.getType().toLowerCase()) {
      case "externalbutton" -> myExternalButtonBuilder.build(element);
      case "internalbutton" -> myInternalButtonBuilder.build(element);
      case "text" -> myTextBuilder.build(element);
      case "checkbox" -> myCheckBoxBuilder.build(element);
      case "textfield" -> myTextFieldBuilder.build(element);
      case "region" -> myRegionBuilder.build(element);
      case "turtle" -> myTurtleBuilder.build(element);
      default -> throw new TypeNotPresentException(element.getType(), new Throwable());
    }
    myRoot.getChildren().add(element.getElement());
  }
}
