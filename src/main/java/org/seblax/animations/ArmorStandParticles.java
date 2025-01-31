package org.seblax.animations;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.seblax.Data;
import org.seblax.utils.types.Coord;
import org.seblax.Four;

/**
 * This class handles particle animations related to armor stands.
 * It allows for creating different animations such as selected, idle, and teleporting particles.
 */
public class ArmorStandParticles {

    private final Coord coord;  // The coordinates where the particle effects will be displayed
    private Color color;        // The color of the particles

    /**
     * Setter for the particle color.
     * @param color The color to set for the particles.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Constructor to initialize the particle effect with specific coordinates and color.
     * @param coord The coordinates for the particle effect.
     * @param color The color of the particle effect.
     */
    public ArmorStandParticles(Coord coord, Color color) {
        this.coord = coord;
        this.color = color;
    }

    /**
     * Creates a particle animation when an armor stand is selected.
     * The animation forms a spiral shape, with particles that gradually expand in size.
     * The particle animation will stop once the desired size is reached.
     */
    public void ArmorStandSelected() {
        new BukkitRunnable() {
            double t = 0;               // The time variable used to control the animation
            final double step = 0.1;    // The step increment for the t variable
            final double size = 2;      // The maximum size of the spiral
            final Location loc = coord.toLocation().add(0, 0.1, 0);  // The starting location, slightly raised on the Y-axis

            /**
             * The method that runs the particle animation in a loop.
             * It spawns particles at different positions forming a spiral pattern.
             */
            public void run() {
                t += step;  // Increase time variable
                for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 32) {
                    // Calculate the X, Y, and Z position of the particle based on the spiral equation
                    double x = t * Math.cos(theta);
                    double y = 0.1 * Math.sin(t * 5);  // Vertical oscillation effect
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);  // Move the location to the new position

                    // Spawn the particle at the calculated location with a specific color and size
                    Data.CURRENT_WORLD
                            .spawnParticle(
                                    Particle.DUST,  // Particle type
                                    loc,  // Location where the particle will appear
                                    1,  // Number of particles
                                    new Particle.DustOptions(color, (float) (Math.pow(2, t - 1)))  // Particle appearance options
                            );

                    loc.subtract(x, y, z);  // Reset the location to avoid cumulative shifts
                }

                // Cancel the task once the maximum size is reached
                if (t > size) {
                    this.cancel();
                }
            }

        }.runTaskTimer(Four.getInstance(), 0, 1);  // Run the task at regular intervals (every tick)
    }

    /**
     * Creates a teleportation particle effect at the given location.
     * @param l The location where the teleportation particle effect will occur.
     */
    public void ArmorStandTeleport(Location l) {
        ArmorStandTeleport(new Coord(l.getX(), l.getY(), l.getZ()));
    }

    /**
     * Creates a teleportation particle effect at the given coordinates.
     * @param coord The coordinates where the teleportation effect will occur.
     */
    public void ArmorStandTeleport(Coord coord) {
        // Spawn particles at the given location with random sizes
        Data.CURRENT_WORLD.spawnParticle(
                Particle.DUST,  // Particle type
                coord.toLocation().add(0, 1.7, 0),  // Adjust location vertically for visual clarity
                100,  // Number of particles to spawn
                1, 1, 1,  // Spread of the particles
                new Particle.DustOptions(this.color, (float) (1 + 5 * Math.random()))  // Random size for particles
        );
    }

    /**
     * Creates an idle particle effect, where particles are continuously spawned with small variations.
     * This can be used for a subtle, ongoing visual effect around an armor stand.
     */
    public void ArmorStandIdle() {
        // Spawn idle particles at the specified coordinates with random size variations
        Data.CURRENT_WORLD.spawnParticle(
                Particle.DUST,  // Particle type
                coord.toLocation().add(0, 1.7, 0),  // Adjusted location to match the armor stand
                1,  // Number of particles to spawn
                0.2, 0.2, 0.2,  // Particle spread
                new Particle.DustOptions(this.color, (float) (1 + 1 * Math.random()))  // Random size for particles
        );
    }
}


//package org.seblax.animations;
//
//import org.bukkit.Color;
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.seblax.Data;
//import org.seblax.utils.types.Coord;
//import org.seblax.Four;
//
//public class ArmorStandParticles {
//
//    private final Coord coord;
//    private Color color;
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    public ArmorStandParticles(Coord coord, Color color) {
//        this.coord = coord;
//        this.color = color;
//    }
//
//    public void ArmorStandSelected() {
//        new BukkitRunnable() {
//            double t = 0;
//            final double step = 0.1;
//            final double size = 2;
//            final Location loc = coord.toLocation().add(0,0.1,0);
//
//            public void run() {
//                t += step;
//                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
//                    double x = t * Math.cos(theta);
//                    double y = 0.1 * Math.sin(t*5);
//                    double z = t * Math.sin(theta);
//                    loc.add(x, y, z);
//
//                    Data.CURRENT_WORLD
//                            .spawnParticle(
//                                    Particle.DUST,
//                                    loc,
//                                    1,
//                                    new Particle
//                                            .DustOptions(color,
//                                            (float) (Math.pow(2,t-1))));
//
//                    loc.subtract(x, y, z);
//                    theta = theta + Math.PI / 64;
//                }
//                if (t > size) {
//                    this.cancel();
//                }
//            }
//
//        }.runTaskTimer(Four.getInstance(), 0, 1);
//    }
//
//    public void ArmorStandTeleport(Location l) {
//        ArmorStandTeleport(new Coord(l.getX(), l.getY(), l.getZ()));
//    }
//
//    public void ArmorStandTeleport(Coord coord) {
//        Data.CURRENT_WORLD.spawnParticle(Particle.DUST, coord.toLocation().add(0, 1.7, 0), 100, 1, 1, 1, new Particle.DustOptions(this.color, (float) (1 + 5 * Math.random())));
//    }
//
//    public void ArmorStandIdle() {
//        Data.CURRENT_WORLD.spawnParticle(Particle.DUST, coord.toLocation().add(0, 1.7, 0), 1, 0.2, 0.2, 0.2, new Particle.DustOptions(this.color, (float) (1 + 1 * Math.random())));
//    }
//}