package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

public interface EventHandler {
        void handleOnEnter(OnEnterEvent evt);

        void handleOnExit(OnExitEvent evt);

        void handleOnTransition(OnTransitionEvent evt);
        public static class Adapter implements EventHandler{

            @Override
            public void handleOnEnter(OnEnterEvent evt) {}

            @Override
            public void handleOnExit(OnExitEvent evt) {}

            @Override
            public void handleOnTransition(OnTransitionEvent evt) {}
        }
    }