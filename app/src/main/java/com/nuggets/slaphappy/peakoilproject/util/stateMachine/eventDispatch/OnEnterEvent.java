package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

public  class OnEnterEvent extends DispatchEvent<State,State> {
        public OnEnterEvent(StateMachine source, State newState) {
            super(source, "On Enter Event", null, newState);
        }



        @Override
        public void getHandledBy(EventHandler handler) {
            handler.handleOnEnter(this);
        }
    }