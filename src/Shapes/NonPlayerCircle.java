package Shapes;

import NPCStates.*;

import java.util.Iterator;

// Made by ollol646
public class NonPlayerCircle extends Circle {
    private static final HostileState HOSTILE_STATE = new HostileState();
    private static final CowardState COWARD_STATE = new CowardState();
    private static final DormantState DORMANT_STATE = new DormantState();
    private static Player s_player;
    private MovementState moveState;

    //----- Public methods --------
    public NonPlayerCircle(Point anchor, int radius) {
        super(anchor, radius);
        startDrifting();
        moveState = DORMANT_STATE;
    }

    public static void setSplayer(Player player) {
        NonPlayerCircle.s_player = player;
    }

    public void setDormantState() {
        moveState = DORMANT_STATE;
    }

    public void setActiveMoveState() {
        if (color.equals(s_player.getColor()))
            moveState = HOSTILE_STATE;
        else
            moveState = COWARD_STATE;
    }

    public void updateLOS(Iterator<Rectangle> obstIter) {
        moveState.updateLOS(this, s_player, obstIter);
    }

    //----- Protected methods -----
    @Override
    protected void setVelocity() {
        moveState.setVelocity(this, s_player);
    }

    //----- Private methods -------
    //Set circle to drift in rand * 45 degrees angle
    private void startDrifting() {
        int rand = RNG.nextInt(6);

        if (rand % 3 == 0) //0 or 3
            setVelocityX(1);
        else if (rand % 2 == 0) //2 or 4
            setVelocityX(-1);
        //else leave at 0

        if (rand > 3)
            setVelocityY(1);
        else if (rand > 1)
            setVelocityY(-1);
        //else leave at 0
    }
}
