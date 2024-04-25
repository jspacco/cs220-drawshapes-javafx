package drawshapes;

public class BoundingBox
{
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public BoundingBox(int minX, int minY, int maxX, int maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean intersects(BoundingBox other)
    {
        // TODO: fix this so that it works
        return minX < other.maxX && maxX > other.minX && minY < other.maxY && maxY > other.minY;
    }

    public boolean intersects(IShape shape)
    {
        return this.intersects(shape.getBoundingBox());
    }

}
