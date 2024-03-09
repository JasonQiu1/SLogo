package slogo.view.builders;

import slogo.view.userinterface.UIElement;

/**
 * The UIBuilder interface defines a contract for classes that build UI elements.
 * Implementing classes must provide a method to construct a UI element.
 *
 * @author Jeremyah Flowers
 */
public interface UIBuilder {

  /**
   * Constructs a UI element.
   *
   * @param element The UI element to be constructed.
   */
  void build(UIElement element);
}
