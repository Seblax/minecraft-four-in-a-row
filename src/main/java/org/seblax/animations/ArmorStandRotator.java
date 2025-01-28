package org.seblax.animations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.seblax.four;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ArmorStandRotator {
    private final UUID uuid;
    private final double rotationSpeed = 2.5f;

    public ArmorStandRotator(UUID armorStand) {
        this.uuid = armorStand;
    }

    public void ArmorStandRotate() {
        ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(this.uuid);

        if (armorStand == null ||
                armorStand.isDead() ||
                !armorStand.isValid() ||
                !armorStand.getScoreboardTags().contains("4x4"))
            return;

        Location location = armorStand.getLocation();
        float newYaw = (location.getYaw() + (float) rotationSpeed) % 360;
        location.setYaw(newYaw);
        armorStand.teleport(location);
    }
}

