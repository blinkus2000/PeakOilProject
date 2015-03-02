package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.EventDispatchListener;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.PropertyChangeDispatch;

/**
 * Created by Jack on 18/02/2015.
 */
public abstract class StateMachine {
    State currentState = null;
    PropertyChangeDispatch dispatch;

    public StateMachine() {
        dispatch = new PropertyChangeDispatch(this);
    }

    public final synchronized void doAction(ActionItem item) throws StateMachineException{
        if(currentState == null){
            currentState = getInitialState();
        }
        currentState = currentState.doAction(this,item);
    }
    public abstract State getInitialState();

    public final synchronized State getCurrentState(){
        return currentState;
    }

    final void fireOnEnter(State newState) {
        dispatch.fireOnEnter( newState);
    }

    final void fireOnExit(State oldState) {
        dispatch.fireOnExit(oldState);
    }

    public void addPropertyChangeListener(EventDispatchListener listener) {
        dispatch.addPropertyChangeListener(listener);
    }

    public void fireTransition( State newState, ActionItem item) {
        dispatch.fireTransition(  newState,  item);
    }
    public void shutdown(){
        dispatch.stop();
    }

    public void waitForAllEventsDispatched() throws InterruptedException {
        dispatch.waitForAllEventsDispatched();
    }
}
