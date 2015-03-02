package com.nuggets.slaphappy.peakoilproject.util.stateMachine;

/**
 * Created by Jack on 18/02/2015.
 */
public class StateMachineException extends Exception {
    public StateMachineException(String msg) {
        super(msg);
    }
    public StateMachineException() {
        this("");
    }
}
