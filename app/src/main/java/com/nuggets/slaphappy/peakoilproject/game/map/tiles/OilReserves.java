package com.nuggets.slaphappy.peakoilproject.game.map.tiles;

/**
 * com.nuggets.slaphappy.peakoilproject.game.map.tiles
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class OilReserves extends Resource {
    protected InReserve inReserve = new InReserve();
    public class InReserve{
        int units = 0;
    }
}
