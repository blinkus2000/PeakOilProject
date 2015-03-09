package com.nuggets.slaphappy.peakoilproject.game.PeakOil;

import com.nuggets.slaphappy.peakoilproject.game.PhaseEngine;
import com.nuggets.slaphappy.peakoilproject.game.map.WorldMap;
import com.nuggets.slaphappy.peakoilproject.game.player.Player;
import com.nuggets.slaphappy.peakoilproject.game.player.PlayerTurnMachine;

import java.util.LinkedList;

/**
 * com.nuggets.slaphappy.peakoilproject.game.PeakOil
 * PeakOilProject
 * Created by Jack on 09/03/2015.
 */
public class PeakOilEngine extends PhaseEngine {

    private final PlayerTurnMachine playerTurns;
    private final WorldMap map;
    public PeakOilEngine(LinkedList<Player> players){
        super(new ExploreAndProspectPhase(),new ProduceAndDevelopePhase(),new FightPhase() );
        map = buildMap();
        playerTurns = new PlayerTurnMachine(players,this);
    }

    private WorldMap buildMap() {
        final WorldMap initMap = new WorldMap(9, 7);
        //TODO: put all the tiles up in this shit
        return initMap;
    }

    public void calculateRange() {
        //TODO: do this shit
        // Calculate Range: increment or decrement if total increases or decreases respectively
    }

    public void reset() {
        //TODO: do this shit
        /*
        * Move all MP or Oil in MPU to MPA on same Space
        * Remove 1 MP, 1 Oil in MPU, or 1 PC from each space with MPA > 0
        * */
    }
}
