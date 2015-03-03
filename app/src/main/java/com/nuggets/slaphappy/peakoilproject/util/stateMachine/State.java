package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

import java.util.HashMap;

/**
 * Created by Jack on 18/02/2015.
 */
public abstract class State<T extends ActionItem> {
    protected HashMap<T,Transition<T>> transitionMap = new HashMap<>();
    public final State doAction(StateMachine<T> parent, T item) throws StateMachineException {
        try {
            Transition<T> transition = transitionMap.get(item);
            if(transition == null){
                throw new StateMachineException("Illegal Action: "+ item+ " for State: "+this);
            }
            doOnExit( parent);
            final State newState = transition.transition(parent,this,item);
            newState.doOnEnter( parent,this);
            return newState;
        } finally {
            item.cleanup();
        }
    }
    final void doOnExit(StateMachine<T> parent){
        if(onExit(parent)){
            parent.fireOnExit(this);
        }
    }
    final void doOnEnter(StateMachine<T> parent,State<T> oldState){
        if(onEnter(parent)){
            parent.fireOnEnter(this);
        }
    }
    /**
     * Override this to execute on state exit code, return true if you want the event broadcast
     *
     * */
    protected boolean onExit(StateMachine<T> parent){return false;}
    /**
     * Override this to execute on state entry code, return true if you want the event broadcast
     *
     * */
    protected boolean onEnter(StateMachine<T> parent){return false;}
    public final void registerTransition(T item, Transition<T> transition){
        transitionMap.put(item,transition);
    }
}
