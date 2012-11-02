package DiscWars;

import Shapes.Mine;
import Views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Made by ollol646
public class Game {
    private final Board board;
    private final PlayerInput playerInput;
    private final View view;
    private boolean gameOver, //Always inverted but more intuitive/straightforward this way
            gamePaused;
    private static int s_gameWidth, s_gameHeight, s_lifes, s_level, s_score;

    public static final int
            CIRCLE_INIT_MAXSPEED = 10,
            CIRCLE_INIT_ACCELERATION = 1,
            CIRCLE_RESPAWNPROTECTION = 1500,
            DEFAULT_GAME_WIDTH = 600,
            DEFAULT_GAME_HEIGHT = 800,
            FPS = 20,
            MAXIMUM_NPC_DIAMETER = 25,
            MINIMUM_NPC_DIAMETER = 10,
            MINE_SIZE = 4,
            NPCS_LEVEL_1 = 4,
            PLAYER_BASELIFES = 5,
            PLAYER_INIT_RADIUS = 16,
            PLAYER_RELOADTIME = 1200,
            PLAYER_RESPAWNTIME = 1700,
            START_LEVEL = 1,
            STATUSAREA_DIVIDER_HEIGHT = 5,
            STATUSFIELD_HEIGHT = 100,
            TEXT_FONT_SIZE = 30;

    private final Action npcsSpawnProtection = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.setNPCsMortal();
            npcsSpawnProtectionTimer.stop();
        }
    };

    private final Action spawnThePlayer = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.spawnPlayer();
            playerInput.updatePlayer(board.getPlayer());
            playerRespawnTimer.stop();
        }
    };

    private final Action tick = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameLogic();
            board.broadcastGameChange();
        }
    };
    private final Timer gameTimer = new Timer(1000 / FPS, tick),
            npcsSpawnProtectionTimer = new Timer(Game.CIRCLE_RESPAWNPROTECTION, npcsSpawnProtection),
            playerRespawnTimer = new Timer(Game.PLAYER_RESPAWNTIME, spawnThePlayer);

    //----- Public methods --------
    public static void main(String[] args) {
        new Game(); //No need to store a reference to object
    }

    public static int getSlifes() {
        return s_lifes;
    }

    public static int getSlevel() {
        return s_level;
    }

    public static int getSscore() {
        return s_score;
    }

    public static int getSgameHeight() {
        return s_gameHeight;
    }

    public static int getSgameWidth() {
        return s_gameWidth;
    }

    //----- Package methods -------
    Game() {
        s_gameWidth = DEFAULT_GAME_WIDTH;
        s_gameHeight = DEFAULT_GAME_HEIGHT + STATUSAREA_DIVIDER_HEIGHT;
        board = new Board();
        view = new View(board);
        playerInput = new PlayerInput(view, this);
        onNewGame();
    }

    void toggleGamePaused() {
        if (!gameOver)
            if (gamePaused) {
                view.setGameOnView();
                gamePaused = false;
                gameTimer.start();
            } else {
                view.setPausedView();
                gamePaused = true;
                gameTimer.stop();
            }
    }

    static void increaseScore(int inc) {
        s_score += s_level * inc;
    }

    //If player is not reloading, will add a new mine to the board
    void layMine() {
        if (board.getPlayer() != null) {
            Mine newMine;
            if ((newMine = board.getPlayer().layMine()) != null)
                board.addMine(newMine);
        }
    }

    void newGame() {
        view.endGameOverView();
        onNewGame();
    }

    //----- Private methods -------
    private static void increaseLevel() {
        s_level++;
    }

    private static void decreaseLifes() {
        s_lifes--;
    }

    private static void setStaticsOnNewGame() {
        s_lifes = PLAYER_BASELIFES;
        s_level = START_LEVEL;
        s_score = 0;
    }

    private void gameLogic() {
        if (board.levelComplete()) {
            board.spawnNPCs(s_level);
            increaseLevel();
            npcsSpawnProtectionTimer.start();
        }
        switch (board.movePlayer()) {
            case ALIVE:
                if (board.checkIfPlayerCollided())
                    playerDied();
                break;
            case DIED:
                playerDied();
                break;
            case DEAD:
                //Do nothing
                break;
        }
        board.moveNPCs();
        board.manageNpcCollisions();
    }

    private void onNewGame() {
        setStaticsOnNewGame();
        gameOver = false;
        gamePaused = false;
        board.setBoardNewGame();
        view.setGameOnView();
        playerInput.updatePlayer(board.getPlayer());
        npcsSpawnProtectionTimer.start();
        gameTimer.start();
    }

    private void playerDied() {
        decreaseLifes();

        if (s_lifes > 0) {
            playerRespawnTimer.start();
        } else {
            gameOver = true;
            view.setGameOverView();
        }
    }
}
