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
    private boolean selected;

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
        //System.out.println("scaling by factor: "+factor);
        width *= factor;
        height *= factor;
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        
        if (isSelected())
        {
            gc.setFill(color.darker().darker().darker());
            //System.out.println("brigther red color: "+color.darker());
        }
        else
        {
            gc.setFill(color);
        }
        // x, y is the center, but we need the top left corner to draw a filled rectangle
        gc.fillRect(x - width/2, y - height/2, width, height);
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

    @Override
    public BoundingBox getBoundingBox()
    {
        return new BoundingBox((int)(x - width/2), (int)(y - height/2), (int)(x + width/2), (int)(y + height/2));
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
