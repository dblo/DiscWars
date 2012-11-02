package Shapes;

// Made by ollol646
public abstract class Rectangle extends Shape {
    protected final int width, height;

    public enum CollisionConsequence {BOUNCE, DETONATE, NONE}

    //----- Public methods --------
    public abstract CollisionConsequence onCollision(Circle circle);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getBottomY() {
        return getTopY() + height;
    }

    @Override
    public int getRightmostX() {
        return getLeftmostX() + width;
    }

    //----- Protected methods -----
    protected Rectangle(Point anchor, int width, int height, ShapeColor color) {
        super(anchor);
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
