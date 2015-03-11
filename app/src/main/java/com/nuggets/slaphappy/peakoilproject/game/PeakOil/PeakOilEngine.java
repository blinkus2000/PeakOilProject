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
    //called onExit() Produce and Develop
    void calculateRange() {
        //TODO: See Rules
    }
    //called onExit() FightPhase
    void resetPhase() {
        //TODO: See Rules
    }
    //called once onEnter() Produce and Develop sub state Rust and Mayhem
    //this the rust portion
    void rustAndMahem() {
        //TODO: See rules
    }
    void explore(ExploreAndProspectPhase.Explore explore) {

    }


    public int playerCount() {
        return playerTurns.playerCount();
    }
}
