package slogo.view.userinterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UIPen {

  Stack<Line> lineCollector;
  private final Group myCanvas;
  private Color myPenColor = Color.BLACK;

  public UIPen(Group canvas) {
    lineCollector = new Stack<>();
    myCanvas = canvas;
  }


  public void setColor(Color color) {
    myPenColor = color;
  }

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

  public void eraseLine() {
    myCanvas.getChildren().remove(lineCollector.pop());
  }
}
