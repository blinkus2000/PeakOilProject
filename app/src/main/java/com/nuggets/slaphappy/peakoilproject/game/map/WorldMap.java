package com.nuggets.slaphappy.peakoilproject.game.map;

import com.nuggets.slaphappy.peakoilproject.game.map.tiles.TileInfo;
import com.nuggets.slaphappy.peakoilproject.game.map.tiles.WorldTile;
import com.nuggets.slaphappy.peakoilproject.util.hexMap.HexMap;
import com.nuggets.slaphappy.peakoilproject.util.hexMap.HexTile;

/**
 * com.nuggets.slaphappy.peakoilproject.game.map
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class WorldMap extends HexMap<TileInfo,WorldTile> {
    public WorldMap(int depth, int rows) {
        super(depth, rows);
    }
}
