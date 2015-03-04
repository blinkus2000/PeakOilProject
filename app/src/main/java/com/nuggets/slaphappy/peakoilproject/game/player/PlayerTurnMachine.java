package com.nuggets.slaphappy.peakoilproject.game.player;

import android.util.Log;

import com.nuggets.slaphappy.peakoilproject.game.PeakOilEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachineException;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.Transition;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.EventDispatchListener;

import java.util.LinkedList;

/**
 * com.nuggets.slaphappy.peakoilproject.game.player
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class PlayerTurnMachine extends StateMachine<TurnAction> {
    final LinkedList<PlayerTurn> players = new LinkedList<>();
    protected int passes = 0;
    final PeakOilEngine parent;
    public PlayerTurnMachine(LinkedList<Player> playerList, PeakOilEngine parent) {
        this.parent = parent;
        buildStateMachine(playerList);
        this.addPropertyChangeListener(EventDispatchListener.Logger());
    }
    /*
    * Register action/transition pairs with the states ...
    * */
    private void buildStateMachine(LinkedList<Player> playerList) {
        for(int i = 0 ; i < playerList.size() ; i ++){
            final int nextIndex = (i + 1)%players.size();
            final PlayerTurn state = new PlayerTurn(playerList.get(i));
            state.registerTransition(TurnType.END,new EndTurnTransition(state,nextIndex));
            state.registerTransition(state.getDispatchedAction(),new DispatchTransition(state));
            players.add(state);
        }
    }

    /*
     * Inner Data classes ...
     *
     * */

    private class DispatchTransition extends Transition<TurnAction>{
        final PlayerTurn state;

        public DispatchTransition(PlayerTurn state) {
            this.state = state;
        }

        @Override
        protected State<TurnAction> handle(TurnAction item) {
            try {
                passes = 0;
                parent.doAction(state.getNextAction());
                state.setTakenActionThisTurn(true);
            } catch (StateMachineException e) {
                Log.e("PKO","Error dispatching to PeakOilEngine",e);
            }
            return state;
        }
    }

    private class EndTurnTransition extends Transition<TurnAction>{

        final int nextIndex;
        private final PlayerTurn state;

        public EndTurnTransition(PlayerTurn state, int nextIndex) {
            this.state = state;
            this.nextIndex = nextIndex;
        }

        @Override
        protected State<TurnAction> handle(TurnAction item) {
            if (state.isTakenActionThisTurn()) {
                passes = 0;
                state.setTakenActionThisTurn(false); //reset this for next round
                return players.get(nextIndex);
            }else{
                //this is just a pass, the player took no actions other than passing....
                passes++;
                if(passes>=players.size()){
                    passes = 0;
                    try {
                        //This represents a new phase so we start rotating at the first player again
                        parent.doAction(PeakOilEngine.PhaseAction.ADVANCE);
                    } catch (StateMachineException e) {
                        Log.e("PKO","Error advancing PeakOilEngine",e);
                    }
                    return players.getFirst();
                }else{
                    //Just continue on to the next player like normal.
                    return players.get(nextIndex);
                }
            }
        }
    }



    /*
    * Override and utility methods ...
    * */

    private final PlayerTurn getCurrentPlayer(){
        return (PlayerTurn) getCurrentState();
    }
    @Override
    protected State<TurnAction> getInitialState() {
        return players.getFirst();
    }
    private enum TurnType implements TurnAction{
        END;
        TurnType(){}

        @Override
        public void cleanup() {}
    }



    /*
    * Public Methods ....
    * */

    public void currentPlayerEndTurn() throws StateMachineException {
        getCurrentState().doAction(this,TurnType.END);
    }
    public void currentPlayerDoAction(PeakOilEngine.PhaseActionItem dispatchedAction) throws StateMachineException {
        getCurrentPlayer().setNextAction(dispatchedAction);
        getCurrentState().doAction(this,getCurrentPlayer().getDispatchedAction());
    }

}
