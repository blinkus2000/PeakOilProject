package com.nuggets.slaphappy.peakoilproject.game.player;

import com.nuggets.slaphappy.peakoilproject.game.PeakOilEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachineException;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.Transition;

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
    }
    /*
    * Register action/transition pairs with the states ...
    * */
    private void buildStateMachine(LinkedList<Player> playerList) {
        for(int i = 0 ; i < playerList.size() ; i ++){
            final int nextIndex = (i + 1)%players.size();
            final PlayerTurn state = new PlayerTurn(playerList.get(i));
            state.registerTransition(TurnType.END,new EndTurnTransition(nextIndex));
            state.registerTransition(TurnType.PASS,new PassTransition(nextIndex));
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
                PlayerTurnMachine.this.parent.doAction(state.getNextAction());
            } catch (StateMachineException e) {
            }
            return state;
        }
    }
    private abstract class TurnCompleteTransition extends Transition<TurnAction>{
        final int nextIndex;

        public TurnCompleteTransition(int nextIndex) {
            this.nextIndex = nextIndex;
        }
    }
    private class EndTurnTransition extends TurnCompleteTransition{

        public EndTurnTransition(int nextIndex) {
            super(nextIndex);
        }

        @Override
        protected State<TurnAction> handle(TurnAction item) {
            passes = 0;
            return players.get(nextIndex);
        }
    }
    private class PassTransition extends TurnCompleteTransition{

        public PassTransition(int nextIndex) {
            super(nextIndex);
        }

        @Override
        protected State<TurnAction> handle(TurnAction item) {
            passes++;
            if(passes>=players.size()){
                passes = 0;
                try {
                    PlayerTurnMachine.this.parent.doAction(PeakOilEngine.PhaseAction.ADVANCE);
                } catch (StateMachineException e) {
                    e.printStackTrace();
                }
                return players.get(0);
            }else{
                return players.get(nextIndex);
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
        END,PASS;
        TurnType(){}

        @Override
        public void cleanup() {}
    }



    /*
    * Public Methods ....
    * */


    public void currentPlayerPass() throws StateMachineException {
        getCurrentState().doAction(this,TurnType.PASS);
    }
    public void currentPlayerEndTurn() throws StateMachineException {
        getCurrentState().doAction(this,TurnType.END);
    }
    public void currentPlayerDoAction(PeakOilEngine.PhaseActionItem dispatchedAction) throws StateMachineException {
        getCurrentPlayer().setNextAction(dispatchedAction);
        getCurrentState().doAction(this,getCurrentPlayer().getDispatchedAction());
    }

}
