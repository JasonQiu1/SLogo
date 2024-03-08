package slogo.model.coderunner;

/**
 * The interface for any parser.
 *
 * @author Jason Qiu
 */
public interface Parser {

  /**
   * Returns the next expression
   *
   * @return the next expression if available, otherwise null
   */
  Expression parseNext();
}
