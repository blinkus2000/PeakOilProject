package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

import java.util.HashMap;

/**
 * Created by Jack on 18/02/2015.
 */
public abstract class State {
    protected HashMap<ActionItem,Transition> transitionMap = new HashMap<>();
    public final State doAction(StateMachine parent, ActionItem item) throws StateMachineException {
        try {
            Transition transition = transitionMap.get(item);
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
    final void doOnExit(StateMachine parent){
        if(onExit(parent)){
            parent.fireOnExit(this);
        }
    }
    final void doOnEnter(StateMachine parent,State oldState){
        if(onEnter(parent)){
            parent.fireOnEnter(this);
        }
    }
    /**
     * Override this to execute on state exit code, return true if you want the event broadcast
     *
     * */
    protected boolean onExit(StateMachine parent){return false;}
    /**
     * Override this to execute on state entry code, return true if you want the event broadcast
     *
     * */
    protected boolean onEnter(StateMachine parent){return false;}
    public final void registerTransition(ActionItem item, Transition transition){
        transitionMap.put(item,transition);
    }
}
