package Shapes;

// Made by ollol646
public abstract class Shape {
    //The NUM_CIRCLE_SPAWN_COLORS first elements in enum ShapeColor
    //is available to npc as spawn-colors
    public static final int NUM_CIRCLE_SPAWN_COLORS = 3;
    protected Point anchor;
    protected ShapeColor color;

    public enum ShapeColor {CYAN, MAGENTA, YELLOW, GREEN, GRAY, RED}

    //----- Public methods --------
    abstract public int getBottomY();

    abstract public int getRightmostX();

    public final ShapeColor getColor() {
        return color;
    }

    public final int getTopY() {
        return anchor.getyCoord();
    }

    public final int getLeftmostX() {
        return anchor.getxCoord();
    }

    //----- Protected methods -----
    protected Shape(Point anchor) {
        this.anchor = anchor;
        this.color = null;
    }
    protected final void setColor(ShapeColor color) {
        this.color = color;
    }
}
