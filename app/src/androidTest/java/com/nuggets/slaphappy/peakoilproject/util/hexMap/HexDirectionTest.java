package com.nuggets.slaphappy.peakoilproject.util.hexMap;


import junit.framework.TestCase;

import java.util.EnumMap;
import java.util.List;

import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.Center;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.E;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.NE;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.NW;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.SE;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.SW;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.W;
import static com.nuggets.slaphappy.peakoilproject.util.hexMap.HexDirection.values;

public class HexDirectionTest extends TestCase {
    final HexLoc[][] l;
    final int maxX = 10;
    final int maxY = 10;
    public HexDirectionTest() {
        super();
        l = new HexLoc[maxX][maxY];
        for(int i = 0 ; i < maxX ; i ++){
            for(int j = 0 ; j < maxY ; j++){
                l[i][j] = new HexLoc(i,j);
            }
        }
    }

    public void testTranslate() throws Exception {
        EnumMap<HexDirection,HexLoc> m = HexDirection.getMap();
        HexLoc start = new HexLoc(0,0);
        m.put(W,new HexLoc(-1,0));
        m.put(E,new HexLoc(1, 0));
        m.put(NW,new HexLoc(-1, 1));
        m.put(NE,new HexLoc(0, 1));
        m.put(SW,new HexLoc(-1, -1));
        m.put(SE,new HexLoc(0, -1));
        m.put(Center,start);
        doAssertsForTranslate(m, start);

        m = HexDirection.getMap();
        start = new HexLoc(1,1);
        m.put(W,new HexLoc(0,1));
        m.put(E,new HexLoc(2,1));
        m.put(NW,new HexLoc(1,2));
        m.put(NE,new HexLoc(2,2));
        m.put(SW,new HexLoc(1,0));
        m.put(SE,new HexLoc(2,0));
        m.put(Center, start);
        doAssertsForTranslate(m, start);

        m = HexDirection.getMap();
        start = new HexLoc(1,2);
        m.put(W,new HexLoc(0,2));
        m.put(E,new HexLoc(2,2));
        m.put(NW,new HexLoc(0,3));
        m.put(NE,new HexLoc(1,3));
        m.put(SW,new HexLoc(0,1));
        m.put(SE,new HexLoc(1,1));
        m.put(Center, start);
        doAssertsForTranslate(m, start);


    }

    private void doAssertsForTranslate(EnumMap<HexDirection, HexLoc> m, HexLoc start) {
        for(HexDirection dir : values()){
            HexLoc translate = dir.translate(start);
            assertEquals(dir.name()+translate.first+":"+translate.second,m.get(dir), translate);
        }
    }
    public void testOpposite() throws Exception{
        assertEquals(SE,NW.opposite());
        assertEquals(NW,SE.opposite());
        assertEquals(SW,NE.opposite());
        assertEquals(NE,SW.opposite());
        assertEquals(E,W.opposite());
        assertEquals(W,E.opposite());
        assertEquals(Center,Center.opposite());
    }
    public void testRotate() throws Exception{
        assertEquals(SE,NW.rotate(3));
        assertEquals(SE,NW.rotate(-3));

        assertEquals(NW,SE.rotate(3));
        assertEquals(NW,SE.rotate(-3));

        assertEquals(SW,NE.rotate(3));
        assertEquals(SW,NE.rotate(-3));

        assertEquals(NE,SW.rotate(3));
        assertEquals(NE,SW.rotate(-3));

        assertEquals(E,W.rotate(3));
        assertEquals(E,W.rotate(-3));

        assertEquals(W,E.rotate(3));
        assertEquals(W,E.rotate(-3));

        assertEquals(Center,Center.rotate(10));
        assertEquals(Center,Center.rotate(-10));
        assertEquals(Center,Center.rotate(-1));

        for(HexDirection d : values()){
            assertEquals(d,d.rotate(6));
            assertEquals(d,d.rotate(-6));
        }

    }
    public void testBestGuess() throws Exception{
        List<HexDirection> result = HexUtils.bestGuess(new HexLoc(0, 0), new HexLoc(1, 0), new HexLoc(4, 4));
        assertFalse(result.isEmpty());
        assertEquals(2,result.size());

        result = HexUtils.bestGuess(new HexLoc(0, 0), new HexLoc(2, 1), new HexLoc(4, 4));
        assertFalse(result.isEmpty());
        assertEquals(2,result.size());
        assertEquals(NE,result.get(0));

        result = HexUtils.bestGuess(new HexLoc(0, 0), new HexLoc(0, 1), new HexLoc(4, 4));
        assertFalse(result.isEmpty());
        assertEquals(2,result.size());
        assertEquals(NE,result.get(0));

        result = HexUtils.bestGuess(new HexLoc(0, 0), new HexLoc(0, 3), new HexLoc(4, 4));
        assertFalse(result.isEmpty());
        assertEquals(2,result.size());
        assertEquals(NE,result.get(0));

        result = HexUtils.bestGuess(new HexLoc(0, 0), new HexLoc(1, 3), new HexLoc(4, 4));
        assertFalse(result.isEmpty());
        assertEquals(2,result.size());


        result = HexUtils.bestGuess(new HexLoc(1, 0), new HexLoc(1, 3), new HexLoc(4, 4));
        assertFalse(result.isEmpty());
        assertEquals(4,result.size());


    }
    public void testHexDistance() throws Exception{
        int count = 30;
        for (int i = -10 ; i < 10 ; i ++) {
            for (int j = -10 ; j < 10 ; j++) {
                checkAllDirections(count, new HexLoc(i,j));
            }
        }
        doAssert(0,l[0][0],l[0][0]);
        doAssert(2,l[0][0],l[1][1]);
        doAssert(4,l[0][0],l[3][2]);
        doAssert(10,l[9][0],l[1][5]);
        doAssert(4,l[4][0],l[1][2]);


    }

    private void checkAllDirections(int count, HexLoc startLoc) {
        for(HexDirection dir : HexDirection.dvalues){
            checkDiagDirection(NE, count, startLoc);
        }
    }

    private void checkDiagDirection(HexDirection dir, int count, HexLoc startLoc) {
        for(int i = 0 ; i < count ; i ++){

            HexLoc endLoc = startLoc;
            for(int j = 0 ; j<i;j++){
                endLoc = dir.translate(endLoc);
            }
            doAssert(i, startLoc, endLoc);
        }
    }

    private void doAssert(int expected, HexLoc startLoc, HexLoc endLoc) {
        assertEquals("distance from "+startLoc+" to "+endLoc+" not "+ expected, expected, HexUtils.hexDistance(startLoc, endLoc));
    }

}