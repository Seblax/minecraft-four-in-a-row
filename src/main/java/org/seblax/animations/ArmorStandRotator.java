package org.seblax.animations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import java.util.UUID;

/**
 * This class handles the rotation of a specific Armor Stand in the Minecraft world.
 * It uses a UUID to uniquely identify the Armor Stand that will be rotated.
 */
public class ArmorStandRotator {
    private final UUID uuid;  // The unique identifier for the Armor Stand.
    private final double rotationSpeed = 2.5f;  // The rotation speed of the Armor Stand (in degrees per tick).

    /**
     * Constructor for the class that receives the UUID of an Armor Stand.
     *
     * @param armorStand The UUID of the Armor Stand to be rotated.
     */
    public ArmorStandRotator(UUID armorStand) {
        this.uuid = armorStand;
    }

    /**
     * This method rotates the Armor Stand at a defined speed.
     *
     * The Armor Stand rotates around the Y-axis, and its rotation increases each time this method is executed.
     */
    public void ArmorStandRotate() {
        // Get the Armor Stand from the world using the UUID.
        ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(this.uuid);

        // Check if the Armor Stand is valid and alive. If not, stop the rotation.
        if (armorStand == null ||
                armorStand.isDead() ||
                !armorStand.isValid() ||
                !armorStand.getScoreboardTags().contains("4x4"))
            return;

        // Get the current location of the Armor Stand.
        Location location = armorStand.getLocation();

        // Calculate the new rotation angle (Yaw) by adding the rotation speed to the current angle.
        float newYaw = (location.getYaw() + (float) rotationSpeed) % 360;

        // Set the new rotation for the Armor Stand.
        location.setYaw(newYaw);

        // Teleport the Armor Stand to the same location but with the updated rotation.
        armorStand.teleport(location);
    }
}