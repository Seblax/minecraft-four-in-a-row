package org.seblax.animations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.seblax.four;
import org.seblax.utils.ArmorStandUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AnimatorManager {
    private final Set<ArmorStandUtil> armorStandUtils = new HashSet<>();

    public void addArmorStand(ArmorStandUtil armorStand) {
        armorStandUtils.add(armorStand);
    }

    public void startAnimation() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ArmorStandUtil as : armorStandUtils) {
                    as.particles.ArmorStandIdle();
                    as.rotator.ArmorStandRotate();
                }
            }
        }.runTaskTimer(four.getInstance(), 0L, 1L); // Ejecutar cada tick (1L = 1 tick)
    }
}
