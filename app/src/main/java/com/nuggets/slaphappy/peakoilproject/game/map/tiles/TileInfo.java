package com.nuggets.slaphappy.peakoilproject.game.map.tiles;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.nuggets.slaphappy.peakoilproject.game.player.Player;
import com.nuggets.slaphappy.peakoilproject.util.Fraction;

/**
 * com.nuggets.slaphappy.peakoilproject.game.map
 * PeakOilProject
 * Created by Jack on 02/03/2015.
 */
public class TileInfo {
    private Player owner = Player.UNASSIGNED;
    private OilReserves oilReserves;
    private MilitaryPresence militaryPresence;
    private boolean prospected = false;
    private TileType type = null;
    private String name;
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    private TileInfo(@NonNull TileType type){
        this.type = type;
        name = type.getNextName();
    }



    public static enum TileType {
        Plains(new Fraction(1,2), Color.YELLOW,36),
        Forest(new Fraction(1,3),Color.GREEN,12),
        Mountains(new Fraction(1,4),Color.GRAY,24),
        Sea(new Fraction(1,8),Color.BLUE,96,false),
        Polar(new Fraction(1,16),Color.WHITE,24),
        Fracked(new Fraction(0),Color.BLACK,24);
        private final Fraction productionRatio;
        private final int color;
        private final int count;
        private final boolean isLand;
        TileType(Fraction productionRatio,int color,int count){
            this(productionRatio,color,count,true);
        }
        TileType(Fraction productionRatio,int color,int count, boolean isLand){
            this.productionRatio = productionRatio;
            this.color = color;
            this.count = count;
            this.isLand = isLand;

        }
        public String getNextName(){
            return null;
        }
        public TileInfo get(){
            return new TileInfo(this);
        }

        public int initCap() {
            return !isLand||this==Fracked?0:1;
        }
    }
}
