package NPCStates;

import Shapes.NonPlayerCircle;
import Shapes.Player;
import Shapes.Rectangle;

import java.util.Iterator;

// Made by ollol646
public abstract class AwareState implements MovementState {
    //----- Public methods --------
    @Override
    public abstract void setVelocity(NonPlayerCircle npc, Player player);

    //If an obstacle is blocking LOS with player, sets state dormant. Otherwise sets an active state.
    @Override
    public void updateLOS(NonPlayerCircle npc, Player player, Iterator<Rectangle> obstacleIter) {
        if (player == null)
            return;

        while (obstacleIter.hasNext()) {
            if (obstacleBlocksLOS(npc, player, obstacleIter.next())) {
                npc.setDormantState();
                return;
            }
        }
        npc.setActiveMoveState();
    }

    //----- Private methods -------
    private boolean obstacleBlocksLOS(NonPlayerCircle npc, Player player, Rectangle obstacle) {
        return !((npc.getLeftmostX() < obstacle.getLeftmostX() &&
                player.getLeftmostX() < obstacle.getLeftmostX()) || //Both left of
                (npc.getRightmostX() > obstacle.getRightmostX() &&
                        player.getRightmostX() > obstacle.getRightmostX()) || //Both right of
                (npc.getBottomY() > obstacle.getBottomY() &&
                        player.getBottomY() > obstacle.getBottomY()) || //Both below
                (npc.getTopY() < obstacle.getTopY() && player.getTopY() < obstacle.getTopY())); //Both above
    }
}
