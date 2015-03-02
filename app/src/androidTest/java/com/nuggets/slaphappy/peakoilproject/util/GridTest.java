package com.nuggets.slaphappy.peakoilproject.util;

import android.util.Pair;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;


public class GridTest extends TestCase {
    private static class TestVal extends Pair<Integer,Integer> implements Location{
        public TestVal(Integer first, Integer second) {
            super(first, second);
        }

        @Override
        public Pair<Integer, Integer> loc() {
            return this;
        }
    }
    Grid<TestVal> underTest;
    static int dim;
    static HashMap<Integer,ArrayList<TestVal>> data= setUpBeforeClass();


    public static HashMap<Integer,ArrayList<TestVal>> setUpBeforeClass(){
        data = new HashMap<>();
        dim = 10;
        for(int i = 0 ; i < dim ; i ++){
            ArrayList<TestVal> list = new ArrayList<>(dim);
            data.put(i,list);
            for(int j = 0 ; j < dim ; j++){
                list.add(new TestVal(i,j));
            }
        }
        return data;
    }
    @Override
    public void setUp() throws Exception {
        underTest = new Grid<>(dim);
    }


    public void testAdd() throws Exception {
        for(int i = 0 ; i < dim ; i ++){
            for(int j = 0 ; j < dim ; j++){
                underTest.add(data.get(i).get(j));
            }
        }
        assertEquals(data.get(0).get(0),underTest.get(0,0));
        assertEquals(data.get(9).get(9),underTest.get(9,9));
        assertEquals(data.get(0).get(9),underTest.get(0,9));
        assertEquals(data.get(9).get(0),underTest.get(9,0));
        assertEquals(data.get(5).get(5),underTest.get(5,5));

        assertNotSame(data.get(0).get(5), underTest.get(5, 0));
        assertNotSame(data.get(0).get(9), underTest.get(9, 0));
        assertNotSame(data.get(9).get(0), underTest.get(0, 9));


    }


    public void testBadAdd() throws Exception {
        testBadAdd(-1, 0);
        testBadAdd(0, -1);
        testBadAdd(dim,dim -1);
        testBadAdd(dim-1, dim);

    }

    private void testBadAdd(int first, int second) {
        TestVal val = new TestVal(first, second);
        try {
            underTest.add(val);
            fail();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    private void testBadGet(int first, int second) {
        TestVal val = new TestVal(first, second);
        try {
            underTest.get(val);
            fail();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void testBadGet() throws Exception {
        testBadGet(-1, 0);
        testBadGet(0, -1);
        testBadGet(dim,dim -1);
        testBadGet(dim-1, dim);
    }
}