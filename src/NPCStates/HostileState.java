package NPCStates;

import Shapes.NonPlayerCircle;
import Shapes.Player;

// Made by ollol646
public class HostileState extends ChaseOrFleeState {
    //----- Public methods --------
    @Override
    public void setVelocity(NonPlayerCircle npc, Player player) {
        super.setVelocity(npc, player, npc);
    }
}
