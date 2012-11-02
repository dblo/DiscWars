package DiscWars;

import Shapes.*;
import Views.BoardListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

// Made by ollol646
public class Board {
    private final Collection<BoardListener> listeners;
    private final LinkedList<NonPlayerCircle> npcs;
    private final LinkedList<Rectangle> stationaries;
    private final Random rng = new Random();
    private final Point playerSpawnPoint;
    private final Point safeSpawnPoint;
    private Player player;
    private final static int SPAWN_NPC_ATTEMPTS_CAP = 5;

    public enum PlayerState {ALIVE, DEAD, DIED}

    //----- Public methods --------
    public Player getPlayer() {
        return player;
    }

    public Iterator<NonPlayerCircle> getNPCsIterator() {
        if (npcs != null)
            return npcs.iterator();
        return null;
    }

    public Iterator<Rectangle> getObstaclesIterator() {
        if (stationaries != null)
            return stationaries.iterator();
        return null;
    }

    public void addListener(BoardListener listener) {
        listeners.add(listener);
    }

    //----- Package methods -------
    Board() {
        listeners = new LinkedList<BoardListener>();
        npcs = new LinkedList<NonPlayerCircle>();
        stationaries = new LinkedList<Rectangle>();
        playerSpawnPoint = new Point(Game.getSgameWidth() / 2 - Game.PLAYER_INIT_RADIUS / 2,
                Game.PLAYER_INIT_RADIUS + 10);
        safeSpawnPoint = new Point(Game.getSgameWidth() / 2, Game.getSgameHeight() / 2);
    }

    void setBoardNewGame() {
        npcs.clear();
        stationaries.clear();
        killPlayer();
        spawnPlayer();
        makeLevel();
        spawnNPCs(Game.getSlevel());
    }

    void broadcastGameChange() {
        for (BoardListener l : listeners)
            l.gameChanged();
    }

    void broadcastPlayerChange() {
        NonPlayerCircle.setSplayer(player);
    }

    void setNPCsMortal() {
        for (NonPlayerCircle npc : npcs)
            npc.setIsMortal();
    }

    //Returns state of player after attempting to move
    PlayerState movePlayer() {
        if (player == null)
            return PlayerState.DEAD;

        if (killedByMine(player))
            return PlayerState.DIED;
        player.move();
        return PlayerState.ALIVE;
    }

    void moveNPCs() {
        for(NonPlayerCircle npc : npcs)
            npc.move();
    }

    void manageNpcCollisions() {
        int index = 0;
        while (index < npcs.size()) {
            NonPlayerCircle npc = npcs.get(index);

            //if circle dies then next circle will be at same index so remain at index
            if (!killedByMine(npc)) {
                index++;
            }
        }
    }

    boolean levelComplete() {
        return npcs.isEmpty();
    }

    //Return true if player collided, otherwise false
    boolean checkIfPlayerCollided() {
        if (player == null)
            return false;

        int index = 0;
        NonPlayerCircle npc;

        while (index < npcs.size()) {
            npc = npcs.get(index);

            if (player.isMortal() && player.colliding(npc)) {
                if (player.getColor() == npc.getColor()) {
                    killPlayer();
                    return true;
                }
                onCollisionKilledNPC(npc);
                return false;
            }
            npc.updateLOS(getObstaclesIterator());
            index++;
        }
        return false;
    }

    void addMine(Mine newMine) {
        stationaries.add(newMine);
    }

    void spawnPlayer() {
        player = new Player(new Point(playerSpawnPoint), Game.PLAYER_INIT_RADIUS);
        preventAllSameColor();
        broadcastPlayerChange();
    }

    //Adds a number of npc:s depending on current level, spawns them randomly at set spawn-points
    void spawnNPCs(int level) {
        for (int i = 0; i < level + Game.NPCS_LEVEL_1; i++) {
            int spawnAttempts = 0;
            while (spawnAttempts < SPAWN_NPC_ATTEMPTS_CAP) {
                if (addNpcRandomAtLocation())
                    break;
                else
                    spawnAttempts++;
            }
            if (spawnAttempts == SPAWN_NPC_ATTEMPTS_CAP)
                addNpcAtSafeLocation();
        }
    }

    //----- Private methods -------
    private void addObstacle(Point anchor, int wid, int hei) {
        stationaries.add(new Obstacle(anchor, wid, hei));
    }

    private void killPlayer() {
        player = null;
        broadcastPlayerChange();
        setAllNPCsDormant();
    }

    private void makeLevel() {
        //Creating levels in a more dynamic manner is not within the scope of this project
        //and hence I use magic numbers here
        addObstacle(new Point(100, 100), 40, 105);
        addObstacle(new Point(300, 150), 200, 20);
        addObstacle(new Point(310, 300), 200, 20);
        addObstacle(new Point(80, 350), 60, 100);
        addObstacle(new Point(400, 450), 60, 100);
        addObstacle(new Point(420, 630), 80, 80);
        addObstacle(new Point(100, 600), 200, 40);
        addObstacle(new Point(0, Game.getSgameHeight() - Game.STATUSAREA_DIVIDER_HEIGHT),
                Game.getSgameWidth(), Game.STATUSAREA_DIVIDER_HEIGHT);
    }

    private void onCollisionKilledNPC(NonPlayerCircle npc) {
        npcs.remove(npc);
        Game.increaseScore(1);
        scrambleColors();
        updateNPCsLOS();
        player.setImmortal();
    }

    //Returns true if circle was destroyed, otherwise false
    private boolean killedByMine(Circle circle) {
        Rectangle rectangle;
        int index = 0;

        while (index < stationaries.size()) {
            rectangle = stationaries.get(index);

            if (circle.willCollide(rectangle)) {
                switch (rectangle.onCollision(circle)) {
                    case DETONATE:
                        if (circle instanceof NonPlayerCircle) {
                            npcs.remove(circle);
                            Game.increaseScore(2);
                        } else
                            killPlayer();
                        removeMine(rectangle);
                        return true;

                    case BOUNCE:
                        bounceCircleAgainstRectangle(circle, rectangle);
                        //Use next case to increase index

                    case NONE:
                        index++;
                        break;
                }
            } else index++;
        }
        return false;
    }

    private void removeMine(Rectangle rectangle) {
        stationaries.remove(rectangle);
    }

    private void bounceCircleAgainstRectangle(Circle circle, Rectangle obstacle) {
        if (circle.movingLeft()) {
            if (circle.getCenterX() >= obstacle.getRightmostX())
                circle.reverseVelocityX();
            else
                circle.reverseVelocityY();
        } else {
            if (circle.getCenterX() <= obstacle.getLeftmostX())
                circle.reverseVelocityX();
            else
                circle.reverseVelocityY();
        }
    }

    private void updateNPCsLOS() {
        for (NonPlayerCircle npc : npcs)
            npc.updateLOS(getObstaclesIterator());
    }

    private void scrambleColors() {
        if (player != null)
            player.setRandomColor();

        for (NonPlayerCircle npc : npcs)
            npc.setRandomColor();

        preventAllSameColor();
    }

    //Ensure player and all remaining npc:s does not have same color
    private void preventAllSameColor() {
        if (npcs.size() == 0)
            return;

        for (NonPlayerCircle npc : npcs) {
            if (player.getColor() != npc.getColor())
                return;
        }
        player.setSafeColor();
    }

    private void setAllNPCsDormant() {
        for (NonPlayerCircle npc : npcs)
            npc.setDormantState();
    }

    //Returns true if a npc was added, otherwise false
    private boolean addNpcRandomAtLocation() {
        NonPlayerCircle npc = makeNPCAtRandomLocation();
        for (Rectangle stationary : stationaries)
            if (npc.willCollide(stationary))
                return false;
        npcs.add(npc);
        return true;
    }

    private NonPlayerCircle makeNPCAtRandomLocation() {
        return new NonPlayerCircle(new Point(rng.nextInt(Game.getSgameWidth() - Game.MAXIMUM_NPC_DIAMETER),
                rng.nextInt(Game.getSgameHeight() - Game.MAXIMUM_NPC_DIAMETER - Game.STATUSFIELD_HEIGHT)),
                getRandomNpcRadius());
    }

    private void addNpcAtSafeLocation() {
        npcs.add(new NonPlayerCircle(safeSpawnPoint, getRandomNpcRadius()));
    }

    //Returns value in interval [MINIMUM_NPC_DIAMETER, MAXIMUM_NPC_DIAMETER]
    private int getRandomNpcRadius() {
        return rng.nextInt(Game.MAXIMUM_NPC_DIAMETER - Game.MINIMUM_NPC_DIAMETER + 1) + Game.MINIMUM_NPC_DIAMETER;
    }
}
