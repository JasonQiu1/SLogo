package slogo.view.listeners;

import java.util.Collection;
import slogo.view.userinterface.UIElement;

/**
 * UIController interface defines methods to manage UI elements and update UI themes.
 *
 * @author Jeremyah Flowers
 */
public interface UIListener {

  // Instance Variable
  void sendSignal(UIElement element);

  void passElementsToController(Collection<UIElement> elements);
}
