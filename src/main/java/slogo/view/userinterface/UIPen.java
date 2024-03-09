package slogo.view.userinterface;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UIPen {

  private final Group myCanvas;
  private Color myPenColor = Color.BLACK;

  public UIPen(Group canvas) {
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
    myCanvas.getChildren().add(line);
  }

}
