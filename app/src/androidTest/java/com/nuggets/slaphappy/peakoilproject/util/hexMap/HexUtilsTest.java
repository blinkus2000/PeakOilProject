package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.LinkedList;

public class HexUtilsTest extends TestCase {
    HexMap<Object> map;
    final HashSet<HexTile> skipping = new HashSet<>();
    private final HexCost<Object> defaultWeight = new HexCost<Object>() {
        @Override
        public Integer getCost(HexTile<Object> from, HexTile<Object> to) {
            return 1;
        }
    };
    private HexFilter<Object> hexFilter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        map = new HexMap(10,10);
        map.fill();
        skipping.clear();
        hexFilter = new HexFilter<Object>() {
            @Override
            public boolean accept(HexTile<Object> val) {
                return !skipping.contains(val);
            }
        };

    }
//TODO: need to test when no path is available.
    public void testGetShortestPathAvailable() throws Exception {

        for(int i = 0 ; i < 4 ; i ++){
            skipping.add(map.get(i,1));
        }
        //So the filter basically limits where you can go while calculating shortest path
        HexFilter<Object> filter1 = new HexFilter<Object>() {
            @Override
            public boolean accept(HexTile<Object> val) {
                return !skipping.contains(val);
            }
        };
        final HexTile<Object> start = map.get(0, 0);
        final HexTile<Object> finish = map.get(0, 2);
        LinkedList<HexTile<Object>> path = HexUtils.getShortestPath(start, finish, filter1, defaultWeight);
        assertNotNull(path);
        assertEquals(11, path.size());
        assertEquals(start,path.getFirst());
        HexTile<Object> last = path.getLast();
        HexTile<Object> first = path.getFirst();
        assertEquals(start,first);
        assertEquals(finish,last);
    }

    public void testGetShortestPathNoneAvailable() throws Exception {
        final HashSet<HexTile> skipping = new HashSet<>();
        for(int i = 0 ; i < 10 ; i ++){
            skipping.add(map.get(i,1));
        }
        //So the filter basically limits where you can go while calculating shortest path

        final HexTile<Object> start = map.get(0, 0);
        final HexTile<Object> finish = map.get(0, 2);
        LinkedList<HexTile<Object>> path = HexUtils.getShortestPath(start, finish, hexFilter, defaultWeight);
        assertNull(path);

    }
}