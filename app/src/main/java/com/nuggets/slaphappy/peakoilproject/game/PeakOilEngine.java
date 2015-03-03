package com.nuggets.slaphappy.peakoilproject.game;

import com.nuggets.slaphappy.peakoilproject.util.stateMachine.ActionItem;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

/**
 * com.nuggets.slaphappy.peakoilproject.game
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class PeakOilEngine extends StateMachine<PeakOilEngine.PhaseActionItem> {
    @Override
    protected State<PhaseActionItem> getInitialState() {
        return null;
    }
    public interface PhaseActionItem extends ActionItem{}
    public static enum PhaseAction implements PhaseActionItem{
    ADVANCE;
        @Override
        public void cleanup() {

        }
    }

}
