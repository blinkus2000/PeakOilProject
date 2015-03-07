package com.nuggets.slaphappy.peakoilproject.game.player;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;

/**
 * com.nuggets.slaphappy.peakoilproject.game.player
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class PlayerTurn extends State<TurnAction> {
    final Player player;
    private PhaseEngine.PhaseActionItem nextAction = null;
    private final DispatchedAction dispatchedAction;
    private boolean takenActionThisTurn = false;
    public PlayerTurn(Player player) {
        this.player = player;
        dispatchedAction = new DispatchedAction();
    }
    public class DispatchedAction implements TurnAction{
        public PhaseEngine.PhaseActionItem getDispatchedAction(){
            return nextAction;
        }
        @Override
        public void cleanup() {
            nextAction = null;
        }
    }

    public PhaseEngine.PhaseActionItem getNextAction() {
        return nextAction;
    }

    public void setNextAction(PhaseEngine.PhaseActionItem nextAction) {
        this.nextAction = nextAction;
    }

    public DispatchedAction getDispatchedAction() {
        return dispatchedAction;
    }

    public boolean isTakenActionThisTurn() {
        return takenActionThisTurn;
    }

    public void setTakenActionThisTurn(boolean takenActionThisTurn) {
        this.takenActionThisTurn = takenActionThisTurn;
    }
}
