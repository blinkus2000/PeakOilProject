package com.nuggets.slaphappy.peakoilproject.game.PeakOil;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.StateMachine;

/**
 * com.nuggets.slaphappy.peakoilproject.game.PeakOil
 * PeakOilProject
 * Created by Jack on 09/03/2015.
 */
public class ProduceAndDevelopePhase extends CompoundPeakOilPhase {


    public ProduceAndDevelopePhase() {
        super(subStates);
    }
    @Override
    protected boolean onExit(StateMachine<PhaseEngine.PhaseActionItem> parent) {
        master.calculateRange();
        inner.reset();
        return true;
    }

    @Override
    protected boolean peakOilOnEnter() {
        RUST_AND_MAYHEM.isFirstEntry = true;
        return true;
    }


    public static class Produce extends SubPhase{
        private Produce(){}
    }
    public static class Transport extends SubPhase{
        private Transport(){}
    }
    public static class DevelopMPA extends SubPhase{
        private DevelopMPA(){}
    }
    public static class RustAndMayhem extends SubPhase{
        boolean isFirstEntry = true;
        private RustAndMayhem(){}

        @Override
        protected boolean onEnter(StateMachine<PhaseEngine.PhaseActionItem> parent) {
            if (isFirstEntry) {
                ((PeakOilEngine)parent).rustAndMahem();
                isFirstEntry = false;
            }
            return true;
        }
    }
    public static class DevelopPC extends SubPhase{
        private DevelopPC(){}
    }
    public static class DevelopPipelines extends SubPhase{
        private DevelopPipelines(){}
    }
    private final static Produce PRODUCE = new Produce();
    private final static Transport TRANSPORT = new Transport();
    private final static DevelopMPA DEVELOP_MPA = new DevelopMPA();
    private final static RustAndMayhem RUST_AND_MAYHEM = new RustAndMayhem();
    private final static DevelopPC DEVELOP_PC = new DevelopPC();
    private final static DevelopPipelines DEVELOP_PIPELINES = new DevelopPipelines();
    private final static SubPhase[] subStates = {PRODUCE,TRANSPORT,DEVELOP_MPA,RUST_AND_MAYHEM,DEVELOP_PC,DEVELOP_PIPELINES};

}
