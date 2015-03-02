package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

public class OnExitEvent extends DispatchEvent<State,State> {
        public OnExitEvent(StateMachine source, State oldState) {
            super(source, "On Exit Event", oldState,null);
        }



        @Override
        public void getHandledBy(EventHandler handler) {
            handler.handleOnExit(this);
        }
    }