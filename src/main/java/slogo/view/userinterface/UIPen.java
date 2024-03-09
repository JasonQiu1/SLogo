package slogo.view.userinterface;

import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * The UIPen class represents a pen used for drawing on a canvas.
 *
 * @author Jeremyah Flowers
 */
public class UIPen {

  private final Stack<Line> lineCollector;
  private final Group myCanvas;
  private Color myPenColor = Color.BLACK;

  public UIPen(Group canvas) {
    lineCollector = new Stack<>();
    myCanvas = canvas;
  }


  public void setColor(Color color) {
    myPenColor = color;
  }

  /**
   * Draws a line on the canvas from the starting point to the ending point.
   *
   * @param startingX The x-coordinate of the starting point.
   * @param startingY The y-coordinate of the starting point.
   * @param nextX     The x-coordinate of the ending point.
   * @param nextY     The y-coordinate of the ending point.
   */
  public void draw(double startingX, double startingY, double nextX, double nextY) {
    Line line = new Line();
    line.setStroke(myPenColor);
    line.setStartX(startingX);
    line.setStartY(startingY);
    line.setEndX(nextX);
    line.setEndY(nextY);
    lineCollector.push(line);
    myCanvas.getChildren().add(line);
  }

  public void clearScreen() {
    myCanvas.getChildren().removeAll(lineCollector);
    lineCollector.clear();
  }

  /**
   * Erases the last drawn line from the canvas.
   */
  public void eraseLine() {
    if (!lineCollector.isEmpty()) {
      myCanvas.getChildren().remove(lineCollector.pop());
    }
  }
}
