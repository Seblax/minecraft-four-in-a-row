package org.seblax.utils;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.seblax.Config;
import org.seblax.Data;
import org.seblax.animations.ArmorStandParticles;
import org.seblax.animations.ArmorStandRotator;
import org.seblax.team.TeamColor;
import org.seblax.utils.types.Coord;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**
 * Utility class for managing an ArmorStand associated with a team.
 * Handles its appearance, animations, and properties like color and head texture.
 */
public class ArmorStandUtil {
    private final ArmorStandParticles particles;
    private final ArmorStandRotator rotator;
    private final UUID armorStandUUID;
    private final String name;
    private final int teamID;

    private ArmorStand armorStand;
    private TeamColor currentColor;

    /**
     * Constructs an ArmorStandUtil instance and initializes the ArmorStand.
     *
     * @param coord Initial coordinates for the ArmorStand.
     * @param name  Name associated with the ArmorStand (team name).
     */
    public ArmorStandUtil(Coord coord, String name) {
        this.name = name;
        this.teamID = Integer.parseInt(this.name.split(" ")[1]);
        int colorID = Config.getColor(this.teamID);

        this.armorStand = (ArmorStand) Data.CURRENT_WORLD.spawnEntity(coord.toLocation(), EntityType.ARMOR_STAND);
        this.armorStandUUID = this.armorStand.getUniqueId();
        this.currentColor = TeamColor.of(colorID);

        this.particles = new ArmorStandParticles(coord, this.currentColor.getColor());
        this.rotator = new ArmorStandRotator(this.armorStandUUID);


        initializeArmorStand();
        updateArmorStandSkin();
    }

    /** @return the name associated with this ArmorStand. */
    public String getName() {
        return this.name;
    }

    /** @return the current color of the ArmorStand. */
    public TeamColor getCurrentColor() {
        return currentColor;
    }

    public ArmorStandParticles getArmorStandParticle() {
        return this.particles;
    }

    public ArmorStandRotator getArmorStandRotator() {
        return this.rotator;
    }

    public void ArmorStandTeleport(Location location){
        this.particles.ArmorStandTeleport(location);
    }



    /**
     * Sets the current color of the ArmorStand and updates related effects.
     *
     * @param currentColor The new color to set.
     */
    public void setCurrentColor(TeamColor currentColor) {
        this.particles.setColor(currentColor.getColor());
        this.currentColor = currentColor;
        updateArmorStandSkin();
    }

    /**
     * Fetches the actual ArmorStand entity from the game.
     *
     * @return The ArmorStand instance.
     */
    public ArmorStand getArmorStand() {
        ArmorStand res = (ArmorStand) Bukkit.getEntity(this.armorStandUUID);
        if( res != null){
            this.armorStand = res;
        }
        return this.armorStand;
    }

    /** @return the UUID of the ArmorStand. */
    public UUID getUUID() {
        return this.armorStandUUID;
    }

    /**
     * Initializes the ArmorStand with predefined properties.
     */
    private void initializeArmorStand() {
        this.armorStand.setVisible(false);
        this.armorStand.setCustomNameVisible(true);
        this.armorStand.addScoreboardTag("4x4");
        this.armorStand.addScoreboardTag(this.name.split(" ")[1]);
        this.armorStand.setGravity(false);
    }

    /**
     * Updates the ArmorStand's appearance, including its name and helmet.
     */
    public void updateArmorStandSkin() {
        this.armorStand.setCustomName(TeamColor.of(currentColor.getValue() + 1).getChatColor() + "-=" +
                currentColor.getChatColor() + "" + ChatColor.BOLD + this.name +
                TeamColor.of(currentColor.getValue() + 1).getChatColor() + "=-");

        String helmet = currentColor.getColorName() + Data.TEAM_HEAD_ITEM;
        setHelmet(helmet.toUpperCase().trim());

        Config.setColor(this.teamID, currentColor.getValue());
    }

    /**
     * Sets the helmet for the ArmorStand using a predefined material name.
     *
     * @param head The material name of the helmet.
     */
    public void setHelmet(String head) {
        ItemStack helmet = new ItemStack(Material.valueOf(head));
        setItemOnArmorStand(helmet);
    }

    /**
     * Sets a custom head texture on the ArmorStand using a URL.
     *
     * @param url The texture URL for the custom head.
     */
    public void setHead(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        PlayerProfile playerProfile = Bukkit.createPlayerProfile(new UUID(0, 0));
        try {
            URL skinUrl = new URL("https://textures.minecraft.net/texture/" + url);
            playerProfile.getTextures().setSkin(skinUrl);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to set head texture: " + e.getMessage());
            return;
        }

        skullMeta.setOwnerProfile(playerProfile);
        head.setItemMeta(skullMeta);
        setItemOnArmorStand(head);
    }

    /**
     * Helper method to set an item on the ArmorStand's head slot.
     *
     * @param item The ItemStack to set as the helmet.
     */
    private void setItemOnArmorStand(ItemStack item) {
        EntityEquipment equipment = getArmorStand().getEquipment();
        if (equipment != null) {
            equipment.clear();
            equipment.setHelmet(item);
        }
    }
}