package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.ActionItem;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

public class OnTransitionEvent extends DispatchEvent<ActionItem,State> {
        public OnTransitionEvent(StateMachine source,ActionItem item, State newState ) {
            super(source, "On Transition Event",item, newState);
        }
        @Override
        public void getHandledBy(EventHandler handler) {
            handler.handleOnTransition(this);
        }
    }