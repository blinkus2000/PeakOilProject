package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import com.nuggets.slaphappy.peakoilproject.util.Grid;

/**
 * Created by Jack on 18/02/2015.
 */
public class HexMap<T, V extends HexTile<T>> extends Grid<V> {
    public HexMap(int depth, int rows) {
        super(rows, depth);
    }

    @Override
    public void add(V val) {
        super.add(val);
        HexLoc node = val.loc();
        for (HexDirection dir : HexDirection.values()) {
            try {
                if (dir != HexDirection.Center) {
                    V tile = get(dir.translate(node));
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

    public void fill(TileFactory<T,V> fact) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                add(fact.getTile(new HexLoc(j, i)));
            }
        }
    }
    public static interface TileFactory<T,V extends HexTile<T>>{
        public V getTile(HexLoc loc);
    }
}
