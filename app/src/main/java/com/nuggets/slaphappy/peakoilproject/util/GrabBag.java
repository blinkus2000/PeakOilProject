package com.nuggets.slaphappy.peakoilproject.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 18/02/2015.
 */
public class GrabBag <T>{
    final private List<T> contents;
    final private Random gen = new Random();

    public GrabBag(List<T> contents) {
        this.contents = contents;
    }
    public GrabBag(){
        this(new LinkedList<T>());
    }
    public final synchronized void add(T val){
      if(!contents.contains(val)){
        contents.add(val);
      }
    }
    public final synchronized T grab(){
        final int nextInt = gen.nextInt(contents.size());
        T returnVal =contents.get(nextInt);
        contents.remove(returnVal);
        return returnVal;
    }
}
