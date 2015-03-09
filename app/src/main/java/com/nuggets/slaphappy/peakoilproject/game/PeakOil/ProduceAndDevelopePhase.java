package com.nuggets.slaphappy.peakoilproject.game.PeakOil;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

/**
 * com.nuggets.slaphappy.peakoilproject.game.PeakOil
 * PeakOilProject
 * Created by Jack on 09/03/2015.
 */
public class ProduceAndDevelopePhase extends PhaseEngine.PhaseState {

    @Override
    protected boolean onExit(StateMachine<PhaseEngine.PhaseActionItem> parent) {
        ((PeakOilEngine)parent).calculateRange();
        return true;
    }
}
