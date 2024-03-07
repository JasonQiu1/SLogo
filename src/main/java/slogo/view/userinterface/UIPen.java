package slogo.view.userinterface;

import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UIPen {

  Collection<Line> lineCollector;
  private final Group myCanvas;
  private Color myPenColor = Color.BLACK;

  public UIPen(Group canvas) {
    lineCollector = new ArrayList<>();
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

    lineCollector.add(line);
    myCanvas.getChildren().add(line);
  }

  public void clearScreen() {
    myCanvas.getChildren().removeAll(lineCollector);
    lineCollector.clear();
  }
}
