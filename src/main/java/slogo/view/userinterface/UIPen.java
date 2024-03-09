package slogo.view.userinterface;

import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * The UIPen class represents a pen used for drawing on a canvas.
 *
 *  @author Jeremyah Flowers
 */
public class UIPen {

  private final Group myCanvas;
  private final Stack<Line> lineCollector;
  private Color myPenColor = Color.BLACK;

  /**
   * Constructs a UIPen with the specified canvas.
   *
   * @param canvas The canvas on which the pen will draw.
   */
  public UIPen(Group canvas) {
    lineCollector = new Stack<>();
    myCanvas = canvas;
  }

  /**
   * Sets the color of the pen.
   *
   * @param color The color to set the pen to.
   */
  public void setColor(Color color) {
    myPenColor = color;
  }

  /**
   * Draws a line on the canvas.
   *
   * @param startingX The starting x-coordinate of the line.
   * @param startingY The starting y-coordinate of the line.
   * @param nextX     The ending x-coordinate of the line.
   * @param nextY     The ending y-coordinate of the line.
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

  /**
   * Clears the screen by removing all drawn lines.
   */
  public void clearScreen() {
    myCanvas.getChildren().removeAll(lineCollector);
    lineCollector.clear();
  }

  /**
   * Erases the last drawn line.
   */
  public void eraseLine() {
    myCanvas.getChildren().remove(lineCollector.pop());
  }
}
