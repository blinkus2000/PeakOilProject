package com.nuggets.slaphappy.peakoilproject.util;

import java.util.EnumMap;

/**
 * Created by Jack on 20/02/2015.
 */
public enum TriStateBoolean {
    TRUE(true), FALSE(false), TRI(null);
    final Boolean val;
    final int l;

    TriStateBoolean(Boolean bool) {
        val = bool;

        l = ordinal();
    }

    public TriStateBoolean not() {
        switch (this) {
            case TRUE:
                return FALSE;
            case FALSE:
                return TRUE;
            case TRI:
                return TRI;
            default:
                return this;
        }
    }

    public TriStateBoolean or(TriStateBoolean other) {
        return TruthTable.or.get(this).get(other);
    }

    public TriStateBoolean and(TriStateBoolean other) {
        return TruthTable.and.get(this).get(other);
    }

    public static TriStateBoolean get(Boolean b) {
        try {
            return b ? TRUE : FALSE;
        } catch (Exception e) {
            return TRI;
        }
    }
    public static class TriStateTable<T> extends EnumMap<TriStateBoolean,EnumMap<TriStateBoolean,T>>{
        public TriStateTable() {
            super(TriStateBoolean.class);
            for(TriStateBoolean val : TriStateBoolean.values()){
                this.put(val,new EnumMap<TriStateBoolean, T>(TriStateBoolean.class));
            }
        }

    }
    public static <T> TriStateTable<T> getTable(){
        return new TriStateTable<>();
    }
    private static class TruthTable {
        public static final TriStateTable<TriStateBoolean> or = buildOrTable();


        public static final TriStateTable<TriStateBoolean> and = buildAndTable();

        private static TriStateTable<TriStateBoolean> buildAndTable() {
            TriStateTable<TriStateBoolean> t = getTable();
            t.get(TRUE).put(TRUE, TRUE);
            t.get(TRUE).put(FALSE, FALSE);
            t.get(TRUE).put(TRI, TRI);
            t.get(FALSE).put(TRUE, FALSE);
            t.get(FALSE).put(FALSE, FALSE);
            t.get(FALSE).put(TRI, TRI);
            t.get(TRI).put(TRUE, TRI);
            t.get(TRI).put(FALSE, TRI);
            t.get(TRI).put(TRI, TRI);
            return t;
        }

        private static TriStateTable<TriStateBoolean> buildOrTable() {
            TriStateTable<TriStateBoolean> t = getTable();
            t.get(TRUE).put(TRUE, TRUE);
            t.get(TRUE).put(FALSE, TRUE);
            t.get(TRUE).put(TRI, TRI);
            t.get(FALSE).put(TRUE, TRUE);
            t.get(FALSE).put(FALSE, FALSE);
            t.get(FALSE).put(TRI, TRI);
            t.get(TRI).put(TRUE, TRI);
            t.get(TRI).put(FALSE, TRI);
            t.get(TRI).put(TRI, TRI);
            return t;
        }




    }
}
