package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public abstract class EventDispatchListener implements PropertyChangeListener , EventHandler {
    @Override
    public final void propertyChange(PropertyChangeEvent event) {
        if(event instanceof DispatchEvent){
            ((DispatchEvent)event).getHandledBy(this);
        }
    }


    @Override
    public void handleOnEnter(OnEnterEvent evt) {

    }

    @Override
    public void handleOnExit(OnExitEvent evt) {

    }

    @Override
    public void handleOnTransition(OnTransitionEvent evt) {

    }
}
