package com.nuggets.slaphappy.peakoilproject.game.PeakOil;

import android.support.annotation.NonNull;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.util.hexMap.HexLoc;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.State;
import com.nuggets.slaphappy.peakoilproject.util.stateMachine.Transition;

/**
 * com.nuggets.slaphappy.peakoilproject.game.PeakOil
 * PeakOilProject
 * Created by Jack on 09/03/2015.
 */
public class ExploreAndProspectPhase extends PeakOilPhase {
    public final Explore EXPLORE_ACTION = new Explore();
    public static class Explore implements PhaseEngine.PhaseActionItem{
        private HexLoc start = null,finish = null;
        @Override
        public void cleanup() {
            start = null;
            finish = null;
        }
        private Explore(){}
        public void set(@NonNull HexLoc start,@NonNull HexLoc finish){
            this.start = start;
            this.finish = finish;
        }

        public HexLoc getStart() {
            return start;
        }
        public HexLoc getFinish() {
            return finish;
        }
    }
    public ExploreAndProspectPhase(){
        this.registerTransition(EXPLORE_ACTION,new Transition<PhaseEngine.PhaseActionItem>() {
            @Override
            protected State<PhaseEngine.PhaseActionItem> handle(PhaseEngine.PhaseActionItem item) {
                master.explore(EXPLORE_ACTION);
                return ExploreAndProspectPhase.this;
            }
        });
    }
}
