package com.nuggets.slaphappy.peakoilproject.game.player;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachineException;

import junit.framework.TestCase;

import java.util.LinkedList;

public class PlayerTurnMachineTest extends TestCase {
    PlayerTurnMachine underTest;
    TestEngine parentEngine;
    LinkedList<Player> players;
    TestState[] engineStates;

    public void testAdvancingNoActions() throws StateMachineException {
        for (TestState testState : engineStates) {
            assertEquals(testState,parentEngine.getCurrentState());
            //all players pass
            for (Player player : players) {
                assertEquals(player,underTest.getCurrentPlayer());
                underTest.currentPlayerEndTurn();
            }
        }
    }
    public void testAdvancingWithActions() throws StateMachineException {
        for (TestState testState : engineStates) {
            assertEquals(testState,parentEngine.getCurrentState());
            // with players doing actions we will not advance to the next state
            for (Player player : players) {
                assertEquals(player,underTest.getCurrentPlayer());
                underTest.currentPlayerDoAction(DoAction.DO_ACTION);
                underTest.currentPlayerEndTurn();
            }
            assertEquals(testState,parentEngine.getCurrentState());
            //once all players pass the test state will advance
            for (Player player : players) {
                assertEquals(player, underTest.getCurrentPlayer());
                underTest.currentPlayerEndTurn();
            }
        }
    }
    public void testAdvancingWithOnlyOnePlayerTakingActions() throws StateMachineException {
        for (TestState testState : engineStates) {
            assertEquals(testState,parentEngine.getCurrentState());
            // with players doing actions we will not advance to the next state
            for (Player player : players) {
                assertEquals(player,underTest.getCurrentPlayer());
                if (player==players.getLast()) {
                    underTest.currentPlayerDoAction(DoAction.DO_ACTION);
                }
                underTest.currentPlayerEndTurn();
            }
            assertEquals(testState,parentEngine.getCurrentState());
            //once all players pass the test state will advance
            for (Player player : players) {
                assertEquals(player, underTest.getCurrentPlayer());
                underTest.currentPlayerEndTurn();
            }
        }
    }




    public void setUp() throws Exception {
        super.setUp();
        buildEngine();
        buildPlayers();

        underTest = new PlayerTurnMachine(players,parentEngine);

    }

    private void buildPlayers() {
        players = new LinkedList<>();
        for(int i = 0 ; i < 4 ; i ++){
            players.add(new Player(i,"Player"+i));
        }
    }

    private void buildEngine() {
        engineStates = new TestState[3];
        for(int i = 0 ; i < engineStates.length ; i ++){
            engineStates[i] = new TestState();
        }
        parentEngine = new TestEngine(engineStates);
    }

    public void tearDown() throws Exception {

    }
    enum DoAction implements PhaseEngine.PhaseActionItem{
        DO_ACTION;
        DoAction(){}
        @Override
        public void cleanup() {
        }
    }
    public class TestEngine extends PhaseEngine {
        public TestEngine(TestState ... states) {
            super(states);
            for(final TestState state : states){
                state.registerTransition(DoAction.DO_ACTION,new NonAdvanceTransition(state) {
                    @Override
                    protected void doWork(PhaseActionItem item) {
                        state.didWork();
                    }
                });
            }
        }
    }
    public class TestState extends PhaseEngine.PhaseState{
        int workDid = 0;
        public void didWork() {
            workDid++;
        }
    }
}