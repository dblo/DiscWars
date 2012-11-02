package Shapes;

// Made by ollol646
public class Obstacle extends Rectangle {
    //----- Public methods --------
    public Obstacle(Point anchor, int width, int height) {
        super(anchor, width, height, ShapeColor.GREEN);
    }

    @Override
    public CollisionConsequence onCollision(Circle circle) {
        return CollisionConsequence.BOUNCE;
    }
}
