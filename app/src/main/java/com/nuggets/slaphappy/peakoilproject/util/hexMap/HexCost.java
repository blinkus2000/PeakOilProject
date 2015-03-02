package com.nuggets.slaphappy.peakoilproject.util.hexMap;

/**
 * Created by Jack on 02/03/2015.
 */
public interface HexCost<T> {
    Integer getCost(HexTile<T> from, HexTile<T> to);
}
