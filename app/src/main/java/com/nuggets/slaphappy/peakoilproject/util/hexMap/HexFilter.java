package com.nuggets.slaphappy.peakoilproject.util.hexMap;

/**
 * Created by Jack on 23/02/2015.
 */
public interface HexFilter <T> {
    public abstract boolean accept(HexTile<T> val);
}
