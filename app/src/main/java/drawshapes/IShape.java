package drawshapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface IShape
{
    void move(double dx, double dy);
    void scale(double factor);
    void draw(GraphicsContext gc);
    boolean contains(Point2D p);
    public Color getColor();
    public void setColor(Color color);
}
