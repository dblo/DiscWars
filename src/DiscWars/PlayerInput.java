package DiscWars;

import Shapes.Player;
import Views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Made by ollol646
class PlayerInput {
    private Player player;
    private final Game game;

    //----- Package methods -------
    PlayerInput(View view, Game game) {
        this.game = game;
        initKeyBindings(view.getGameComponent());
    }

    //Local player hides objects player but the "this." command should prevent ambiguity
    void updatePlayer(Player player) {
        this.player = player;
    }

    //----- Private methods -------
    private void initKeyBindings(JComponent gameOn) {
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        gameOn.getActionMap().put("right", pressedRight);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        gameOn.getActionMap().put("left", pressedLeft);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
        gameOn.getActionMap().put("down", pressedDown);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        gameOn.getActionMap().put("up", pressedUp);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("P"), "pause");

        gameOn.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "reRight");
        gameOn.getActionMap().put("reRight", releasedRight);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "reLeft");
        gameOn.getActionMap().put("reLeft", releasedLeft);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "reDown");
        gameOn.getActionMap().put("reDown", releasedDown);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "reUp");
        gameOn.getActionMap().put("reUp", releasedUp);

        gameOn.getInputMap().put(KeyStroke.getKeyStroke("P"), "pause");
        gameOn.getActionMap().put("pause", togglePause);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        gameOn.getActionMap().put("space", layMine);
        gameOn.getInputMap().put(KeyStroke.getKeyStroke("Y"), "newGame");
        gameOn.getActionMap().put("newGame", newGame);
    }

    private final Action newGame = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.newGame();
        }
    };

    private final Action layMine = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.layMine();
        }
    };

    private final Action togglePause = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.toggleGamePaused();
        }
    };

    private final Action releasedDown = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.stopMoveDown();
        }
    };

    private final Action releasedLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.stopMoveLeft();
        }
    };

    private final Action releasedRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.stopMoveRight();
        }
    };

    private final Action releasedUp = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.stopMoveUp();
        }
    };

    private final Action pressedDown = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveDown();
        }
    };

    private final Action pressedLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveLeft();
        }
    };

    private final Action pressedUp = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveUp();
        }
    };

    private final Action pressedRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveRight();
        }
    };
}
