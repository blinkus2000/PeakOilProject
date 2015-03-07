package com.nuggets.slaphappy.peakoilproject.game;

import android.support.annotation.NonNull;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.ActionItem;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachineException;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.Transition;

/**
 * com.nuggets.slaphappy.peakoilproject.game
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class PhaseEngine extends StateMachine<PhaseEngine.PhaseActionItem> {
    protected final PhaseState[] states;

    public PhaseEngine(@NonNull PhaseState ... states) {
        this.states = states;
        buildAdvanceingTransitions();
    }

    private void buildAdvanceingTransitions() {
        for(int i = 0 ; i < this.states.length ; i++){
            this.states[i].registerTransition(PhaseAction.ADVANCE,new AdvanceTransition(i));
        }
    }

    @Override
    protected PhaseState getInitialState() {
        return states[0];
    }

    public void advance() throws StateMachineException {
        doAction(PhaseAction.ADVANCE);
    }

    public interface PhaseActionItem extends ActionItem{}
    private static enum PhaseAction implements PhaseActionItem{
    ADVANCE;
        @Override
        public void cleanup() {

        }
    }
    public static abstract class PhaseState extends State<PhaseActionItem> {

    }

    private class AdvanceTransition extends Transition<PhaseActionItem>{
        final int parentStateIndex;

        private AdvanceTransition(int parentStateIndex) {
            this.parentStateIndex = parentStateIndex;
        }

        @Override
        protected PhaseState handle(PhaseActionItem item) {
            int newIndex = (parentStateIndex+1)%states.length;
            return states[newIndex];
        }
    }
    public abstract class NonAdvanceTransition extends Transition<PhaseActionItem>{
        final protected PhaseState parent;

        protected NonAdvanceTransition(PhaseState parent) {
            this.parent = parent;
        }

        @Override
        protected final PhaseState handle(PhaseActionItem item) {
            doWork(item);
            return parent;
        }
        protected abstract void doWork(PhaseActionItem item);

    }
}
