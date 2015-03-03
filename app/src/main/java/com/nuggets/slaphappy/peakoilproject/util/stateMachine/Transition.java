package com.nuggets.slaphappy.peakoilproject.util.stateMachine;


public abstract class Transition<T extends ActionItem>  {

    protected State<T> newState = null;
    protected State<T> oldState = null;


    public final State<T> transition(StateMachine<T> parent,State<T> oldState, T item)throws StateMachineException {
        this.newState = null;
        this.oldState = oldState;
        newState = handle(item);
        parent.fireTransition( newState , item);
        return newState;
    }

    protected abstract State<T> handle(T item);


}
