package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class HexUtilsTest extends TestCase {
    HexMap<Object, HexTile<Object>> map;
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
        map = new HexMap<>(10,10);
        map.fill(new HexMap.TileFactory<Object, HexTile<Object>>() {
            @Override
            public HexTile<Object> getTile(HexLoc loc) {
                return new HexTile<>(loc);
            }
        });
        skipping.clear();
        hexFilter = new HexFilter<Object>() {
            @Override
            public boolean accept(HexTile<Object> val) {
                return !skipping.contains(val);
            }
        };

    }
    public void testGetShortestPathAvailable() throws Exception {

        for(int i = 0 ; i < 4 ; i ++){
            skipping.add(map.get(i,1));
        }
        //So the filter basically limits where you can go while calculating shortest path
        final HexTile<Object> start = map.get(0, 0);
        final HexTile<Object> finish = map.get(0, 2);
        LinkedList<HexTile<Object>> path = HexUtils.getShortestPath(start, finish, hexFilter, defaultWeight);
        assertNotNull(path);
        assertEquals(11, path.size());
        assertEquals(start,path.getFirst());
        HexTile<Object> last = path.getLast();
        HexTile<Object> first = path.getFirst();
        assertEquals(start,first);
        assertEquals(finish,last);
    }
    public void testConvolutedPath(){
        skipping.add(map.get(4,4));
        skipping.add(map.get(3,3));
        skipping.add(map.get(3,2));
        skipping.add(map.get(4,2));
        skipping.add(map.get(5,2));
        skipping.add(map.get(6,2));
        skipping.add(map.get(7,2));
        skipping.add(map.get(7,3));
        skipping.add(map.get(8,3));
        skipping.add(map.get(8,4));
        skipping.add(map.get(7,5));
        skipping.add(map.get(6,5));
        skipping.add(map.get(6,4));
        final HexTile<Object> start = map.get(1, 1);
        final HexTile<Object> finish = map.get(7, 4);
        HexTile<?>[] expected = {start,
                map.get(2,2),map.get(2,3),map.get(3,4),map.get(3,5),map.get(4,5),map.get(5,4),map.get(5,3),map.get(6,3),
                              finish};
        HexPath<Object> path = HexUtils.getShortestPath(start, finish, hexFilter, defaultWeight);
        assertEquals(expected.length,path.size());
        assertEquals(expected.length-1,path.getPathCost());
        final Iterator<HexTile<Object>> iter = path.iterator();
        for(HexTile<?> tile : expected ){
            assertEquals(tile, iter.next());
        }
        final HashSet<HexTile<?>> bigWeightSet = new HashSet<>();
        bigWeightSet.addAll(Arrays.asList(expected));
        HexCost<Object> zeroWeight = new HexCost<Object>() {
            @Override
            public Integer getCost(HexTile<Object> from, HexTile<Object> to) {
                boolean lowRow = from.loc().second<2||to.loc().second<2;
                boolean bigWeight = bigWeightSet.contains(from)||bigWeightSet.contains(to)||lowRow;
                return bigWeight?10:0;
            }
        };
        path = HexUtils.getShortestPath(start, finish, hexFilter, zeroWeight);
        assertEquals(13,path.size());
        assertEquals(50,path.getPathCost());
        HexTile<Object> last = path.getLast();
        HexTile<Object> first = path.getFirst();
        assertEquals(start,first);
        assertEquals(finish,last);

    }
    public void testGetShortestPathNoneAvailable() throws Exception {
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