package slogo.model.coderunner;

import java.util.List;

/**
 * A list that acts like a parser. Used to parse block expressions.
 *
 * @author Jason Qiu
 */
class ListParser implements Parser {

  private final List<Expression> expressions;
  private int cursorPos = 0;

  public ListParser(List<Expression> expressions) {
    this.expressions = expressions;
  }

  /**
   * Returns the next expression in the list
   * @return the next expression in the list if not done, otherwise null
   */
  @Override
  public Expression parseNext() {
    if (cursorPos == expressions.size()) {
      return null;
    }
    return expressions.get(cursorPos++);
  }
}
