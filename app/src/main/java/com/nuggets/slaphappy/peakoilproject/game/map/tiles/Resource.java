package com.nuggets.slaphappy.peakoilproject.game.map.tiles;

/**
 * com.nuggets.slaphappy.peakoilproject.game.map.tiles
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class Resource {
    protected Used used = new Used();
    protected Available avail = new Available();
    public class Used{
        int units = 0;
    }
    public class Available{
        int units = 0;
    }
}
