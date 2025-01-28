package org.seblax.utils;

import org.bukkit.Location;
import org.seblax.Data;

public class Coord extends Triple<Double>{
    float yaw = 0f;

    public Coord(Double x, Double y, Double z){
        super(x,y,z);
    }

    public Coord(Double x, Double y, Double z, float yaw){
        super(x,y,z);
        this.yaw = yaw;
    }

    public Coord(Integer x, Integer y, Integer z){
        super(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    public Location toLocation(){
        return  new Location(Data.CURRENT_WORLD,x,y,z,yaw,0);
    }

    @Override
    public String toString(){
        return "[" + x + ", "+ y + ", "+ z + ", " + "]";
    }
}