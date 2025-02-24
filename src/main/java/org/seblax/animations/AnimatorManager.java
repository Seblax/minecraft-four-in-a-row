package org.seblax.animations;

import org.bukkit.scheduler.BukkitRunnable;
import org.seblax.Four;
import org.seblax.utils.ArmorStandUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * This class manages the animation of multiple Armor Stands.
 * It uses a set of `ArmorStandUtil` objects to manage the animations of each Armor Stand.
 * The class also has the ability to start a periodic animation task.
 */
public class AnimatorManager {
    // A set of ArmorStandUtil objects to manage the animations of multiple Armor Stands.
    private final Set<ArmorStandUtil> armorStandUtils = new HashSet<>();

    /**
     * Adds a new ArmorStandUtil to the set.
     *
     * @param armorStand The ArmorStandUtil object to be added to the set.
     */
    public void addArmorStand(ArmorStandUtil armorStand) {
        armorStandUtils.add(armorStand);  // Adds the ArmorStandUtil to the set.
    }

    /**
     * Starts a periodic task to update the animations of the Armor Stands.
     * Each Armor Stand in the set will have its particles and rotations updated.
     */
    public void startAnimation() {
        // Creates a task that runs periodically every tick (1/20 seconds).
        new BukkitRunnable() {
            @Override
            public void run() {
                // Iterates through all the ArmorStandUtils in the set.
                for (ArmorStandUtil as : armorStandUtils) {
                    // Calls the methods to show the particles and rotate the armor stand.
                    as.getArmorStandParticle().ArmorStandIdle();  // Calls the particle animation for the Armor Stand.
                    as.getArmorStandRotator().ArmorStandRotate();  // Calls the rotation animation for the Armor Stand.
                }
            }
        }.runTaskTimer(Four.getInstance(), 0L, 1L);  // Runs the task every 1 tick (20 ticks per second).
    }
}