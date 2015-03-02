package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import com.nuggets.slaphappy.peakoilproject.util.Grid;

/**
 * Created by Jack on 18/02/2015.
 */
public class HexMap<T> extends Grid<HexTile<T>> {
    public HexMap(int depth, int rows) {
        super(rows, depth);
    }

    @Override
    public void add(HexTile<T> val) {
        super.add(val);
        HexLoc node = val.loc();
        for (HexDirection dir : HexDirection.values()) {
            try {
                if (dir != HexDirection.Center) {
                    HexTile<T> tile = get(dir.translate(node));
                    if (tile != null) {
                        val.addChild(dir, tile);
                        tile.addChild(dir.opposite(), val);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void fill() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                add(new HexTile<T>(new HexLoc(j, i)));
            }
        }
    }

}
