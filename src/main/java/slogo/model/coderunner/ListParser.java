package slogo.model.coderunner;

import java.util.List;

class ListParser implements Parser {

  private final List<Expression> expressions;
  private int cursorPos = 0;

  public ListParser(List<Expression> expressions) {
    this.expressions = expressions;
  }

  @Override
  public Expression parseNext() {
    if (cursorPos == expressions.size()) {
      return null;
    }
    return expressions.get(cursorPos++);
  }
}
