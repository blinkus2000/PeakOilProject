package com.nuggets.slaphappy.peakoilproject.game.player;

/**
 * com.nuggets.slaphappy.peakoilproject.game.player
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class Player {
    public static final Player UNASSIGNED = new Player(-1, "UNASSIGNED");
    final int playerID;
    final String name;

    public Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
    }

}
