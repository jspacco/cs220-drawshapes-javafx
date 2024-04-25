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
    Color getColor();
    void setColor(Color color);
    BoundingBox getBoundingBox();
    boolean isSelected();
    void setSelected(boolean selected);
}
