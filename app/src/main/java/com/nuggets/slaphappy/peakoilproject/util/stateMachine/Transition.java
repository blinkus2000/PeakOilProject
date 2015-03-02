package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

/**
 * Created by Jack on 18/02/2015.
 */
public abstract class Transition  {

    protected State newState = null;
    protected State oldState = null;


    public final State transition(StateMachine parent,State oldState, ActionItem item)throws StateMachineException {
        this.newState = null;
        this.oldState = oldState;
        newState = handle(item);
        parent.fireTransition( newState , item);
        return newState;
    }

    protected abstract State handle(ActionItem item);


}
