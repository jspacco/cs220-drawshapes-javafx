package drawshapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements IShape
{
    private double width;
    private double height;
    private double x;
    private double y;
    private Color color;

    public Rectangle(Color color, double width, double height, double x, double y)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void move(double dx, double dy)
    {
        x += dx;
        y += dy;
    }

    @Override
    public void scale(double factor)
    {
        width *= factor;
        height *= factor;
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        gc.setFill(color);
        gc.fillRect(x - width/2, y - height/2, width, height);
        //System.out.println("Drawing rectangle at (" + x + ", " + y + ") with width " + width + " and height " + height);
    }

    @Override
    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    @Override
    public boolean contains(Point2D p)
    {
        return p.getX() >= x && p.getX() <= x + width && p.getY() >= y && p.getY() <= y + height;
    }

}
