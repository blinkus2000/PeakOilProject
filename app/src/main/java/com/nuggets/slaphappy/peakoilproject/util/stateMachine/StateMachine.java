package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.EventDispatchListener;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.PropertyChangeDispatch;

/**
 * Created by Jack on 18/02/2015.
 */
public abstract class StateMachine<T extends ActionItem> {
    State<T> currentState = null;
    PropertyChangeDispatch dispatch;

    public StateMachine() {
        dispatch = new PropertyChangeDispatch(this);
    }

    public final synchronized void doAction(T item) throws StateMachineException{

        currentState = getCurrentState().doAction(this,item);
    }
    protected abstract State<T> getInitialState();

    public final synchronized State<T> getCurrentState(){
        if(currentState == null){
            currentState = getInitialState();
        }
        return currentState;
    }

    final void fireOnEnter (State<T> newState) {
        dispatch.fireOnEnter( newState);
    }

    final void fireOnExit (State<T> oldState) {
        dispatch.fireOnExit(oldState);
    }

    public void addPropertyChangeListener(EventDispatchListener listener) {
        dispatch.addPropertyChangeListener(listener);
    }

    public void fireTransition( State<T> newState, ActionItem item) {
        dispatch.fireTransition(  newState,  item);
    }
    public void shutdown(){
        dispatch.stop();
    }

    public void waitForAllEventsDispatched() throws InterruptedException {
        dispatch.waitForAllEventsDispatched();
    }
}
