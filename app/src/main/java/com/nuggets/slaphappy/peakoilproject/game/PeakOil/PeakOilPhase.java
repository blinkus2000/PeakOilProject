package com.nuggets.slaphappy.peakoilproject.game.PeakOil;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

/**
 * com.nuggets.slaphappy.peakoilproject.game.PeakOil
 * PeakOilProject
 * Created by Jack on 11/03/2015.
 */
public abstract class PeakOilPhase extends PhaseEngine.PhaseState {
    protected PeakOilEngine master = null;

    @Override
    protected final boolean onEnter(StateMachine<PhaseEngine.PhaseActionItem> parent) {
        if(master==null){
            master = (PeakOilEngine) parent;
        }
        return peakOilOnEnter();
    }

    protected  boolean peakOilOnEnter(){return false;}
}
