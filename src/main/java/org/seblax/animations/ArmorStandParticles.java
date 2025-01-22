package org.seblax.animations;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.seblax.Data;
import org.seblax.utils.Coord;

public class ArmorStandParticles {

    private Coord coord;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public ArmorStandParticles(Coord coord, Color color){
        this.coord = coord;
        this.color = color;
    }

    public void ArmorStandSelected(){

    }

    public void ArmorStandTeleport(double x, double y, double z){
        ArmorStandTeleport(new Coord(x,y,z));
    }

    public void ArmorStandTeleport(Coord coord){
        Data.CurrentWorld.spawnParticle(Particle.DUST, coord.toLocation().add(0,1.7,0), 100,1,1,1, new Particle.DustOptions(this.color, (float) (1 + 5*Math.random())));
    }

    public void ArmorStandIdle(){
        Data.CurrentWorld.spawnParticle(Particle.DUST, coord.toLocation().add(0,1.7,0), 1,0.2,0.2,0.2, new Particle.DustOptions(this.color, (float) (1 + 1*Math.random())));
    }
}