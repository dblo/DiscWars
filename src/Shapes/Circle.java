package Shapes;

import DiscWars.Game;

import java.util.Random;

// Made by ollol646
public abstract class Circle extends Shape {
    protected int radius, diameter, maxSpeedOneDirection, acceleration;
    protected Point velocity, center;
    protected boolean mortal;   //Can only interact with hostile shapes when true
    protected static final Random RNG = new Random();

    //----- Public methods --------
    public void setIsMortal() {
        mortal = true;
    }

    public boolean isMortal() {
        return mortal;
    }

    public int getCenterX() {
        return center.getxCoord();
    }

    public int getCenterY() {
        return center.getyCoord();
    }

    public int getDiameter() {
        return diameter;
    }

    public void setRandomColor() {
        color = makeRandomColor();
    }

    public boolean movingLeft() {
        return velocity.getxCoord() < 0;
    }

    public boolean movingUp() {
        return velocity.getyCoord() < 0;
    }

    public boolean movingRight() {
        return velocity.getxCoord() > 0;
    }

    public boolean movingDown() {
        return velocity.getyCoord() > 0;
    }

    public int getRightmostX() {
        return anchor.getxCoord() + diameter;
    }

    public int getBottomY() {
        return anchor.getyCoord() + diameter;
    }

    public void reverseVelocityX() {
        velocity.setXCoord(-velocity.getxCoord());
    }

    public void reverseVelocityY() {
        velocity.setYCoord(-velocity.getyCoord());
    }

    private int getNextBottomY() {
        return getBottomY() + velocity.getyCoord();
    }

    private int getNextTopY() {
        return getTopY() + velocity.getyCoord();
    }

    private int getNextRightmostX() {
        return getRightmostX() + velocity.getxCoord();
    }

    private int getNextLeftmostX() {
        return getLeftmostX() + velocity.getxCoord();
    }

    public void setVelocityX(int newVelocityX) {
        velocity.setXCoord(newVelocityX);
    }

    public void setVelocityY(int newVelocityY) {
        velocity.setYCoord(newVelocityY);
    }

    public int getAccelleratedX() {
        return velocity.getxCoord() + acceleration;
    }

    public int getAccelleratedY() {
        return velocity.getyCoord() + acceleration;
    }

    public int getDeceleratedX() {
        return velocity.getxCoord() - acceleration;
    }

    public int getDeceleratedY() {
        return velocity.getyCoord() - acceleration;
    }

    public int getMaxSpeedOneDirection() {
        return maxSpeedOneDirection;
    }

    public void move() {
        setVelocity();
        horizontalMovement();
        verticalMovement();
        updateCenter();
    }

    //Returns true if circle will collide, otherwise false
    public boolean willCollide(Rectangle rectangle) {
        return !(getNextBottomY() < rectangle.getTopY() ||
                getNextTopY() > rectangle.getBottomY() ||
                getNextLeftmostX() > rectangle.getRightmostX() ||
                getNextRightmostX() < rectangle.getLeftmostX());
    }

    /* Returns true if colliding, otherwise false, by comparing
    the sum of circles radius:s to the distance between their centers */
    public boolean colliding(Circle circle) {
        return Math.sqrt(Math.pow((double) (getCenterX() - circle.getCenterX()), 2) +
                Math.pow((double) (getCenterY() - circle.getCenterY()), 2))
                <= radius + circle.radius;
    }

    //----- Protected methods -----
    protected abstract void setVelocity();

    protected Circle(Point anchor, int radius) {
        super(anchor);
        this.radius = radius;
        mortal = false;
        diameter = 2 * radius;
        velocity = new Point(0, 0);
        center = new Point(getLeftmostX() + radius, getTopY() + radius);
        maxSpeedOneDirection = Game.CIRCLE_INIT_MAXSPEED;
        acceleration = Game.CIRCLE_INIT_ACCELERATION;
        setColor(makeRandomColor());
    }

    //----- Private methods -------
    private ShapeColor makeRandomColor() {
        return ShapeColor.values()[RNG.nextInt(NUM_CIRCLE_SPAWN_COLORS)];
    }

    //Moves circle horizontally if allowed, otherwise bounce off of level boundaries
    private void horizontalMovement() {
        if (movingLeft()) {
            if (getNextLeftmostX() >= 0)
                anchor.setXCoord(getNextLeftmostX());
            else {
                anchor.setXCoord(0);
                reverseVelocityX();
            }
        } else {
            if (getNextRightmostX() <= Game.getSgameWidth())
                anchor.setXCoord(getNextLeftmostX());
            else {
                anchor.setXCoord(Game.getSgameWidth() - diameter);
                reverseVelocityX();
            }
        }
    }

    //Moves circle vertically if allowed, otherwise bounce off of level boundaries
    private void verticalMovement() {
        if (movingUp()) {
            if (getNextTopY() >= 0)
                anchor.setYCoord(getNextTopY());
            else {
                anchor.setYCoord(0);
                reverseVelocityY();
            }
        } else {
            if (getNextBottomY() <= Game.getSgameHeight())
                anchor.setYCoord(getNextTopY());
            else {
                anchor.setYCoord(Game.getSgameHeight() - diameter);
                reverseVelocityY();
            }
        }
    }

    private void updateCenter() {
        center.setXCoord(getLeftmostX() + radius);
        center.setYCoord(getTopY() + radius);
    }
}
