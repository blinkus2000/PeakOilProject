package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import java.util.LinkedList;

/**
 * com.nuggets.slaphappy.peakoilproject.util.hexMap
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class HexPath <T> extends LinkedList<HexTile<T>> {
    private int pathCost = 0;

    public final void addFirst(HexTile<T> tile, int cost) {
        super.addFirst(tile);
        pathCost = Math.max(pathCost,cost);
    }

    public final void addLast(HexTile<T> tile, int cost) {
        super.addLast(tile);
        pathCost = Math.max(pathCost,cost);
    }

    public final int getPathCost() {
        return pathCost;
    }
}
