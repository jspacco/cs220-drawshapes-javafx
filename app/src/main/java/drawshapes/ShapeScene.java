package drawshapes;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class ShapeScene
{
    private List<IShape> shapes = new LinkedList<>();

    public void addShape(IShape shape)
    {
        shapes.add(shape);
    }

    public Collection<IShape> getShapes()
    {
        return shapes;
    }

    public Collection<IShape> contains(Point2D point)
    {
        // use stream().filter to get all shapes that contain the point
        return shapes.stream().filter(shape -> shape.contains(point)).collect(Collectors.toList());
    }

    public void clear()
    {
        shapes.clear();
    }

    public void draw(GraphicsContext gc)
    {
        for (IShape shape : shapes)
        {
            shape.draw(gc);
        }
    }

    public void selectShapes(Rectangle highlight)
    {
        BoundingBox box = highlight.getBoundingBox();
        shapes.stream().filter(shape -> shape.getBoundingBox().intersects(box)).forEach(shape -> shape.setSelected(true));
    }

    public void unselectAll() {
        shapes.forEach(shape -> shape.setSelected(false));
    }

}
