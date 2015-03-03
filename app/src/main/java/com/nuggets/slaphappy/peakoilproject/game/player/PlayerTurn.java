package com.nuggets.slaphappy.peakoilproject.game.player;

import com.nuggets.slaphappy.peakoilproject.game.PeakOilEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;

/**
 * com.nuggets.slaphappy.peakoilproject.game.player
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class PlayerTurn extends State<TurnAction> {
    final Player player;
    private PeakOilEngine.PhaseActionItem nextAction = null;
    private final DispatchedAction dispatchedAction;
    private boolean takenActionThisTurn = false;
    public PlayerTurn(Player player) {
        this.player = player;
        dispatchedAction = new DispatchedAction();
    }
    public class DispatchedAction implements TurnAction{
        public PeakOilEngine.PhaseActionItem getDispatchedAction(){
            return nextAction;
        }
        @Override
        public void cleanup() {
            nextAction = null;
        }
    }

    public PeakOilEngine.PhaseActionItem getNextAction() {
        return nextAction;
    }

    public void setNextAction(PeakOilEngine.PhaseActionItem nextAction) {
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
