package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import com.nuggets.slaphappy.peakoilproject.util.Location;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * ${PACKAGE_NAME}
 * ${PROJECT_NAME}
 * Created by Jack on 18/02/2015.
 *
 *
 */
public class HexTile <T> implements Location {
    private final HexLoc loc;
    private T data = null;
    final EnumMap<HexDirection, HexTile<T>> nodes = HexDirection.getMap();

    public HexTile(HexLoc loc) {
        this.loc = loc;
    }


    public final T getData(){
        return data;
    }
    public final void setData(T data){
        this.data = data;
    }
    public final void addChild(HexDirection dir, HexTile<T> val) {
        nodes.put(dir, val);
    }

    @Override
    public HexLoc loc() {
        return loc;
    }

    public List<HexDirection> getPossibleDirections() {
        List<HexDirection> directions =new LinkedList<>();
        for (HexDirection dir : HexDirection.dvalues) {
            if (nodes.get(dir) != null) {
                directions.add(dir);
            }
        }
        return directions;
    }

    @Override
    public String toString() {
        return "HexTile{" +
                "loc=" + loc +
                '}';
    }
}
