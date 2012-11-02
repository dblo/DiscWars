package Shapes;

import DiscWars.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Made by ollol646
public class Player extends Circle {
    private boolean moveLeft, moveRight, moveUp, moveDown, reloading;

    private final Action playerSpawnProtection = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setIsMortal();
            playerSpawnProtectionTimer.stop();
        }
    };

    private final Action arming = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            reloading = false;
            armingTimer.stop();
        }
    };
    private final Timer playerSpawnProtectionTimer =
            new Timer(Game.CIRCLE_RESPAWNPROTECTION, playerSpawnProtection),
            armingTimer = new Timer(Game.PLAYER_RELOADTIME, arming);

    //----- Public methods --------
    public Player(Point anchor, int radius) {
        super(anchor, radius);
        moveLeft = moveRight = moveUp = moveDown = reloading = false;
        playerSpawnProtectionTimer.start();
    }

    //Return new mine if player is reloading, otherwise null
    public Mine layMine() {
        if (reloading)
            return null;

        reloading = true;
        armingTimer.start();
        return new Mine(new Point(center.getxCoord() - Game.MINE_SIZE / 2,
                center.getyCoord() - Game.MINE_SIZE / 2), Game.MINE_SIZE, Game.MINE_SIZE,
                color);
    }

    public void setImmortal() {
        mortal = false;
        playerSpawnProtectionTimer.start();
    }

    public void moveRight() {
        moveRight = true;
    }

    public void moveLeft() {
        moveLeft = true;
    }

    public void moveUp() {
        moveUp = true;
    }

    public void moveDown() {
        moveDown = true;
    }

    public void stopMoveRight() {
        moveRight = false;
    }

    public void stopMoveLeft() {
        moveLeft = false;
    }

    public void stopMoveUp() {
        moveUp = false;
    }

    public void stopMoveDown() {
        moveDown = false;
    }

    //Set to a color unavailable to npc:s
    public void setSafeColor() {
        setColor(ShapeColor.values()[ShapeColor.values().length - 1]);
    }

    //----- Protected methods -----
    @Override
    protected void setVelocity() {
        if (moveRight) {
            if (getAccelleratedX() <= maxSpeedOneDirection)
                setVelocityX(getAccelleratedX());
            else
                setVelocityX(maxSpeedOneDirection);
        }
        if (moveLeft) {
            if (getDeceleratedX() >= -maxSpeedOneDirection)
                setVelocityX(getDeceleratedX());
            else
                setVelocityX(-maxSpeedOneDirection);
        }
        if (moveUp) {
            if (getDeceleratedY() >= -maxSpeedOneDirection)
                setVelocityY(getDeceleratedY());
            else
                setVelocityY(-maxSpeedOneDirection);
        }
        if (moveDown)
            if (getAccelleratedY() <= maxSpeedOneDirection)
                setVelocityY(getAccelleratedY());
            else
                setVelocityY(maxSpeedOneDirection);
    }
}
