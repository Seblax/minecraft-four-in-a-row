package org.seblax;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.seblax.animations.AnimatorManager;
import org.seblax.team.TeamsManager;
import org.seblax.utils.ArmorStandUtil;
import org.seblax.utils.FileData;

public final class four extends JavaPlugin {

    private static four instance;
    public static AnimatorManager animatorManager;
    public static Game game;

    public static four getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        RemoveArmorStands();

        FileData.plugin = this.getDataFolder();
        FileData save = new FileData(ArmorStandUtil.CONFIG_PATH);

        if(!save.exist){
            save.set("1",1);
            save.set("2",2);
        }

        getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        Initialize();
    }

    @Override
    public void onDisable() {
        RemoveArmorStands();
    }

    public static void Initialize(){
        World world = Bukkit.getWorld(Data.WORLD_PATH_FOLDER);
        if (world == null) {
            System.out.println(String.format("Couldn't found %s world folder.",Data.WORLD_PATH_FOLDER));
            return;
        }

        Data.CURRENT_WORLD = world;
        Data.Teams.manager = new TeamsManager();

        animatorManager = new AnimatorManager();
        animatorManager.addArmorStand(Data.Teams.manager.teamA.getArmorStandUtil());
        animatorManager.addArmorStand(Data.Teams.manager.teamB.getArmorStandUtil());
        animatorManager.startAnimation();
    }

    void RemoveArmorStands(){
        if(Data.Teams.manager != null)
            Data.Teams.manager.remove();

        for (Entity entity : Bukkit.getWorld("TNT 3 - Rework").getEntities()) {
            if (entity instanceof ArmorStand armorStand) {
                if (armorStand.getScoreboardTags().contains("4x4")) {
                    armorStand.remove();
                }
            }
        }
    }
}
