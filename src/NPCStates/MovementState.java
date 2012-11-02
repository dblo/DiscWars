package NPCStates;

import Shapes.NonPlayerCircle;
import Shapes.Player;
import Shapes.Rectangle;

import java.util.Iterator;

// Made by ollol646
public interface MovementState {
    //----- Public methods --------
    public void setVelocity(NonPlayerCircle npc, Player player);

    //Determines if npc and player are in LOS and sets the npc:s movementState accordingly
    public void updateLOS(NonPlayerCircle npc, Player player, Iterator<Rectangle> obstacleIter);
}
