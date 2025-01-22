package org.seblax.utils;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.seblax.Data;
import org.seblax.animations.ArmorStandParticles;
import org.seblax.animations.ArmorStandRotator;
import org.seblax.team.TeamColor;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class ArmorStandUtil{
    public static String path = "playerscolors.yml";

    public ArmorStandParticles particles;
    public ArmorStandRotator rotator;

    UUID armorStandUUID;
    ArmorStand armorStand;
    TeamColor currentColor;
    public String name;

    public void CurrentColor(TeamColor currentColor) {
        this.particles.setColor(currentColor.getColor());
        this.currentColor = currentColor;
    }

    public TeamColor CurrentColor() {
        return currentColor;
    }

    public ArmorStand GetArmorStand() {
        this.armorStand = (ArmorStand) Bukkit.getEntity(this.armorStandUUID);
        return this.armorStand;
    }

    public UUID GetUUID(){
        return  this.armorStandUUID;
    }

    public ArmorStandUtil(Coord coord, String name){
        int colorID = (int)new FileData(path).Get(name.split(" ")[1]);
        this.armorStand = (ArmorStand) Data.CurrentWorld.spawnEntity(coord.toLocation(), EntityType.ARMOR_STAND);
        this.armorStandUUID = this.armorStand.getUniqueId();
        this.currentColor = TeamColor.of(colorID);
        this.name = name;

        this.particles = new ArmorStandParticles(coord, this.currentColor.getColor());
        this.rotator = new ArmorStandRotator(this.armorStandUUID);

        InitializeArmorStand();
        SetAmorStandSkin(this.armorStand);
    }

    public void InitializeArmorStand(){
        this.armorStand.setVisible(false);
        this.armorStand.setCustomNameVisible(true);
        this.armorStand.addScoreboardTag("4x4");
        this.armorStand.addScoreboardTag(this.name.split(" ")[1]);
        this.armorStand.setGravity(false);
    }

    public void SetAmorStandSkin(ArmorStand armorstand){
        armorstand.setCustomName(currentColor.getChatColor() +""+ ChatColor.BOLD + this.name);

        String helmet = currentColor.getColorName() + Data.Heads;

        SetHelmet(helmet.toUpperCase().trim(), armorStand);

        new FileData(path).Set(this.name.split(" ")[1],currentColor.getValue());
    }

    public void SetAmorStandSkin(){
        SetAmorStandSkin(GetArmorStand());
    }

    public void SetHelmet(String head, ArmorStand armorStand){
        ItemStack helmet = new ItemStack(Material.valueOf(head.toUpperCase().trim()));
        SetItemHead(helmet, armorStand);
    }

    public void SetHead(String url, ArmorStand armorStand){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        PlayerProfile playerProfile = Bukkit.createPlayerProfile(new UUID(0,0));
        URL skin = null;

        try {
            skin = new URL("https://textures.minecraft.net/texture/" + url);

        }catch (IOException e){
            System.out.println(e);
            return;
        }

        playerProfile.getTextures().setSkin(skin);

        skullMeta.setOwnerProfile(playerProfile);
        head.setItemMeta(skullMeta);

        SetItemHead(head, armorStand);
    }

    public void SetHead(String url){
        SetHead(url, GetArmorStand());
    }

    void SetItemHead(ItemStack item, ArmorStand armorStand){
        EntityEquipment equipment = armorStand.getEquipment();

        assert equipment != null;
        equipment.clear();
        equipment.setHelmet(item);
    }
}
