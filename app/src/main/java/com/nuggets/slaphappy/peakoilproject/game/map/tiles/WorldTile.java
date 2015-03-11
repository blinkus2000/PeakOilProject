package com.nuggets.slaphappy.peakoilproject.game.map.tiles;

import com.nuggets.slaphappy.peakoilproject.util.hexMap.HexLoc;
import com.nuggets.slaphappy.peakoilproject.util.hexMap.HexTile;

/**
 * com.nuggets.slaphappy.peakoilproject.game.map.tiles
 * PeakOilProject
 * Created by Jack on 11/03/2015.
 */
public class WorldTile  extends HexTile<TileInfo> {
    public WorldTile(HexLoc loc, TileInfo info) {
        super(loc);
        this.setData(info);
    }
}
