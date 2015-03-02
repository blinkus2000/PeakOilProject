package com.nuggets.slaphappy.peakoilproject.util;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Jack on 18/02/2015.
 */
public class Grid <T extends Location> extends ArrayList<ArrayList<T>>{
    protected final int x,y;
    public Grid(int dim){
        this(dim,dim);
    }
    public Grid(int x, int y ){
        super(y);
        this.x = x;
        this.y = y;
        for(int i = 0 ; i < y ; i ++){
            add(new ArrayList<T>(x));
        }
    }
    public void add( T val){
        int x = val.loc().first;
        int y = val.loc().second;
        if(x>=this.x){
            throw new IndexOutOfBoundsException(Integer.toString(x));
        }
        ArrayList<T> ts = get(y);
        if (ts!=null) {
            ts.add(x, val);
        }else{
            throw new IndexOutOfBoundsException(Integer.toString(y));
        }
    }
    public T get( int x,int y){
        if(x>=this.x){
            throw new IndexOutOfBoundsException(Integer.toString(x));
        }
        ArrayList<T> ts = get(y);
        if (ts!=null) {
            return ts.get(x);
        } else {
            throw new IndexOutOfBoundsException(Integer.toString(y));
        }
    }
    public boolean containsVal(T val){
        for(ArrayList<T> list : this){
            if(list.contains(val)){
                return true;
            }
        }
        return false;
    }
    public T get(Pair<Integer,Integer> p){
        return get(p.first,p.second);
    }
}
