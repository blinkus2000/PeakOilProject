package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.EventDispatchListener;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.OnEnterEvent;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.OnExitEvent;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch.OnTransitionEvent;

import junit.framework.TestCase;

public class StateMachineTest extends TestCase {
    State[] states;
    enum Actions implements ActionItem{
        ADVANCE_0,ADVANCE_1,ADVANCE_2,ADVANCE_3;
        Actions(){}
        @Override
        public void cleanup() {
            System.out.println(name()+" cleaning up");
        }
    }
    int onEntryCount;
    int onExitCount;
    int onTransitionCount;
    int onEntryEventCount;
    int onExitEventCount;
    int onTransitionEventCount;
    StateMachine underTest;
    EventDispatchListener listener = new EventDispatchListener(){
        @Override
        public void handleOnEnter(OnEnterEvent evt) {
            onEntryEventCount++;
        }

        @Override
        public void handleOnExit(OnExitEvent evt) {
            onExitEventCount++;
        }

        @Override
        public void handleOnTransition(OnTransitionEvent evt) {
            onTransitionEventCount++;
        }
    };
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        onEntryCount = 0;
        onExitCount = 0;
        onTransitionCount = 0;
        onEntryEventCount = 0;
        onExitEventCount = 0;
        onTransitionEventCount = 0;
        generateNewStateMachine();
        registerActionEvents();
        underTest.addPropertyChangeListener(listener);
    }

    private void registerActionEvents() {
        for(int i = 0 ; i < states.length ; i ++){
            State state = states[i];
            final int stateIndex = i;
            for(final Actions action : Actions.values() ){
                state.registerTransition(action,new Transition(){
                    @Override
                    protected State handle(ActionItem item) {
                        try {
                            int chosenIndex = (stateIndex+action.ordinal())%states.length;
                            return states[chosenIndex];
                        } finally {
                            onTransitionCount++;
                        }
                    }
                });
            }
        }
    }

    private void generateNewStateMachine() {
        states = new State[Actions.values().length];
        for(int i = 0 ; i < states.length ; i ++){
            states[i] = new State(){
                @Override
                protected boolean onExit(StateMachine parent) {
                    try {return true;} finally {onExitCount++;}
                }
                @Override
                protected boolean onEnter(StateMachine parent) {
                    try {return true;} finally {onEntryCount++;}
                }
            };
        }
        underTest = new StateMachine() {
            @Override
            public State getInitialState() {
                return states[0];
            }
        };
        underTest.getInitialState();
    }

    protected void tearDown() throws Exception{
        states = null;
    }
    public void testGetCurrentStateAdvanceSequential() throws Exception {
        assertNotNull(underTest.getCurrentState());
        assertEquals(0,onEntryCount);
        assertEquals(0,onExitCount);
        assertEquals(0,onTransitionCount);
        assertEquals(0,onEntryEventCount);
        assertEquals(0,onExitEventCount);
        assertEquals(0,onTransitionEventCount);
        underTest.doAction(Actions.ADVANCE_0);
        underTest.waitForAllEventsDispatched();
        assertEquals(states[0],underTest.getCurrentState());
        assertEquals(1,onEntryCount);
        assertEquals(1,onExitCount);
        assertEquals(1,onTransitionCount);
        assertEquals(1,onEntryEventCount);
        assertEquals(1,onExitEventCount);
        assertEquals(1,onTransitionEventCount);
        underTest.doAction(Actions.ADVANCE_2);
        underTest.waitForAllEventsDispatched();
        assertNotNull(underTest.getCurrentState());
        assertEquals(states[2],underTest.getCurrentState());
        assertEquals(2,onEntryCount);
        assertEquals(2,onExitCount);
        assertEquals(2,onTransitionCount);
        assertEquals(2,onEntryEventCount);
        assertEquals(2,onExitEventCount);
        assertEquals(2,onTransitionEventCount);
        underTest.doAction(Actions.ADVANCE_3);
        underTest.waitForAllEventsDispatched();
        assertNotNull(underTest.getCurrentState());
        assertEquals(states[1],underTest.getCurrentState());
        assertEquals(3,onEntryCount);
        assertEquals(3,onExitCount);
        assertEquals(3,onTransitionCount);
        assertEquals(3,onEntryEventCount);
        assertEquals(3,onExitEventCount);
        assertEquals(3,onTransitionEventCount);
    }


}