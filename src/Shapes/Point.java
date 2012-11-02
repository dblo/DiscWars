package Shapes;

// Made by ollol646
public class Point {
    private int xCoord, yCoord;

    //----- Public methods --------
    public Point(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Point(Point p) {
        this.xCoord = p.xCoord;
        this.yCoord = p.yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return xCoord == point.xCoord && yCoord == point.yCoord;
    }
}
