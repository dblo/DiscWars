package NPCStates;

import Shapes.Circle;
import Shapes.NonPlayerCircle;
import Shapes.Player;

// Made by ollol646
public abstract class ChaseOrFleeState extends AwareState {
    //----- Public methods --------
    @Override
    public abstract void setVelocity(NonPlayerCircle npc, Player player);

    //----- Protected methods -----
    protected final void setVelocity(NonPlayerCircle npc, Circle evader, Circle chaser) {
        if (evader.getCenterX() < chaser.getCenterX())
            proceedLeftwards(npc);
        else
            proceedRightwards(npc);

        if (evader.getCenterY() < chaser.getCenterY())
            proceedUpwards(npc);
        else
            proceedDownwards(npc);
    }

    //----- Private methods -------
    private void proceedLeftwards(NonPlayerCircle npc) {
        if (npc.movingLeft()) {
            if (npc.getDeceleratedX() >= -npc.getMaxSpeedOneDirection())
                npc.setVelocityX(npc.getDeceleratedX());
            else
                npc.setVelocityX(-npc.getMaxSpeedOneDirection());
        } else
            npc.setVelocityX(npc.getDeceleratedX());
    }

    private void proceedRightwards(NonPlayerCircle npc) {
        if (npc.movingRight()) {
            if (npc.getAccelleratedX() <= npc.getMaxSpeedOneDirection())
                npc.setVelocityX(npc.getAccelleratedX());
            else
                npc.setVelocityX(npc.getMaxSpeedOneDirection());
        } else
            npc.setVelocityX(npc.getAccelleratedX());
    }

    private void proceedUpwards(NonPlayerCircle npc) {
        if (npc.movingUp()) {
            if (npc.getDeceleratedY() >= -npc.getMaxSpeedOneDirection())
                npc.setVelocityY(npc.getDeceleratedY());
            else
                npc.setVelocityY(-npc.getMaxSpeedOneDirection());
        } else
            npc.setVelocityY(npc.getDeceleratedY());
    }

    private void proceedDownwards(NonPlayerCircle npc) {
        if (npc.movingDown()) {
            if (npc.getAccelleratedY() <= npc.getMaxSpeedOneDirection())
                npc.setVelocityY(npc.getAccelleratedY());
            else
                npc.setVelocityY(npc.getMaxSpeedOneDirection());
        } else
            npc.setVelocityY(npc.getAccelleratedY());
    }
}
