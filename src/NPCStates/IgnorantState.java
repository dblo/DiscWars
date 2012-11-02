package NPCStates;

import Shapes.NonPlayerCircle;
import Shapes.Player;
import Shapes.Rectangle;

import java.util.Iterator;

// Made by ollol646

//Not used due to no time to implement usage
public class IgnorantState implements MovementState {
    //----- Public methods --------
    @Override
    public void setVelocity(NonPlayerCircle npc, Player player) {
        //Is ignorant
    }

    @Override
    public void updateLOS(NonPlayerCircle npc, Player player, Iterator<Rectangle> obstacleIter) {
        //Is ignorant
    }
}
