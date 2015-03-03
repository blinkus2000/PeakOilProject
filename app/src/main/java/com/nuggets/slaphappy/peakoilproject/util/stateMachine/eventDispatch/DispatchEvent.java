package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

import java.beans.PropertyChangeEvent;

public abstract class DispatchEvent<Old,New> extends PropertyChangeEvent {


        public DispatchEvent(StateMachine source, String propertyName,  Old oldState, New newState) {
            super(source, propertyName, oldState, newState);
        }
        @Override
        public Old getOldValue() {
            return (Old) super.getOldValue();
        }

        @Override
        public New getNewValue() {
            return (New)super.getNewValue();
        }

        @Override
        public StateMachine getSource() {
            return (StateMachine)super.getSource();
        }
        public abstract void getHandledBy(EventHandler handler);
        public String toString(){
            String oldValue = getOldValue()==null?" oldValue = null":getOldValue().getClass().getSimpleName()+" = "+getOldValue();
            String newValue = getNewValue()==null?" newValue = null":getNewValue().getClass().getSimpleName()+" = "+getNewValue();
            return "["+getClass().getSimpleName()+"] ["+ oldValue + "] [" + newValue+"]";
        }
    }