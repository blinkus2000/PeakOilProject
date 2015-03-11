package com.nuggets.slaphappy.peakoilproject.game.PeakOil;

import android.util.Log;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.ActionItem;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.ActionWrapper;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachineException;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.Transition;

/**
 * com.nuggets.slaphappy.peakoilproject.game.PeakOil
 * PeakOilProject
 * Created by Jack on 11/03/2015.
 */
public class CompoundPeakOilPhase extends PeakOilPhase{
    final SubEngine inner;

    public CompoundPeakOilPhase(SubPhase ... subStates) {
        this.inner =  new SubEngine(subStates);
    }

    protected static abstract class SubPhase extends PhaseEngine.PhaseState{

    }
    protected class SubEngine extends PhaseEngine{
        int passesCurrentState = 0;
        SubEngine(SubPhase ... subStates){
            super(subStates);
            buildStateMachine(subStates);


        }

        private void buildStateMachine(SubPhase[] subStates) {
            for(final SubPhase subState : subStates){
                subState.registerTransition(PhaseAction.INNER_PASS,new Transition<PhaseActionItem>() {
                    @Override
                    protected State<PhaseActionItem> handle(PhaseActionItem item) {
                        passesCurrentState++;
                        if(passesCurrentState>=master.playerCount()){
                            try {
                                inner.doAction(PhaseAction.ADVANCE);
                            } catch (StateMachineException e) {
                                Log.e("PKO", "Failed to transition with inner state");
                            }
                            passesCurrentState=0;
                            return inner.getCurrentState();
                        }
                        return subState;
                    }
                });
            }
        }

        @Override
        public synchronized void doAction(PhaseActionItem item) throws StateMachineException {
            super.doAction(item);
            if(item!=PhaseAction.INNER_PASS){
                passesCurrentState=0;
            }
        }
    }
    @Override
    public State<PhaseEngine.PhaseActionItem> doAction(StateMachine<PhaseEngine.PhaseActionItem> parent, PhaseEngine.PhaseActionItem item) throws StateMachineException {
        if(item instanceof ActionWrapper){
            ActionItem wrappedItem = ((ActionWrapper) item).getItem();
            if(wrappedItem instanceof PhaseEngine.PhaseActionItem){
                inner.doAction((PhaseEngine.PhaseActionItem) wrappedItem);
                return this;
            }
        }

        return super.doAction(parent, item);
    }

}
