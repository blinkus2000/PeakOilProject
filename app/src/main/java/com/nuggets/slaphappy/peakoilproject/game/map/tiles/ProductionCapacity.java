package com.nuggets.slaphappy.peakoilproject.game.map.tiles;

import android.support.annotation.NonNull;

/**
 * com.nuggets.slaphappy.peakoilproject.game.map.tiles
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class ProductionCapacity {
    private TileInfo.TileType currentType;
    private int cap;
    public ProductionCapacity(@NonNull TileInfo.TileType type) {
        this.currentType = type;
        cap = type.initCap();

    }
    public void updateType(@NonNull TileInfo.TileType newType){
        if (newType== TileInfo.TileType.Fracked) {
            currentType = newType;
            cap = 0;
        }
    }
    public int getCapacity(){
        return cap;
    }

}
