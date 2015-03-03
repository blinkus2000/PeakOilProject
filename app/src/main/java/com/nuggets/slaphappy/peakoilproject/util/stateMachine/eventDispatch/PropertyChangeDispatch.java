package com.nuggets.slaphappy.peakoilproject.util.stateMachine.eventDispatch;

import android.util.Log;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.ActionItem;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jack on 18/02/2015.
 */
public class PropertyChangeDispatch extends PropertyChangeSupport{
    private final StateMachine parent;

    /**
     * Creates a new instance that uses the source bean as source for any event.
     *
     * @param source the bean used as source for all events.
     */
    private Thread dispatch;
    private LinkedBlockingQueue<PropertyChangeEvent> workQueue;
    private volatile boolean isRunning = true;
    private final Object eventLock = new Object();
    public PropertyChangeDispatch(StateMachine source) {
        super(source);
        this.parent = source;
        workQueue = new LinkedBlockingQueue<>();
        start();
    }

    public final void start() {
        stop();
        isRunning = true;
        dispatch = new Thread(new Dispatcher(),"Event Dispatch");
        dispatch.start();
    }

    public final void stop() {
        isRunning=false;
        workQueue.clear();

        if (dispatch!=null) {
            dispatch.interrupt();
            try {
                dispatch.join(100);
            } catch (Exception e) {
            }
            dispatch = null;
        }
    }

    private class Dispatcher implements Runnable{
        @Override
        public void run() {
            while(isRunning&&!Thread.currentThread().isInterrupted()){
                try {
                    asyncFire(workQueue.take());
                } catch (InterruptedException e) {
                } catch(Exception e){
                    Log.e("PKO","unhandled exception dispatching",e);
                  break;
                } finally {
                    synchronized (eventLock){
                        eventLock.notifyAll();
                    }
                }
            }
        }
    }
    private final void asyncFire(PropertyChangeEvent evt){
        super.firePropertyChange(evt);
    }
    public void fireOnEnter(State newState){
        firePropertyChange(new OnEnterEvent(parent,newState));
    }
    public void fireOnExit(State oldState){
        firePropertyChange(new OnExitEvent(parent,oldState));
    }

    public void fireTransition( State newState, ActionItem item) {
        firePropertyChange(new OnTransitionEvent(parent,item,newState));
    }
    public void waitForAllEventsDispatched() throws InterruptedException {
        while(!workQueue.isEmpty()){
            synchronized (eventLock){
                eventLock.wait(1000);
            }
        }
    }
}
