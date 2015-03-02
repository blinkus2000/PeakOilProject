package com.nuggets.slaphappy.peakoilproject.util.hexMap;


import java.util.EnumMap;

/**
 * Created by Jack on 18/02/2015.
 */
public enum HexDirection {
    NW, NE, E, SE, SW, W, Center;
    public static final HexDirection[] dvalues = {NW, NE, E, SE, SW, W};

    public static <T> EnumMap<HexDirection, T> getMap() {
        return new EnumMap<>(HexDirection.class);
    }
    /**
     * Translates a location into this direction
     */
    public HexLoc translate(HexLoc pair) {
        HexLoc p = null;
        int westChange = (pair.second + 1) % 2;
        int eastChange = pair.second % 2;
        switch (this) {
            case NE:
                p = new HexLoc(pair.first + eastChange, pair.second + 1);
                break;
            case NW:
                p = new HexLoc(pair.first - westChange, pair.second + 1);
                break;
            case SE:
                p = new HexLoc(pair.first + eastChange, pair.second - 1);
                break;
            case SW:
                p = new HexLoc(pair.first - westChange, pair.second - 1);
                break;
            case E:
                p = new HexLoc(pair.first + 1, pair.second);
                break;
            case W:
                p = new HexLoc(pair.first - 1, pair.second);
                break;
            case Center:
                p = new HexLoc(pair.first, pair.second);
                break;
        }
        return p;
    }

    /**
     * @return a direction opposite to this one
     */
    public HexDirection opposite() {
        HexDirection d = this;
        switch (this) {
            case NE:
                d = SW;
                break;
            case NW:
                d = SE;
                break;
            case SE:
                d = NW;
                break;
            case SW:
                d = NE;
                break;
            case E:
                d = W;
                break;
            case W:
                d = E;
                break;
        }
        return d;
    }

    /*
    * @return a direction that rotated n steps from here, positive is clockwise and negative is counter clockwise
    * */
    public HexDirection rotate(int step) {
        //Rotation on Center returns Center
        if (this == Center || step == 0) {
            return this;
        }
        final int length = dvalues.length;
        int testIndex = ordinal() + step;
        if (testIndex < 0) {
            testIndex = (testIndex % length) + length;
        }
        int val = testIndex % length;
        HexDirection returnVal = dvalues[val];
        return returnVal;

    }


}
