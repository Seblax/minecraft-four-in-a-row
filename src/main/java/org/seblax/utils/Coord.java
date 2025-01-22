package org.seblax.utils;

import org.bukkit.Location;
import org.seblax.Data;

public class Coord extends Triple<Double>{
    public Coord(Double x, Double y, Double z){
        super(x,y,z);
    }

    public Coord(Integer x, Integer y, Integer z){
        super(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    public Location toLocation(){
        return  new Location(Data.CurrentWorld,x,y,z);
    }
}