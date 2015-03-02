package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import android.util.Pair;

/**
 * Created by Jack on 19/02/2015.
 */
public class HexLoc extends Pair<Integer, Integer> {
    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     * true_x is the actual x value used to calculate distances, it avoids the squigly y-axis issue
    **/
    public final int true_x;

    public HexLoc(Integer first, Integer second) {
        super(first, second);

        true_x = first - (second / 2);
    }

    public boolean contains(HexLoc val) {
        boolean isWithinMin = val.first >= 0 && val.second >= 0;
        boolean isWithinMax = val.first < first && val.second < second;
        return isWithinMin && isWithinMax;
    }

    @Override
    public String toString() {
        return " x: " + first + " y:" + second + " true_x:" + true_x;
    }
}
