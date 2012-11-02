package Views;

import DiscWars.Board;
import DiscWars.Game;
import Shapes.*;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Iterator;

// Made by ollol646
class BoardComponent extends JComponent implements BoardListener {
    private final Board board;
    private final EnumMap<Shapes.Shape.ShapeColor, Color> colorMap;

    //----- Public methods --------
    @Override
    public void gameChanged() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Game.getSgameWidth(), Game.getSgameHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        paintObstacles(g);
        paintNPCs(g);
        paintPlayer(g);
    }

    //----- Package methods -------
    BoardComponent(Board board) {
        this.board = board;
        colorMap = new EnumMap<Shapes.Shape.ShapeColor, Color>(Shapes.Shape.ShapeColor.class);
        initColorMap();
    }

    //----- Private methods -------
    private void initColorMap() {
        colorMap.put(Shapes.Shape.ShapeColor.CYAN, Color.CYAN);
        colorMap.put(Shapes.Shape.ShapeColor.GREEN, Color.GREEN);
        colorMap.put(Shapes.Shape.ShapeColor.GRAY, Color.GRAY);
        colorMap.put(Shapes.Shape.ShapeColor.MAGENTA, Color.MAGENTA);
        colorMap.put(Shapes.Shape.ShapeColor.RED, Color.RED);
        colorMap.put(Shapes.Shape.ShapeColor.YELLOW, Color.YELLOW);
    }

    private void paintNPCs(Graphics g) {
        Iterator<NonPlayerCircle> iter = board.getNPCsIterator();

        if (iter != null)
            while (iter.hasNext()) {
                Circle circle = iter.next();
                g.setColor(colorMap.get(circle.getColor()));

                if (circle.isMortal())
                    g.fillOval(circle.getLeftmostX(),
                            circle.getTopY(),
                            circle.getDiameter(), circle.getDiameter());
                else
                    g.drawOval(circle.getLeftmostX(),
                            circle.getTopY(),
                            circle.getDiameter(), circle.getDiameter());
            }
    }

    private void paintObstacles(Graphics g) {
        Iterator<Shapes.Rectangle> obsIter = board.getObstaclesIterator();

        if (obsIter != null)
            while (obsIter.hasNext()) {
                Shapes.Rectangle rectangle = obsIter.next();
                g.setColor(colorMap.get(rectangle.getColor()));

                g.fill3DRect(rectangle.getLeftmostX(), rectangle.getTopY(),
                        rectangle.getWidth(), rectangle.getHeight(), true);
            }
    }

    private void paintPlayer(Graphics g) {
        Player player = board.getPlayer();

        if (player != null) {
            g.setColor(colorMap.get(player.getColor()));

            if (player.isMortal())
                g.fillOval(player.getLeftmostX(),
                        player.getTopY(),
                        player.getDiameter(), player.getDiameter());
            else
                g.drawOval(player.getLeftmostX(),
                        player.getTopY(),
                        player.getDiameter(), player.getDiameter());
        }
    }
}
