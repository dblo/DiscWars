package Shapes;

import DiscWars.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Made by ollol646
public class Mine extends Rectangle {
    private final ShapeColor armedColor;

    private final Action arming = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setColor(armedColor);
            armingTimer.stop();
        }
    };
    private final Timer armingTimer = new Timer(Game.PLAYER_RELOADTIME, arming);

    //----- Public methods --------
    //Creates a gray mine that takes the color of the player when it is armed
    public Mine(Point anchor, int width, int height, ShapeColor armedColor) {
        super(anchor, width, height, ShapeColor.GRAY);
        this.armedColor = armedColor;
        armingTimer.start();
    }

    @Override
    public CollisionConsequence onCollision(Circle circle) {
        if(color == circle.getColor())
            return CollisionConsequence.DETONATE;
        return CollisionConsequence.NONE;
    }
}
