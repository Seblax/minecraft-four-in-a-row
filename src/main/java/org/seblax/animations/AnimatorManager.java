package org.seblax.animations;

import org.bukkit.scheduler.BukkitRunnable;
import org.seblax.four;
import org.seblax.utils.ArmorStandUtil;

import java.util.HashSet;
import java.util.Set;

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
                    as.getArmorStandParticle().ArmorStandIdle();
                    as.getArmorStandRotator().ArmorStandRotate();
                }
            }
        }.runTaskTimer(four.getInstance(), 0L, 1L);
    }
}
