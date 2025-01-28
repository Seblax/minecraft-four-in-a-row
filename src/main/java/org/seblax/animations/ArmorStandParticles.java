package org.seblax.animations;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.seblax.Data;
import org.seblax.utils.Coord;
import org.seblax.four;

public class ArmorStandParticles {

    private final Coord coord;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public ArmorStandParticles(Coord coord, Color color) {
        this.coord = coord;
        this.color = color;
    }

    public void ArmorStandSelected() {
        new BukkitRunnable() {
            double t = 0;
            final double step = 0.1;
            final double size = 2;
            final Location loc = coord.toLocation().add(0,1.7,0);

            public void run() {
                t += step;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 0.1 * Math.sin(t*5);
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);

                    Data.CURRENT_WORLD
                            .spawnParticle(
                                    Particle.DUST,
                                    loc,
                                    1,
                                    new Particle
                                            .DustOptions(color,
                                            (float) (Math.pow(2,t-1))));

                    loc.subtract(x, y, z);
                    theta = theta + Math.PI / 64;
                }
                if (t > size) {
                    this.cancel();
                }
            }

        }.runTaskTimer(four.getInstance(), 0, 1);
    }

    public void ArmorStandTeleport(Location l) {
        ArmorStandTeleport(new Coord(l.getX(), l.getY(), l.getZ()));
    }

    public void ArmorStandTeleport(Coord coord) {
        Data.CURRENT_WORLD.spawnParticle(Particle.DUST, coord.toLocation().add(0, 1.7, 0), 100, 1, 1, 1, new Particle.DustOptions(this.color, (float) (1 + 5 * Math.random())));
    }

    public void ArmorStandIdle() {
        Data.CURRENT_WORLD.spawnParticle(Particle.DUST, coord.toLocation().add(0, 1.7, 0), 1, 0.2, 0.2, 0.2, new Particle.DustOptions(this.color, (float) (1 + 1 * Math.random())));
    }
}