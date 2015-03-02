package com.nuggets.slaphappy.peakoilproject.util;

import android.util.Log;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class GrabBagTest extends TestCase {

    private GrabBag<Integer> gb;

    @Override
    protected void setUp(){
        List<Integer> contents = new LinkedList<>();
        contents.addAll(Arrays.asList(0,1,2,3,4,5,6,7,8,9));

        gb = new GrabBag<>(contents);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAdd() throws Exception {
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0 ; i < 10 ; i ++){
            gb.add(i);
            set.add(i);
        }
        for(int i = 0 ; i < 10 ; i ++){
            Integer grab = gb.grab();
            Log.e("##", grab.toString());
            set.remove(grab);
        }
        assertTrue(set.isEmpty());
    }


    public void testGrab() throws Exception {

    }
}