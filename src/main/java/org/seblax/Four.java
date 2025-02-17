package org.seblax;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.seblax.animations.AnimatorManager;
import org.seblax.team.TeamsManager;

public final class Four extends JavaPlugin {

    private static Four instance;
    public static AnimatorManager animatorManager;
    public static Game game;

    /**
     * Gets the singleton instance of the plugin.
     *
     * @return The Four plugin instance.
     */
    public static Four getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        loadConfiguration();

        removeAllArmorStands();

        // Load or create configuration file

        registerEvents();
        initializeGame();
    }

    @Override
    public void onDisable() {
        removeAllArmorStands();
    }

    /**
     * Loads the configuration file and initializes default values if needed.
     */
    private void loadConfiguration() {
        Config.initialize();
    }

    /**
     * Registers event listeners for the plugin.
     */
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
    }

    /**
     * Initializes the game, ensuring the world and team managers are set up.
     */
    public static void initializeGame() {
        Data.Teams.MANAGER = new TeamsManager();

        animatorManager = new AnimatorManager();
        animatorManager.addArmorStand(Data.Teams.MANAGER.getTeamA().getArmorStandUtil());
        animatorManager.addArmorStand(Data.Teams.MANAGER.getTeamB().getArmorStandUtil());
        animatorManager.startAnimation();
    }

    /**
     * Removes all armor stands associated with the game when enabling/disabling the plugin.
     */
    private void removeAllArmorStands() {
        World world = Data.CURRENT_WORLD;
        if (world == null) {
            Bukkit.getLogger().warning("[Four] Could not find world 'TNT 3 - Rework' for armor stand cleanup.");
            return;
        }

        world.getEntities().stream()
                .filter(entity -> entity instanceof ArmorStand)
                .filter(entity -> entity.getScoreboardTags().contains("4x4"))
                .forEach(Entity::remove);

        if (Data.Teams.MANAGER != null) {
            Data.Teams.MANAGER.remove();
        }

    }
}

//package org.seblax;
//
//import org.bukkit.Bukkit;
//import org.bukkit.World;
//import org.bukkit.entity.ArmorStand;
//import org.bukkit.entity.Entity;
//import org.bukkit.plugin.java.JavaPlugin;
//import org.seblax.animations.AnimatorManager;
//import org.seblax.team.TeamsManager;
//import org.seblax.utils.ArmorStandUtil;
//import org.seblax.utils.FileData;
//
//public final class Four extends JavaPlugin {
//
//    private static Four instance;
//    public static AnimatorManager animatorManager;
//    public static Game game;
//
//    public static Four getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void onEnable() {
//        instance = this;
//        RemoveArmorStands();
//
//        FileData save = new FileData(ArmorStandUtil.CONFIG_PATH, Four.getInstance().getDataFolder());
//
//        if(!save.exists()){
//            save.set("1",1);
//            save.set("2",2);
//        }
//
//        getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);
//        getServer().getPluginManager().registerEvents(new GameListener(this), this);
//
//        Initialize();
//    }
//
//    @Override
//    public void onDisable() {
//        RemoveArmorStands();
//    }
//
//    public static void Initialize(){
//        World world = Bukkit.getWorld(Data.WORLD_PATH_FOLDER);
//        if (world == null) {
//            System.out.println(String.format("Couldn't found %s world folder.",Data.WORLD_PATH_FOLDER));
//            return;
//        }
//
//        Data.CURRENT_WORLD = world;
//        Data.Teams.manager = new TeamsManager();
//
//        animatorManager = new AnimatorManager();
//        animatorManager.addArmorStand(Data.Teams.manager.getTeamA().getArmorStandUtil());
//        animatorManager.addArmorStand(Data.Teams.manager.getTeamB().getArmorStandUtil());
//        animatorManager.startAnimation();
//    }
//
//    void RemoveArmorStands(){
//        if(Data.Teams.manager != null)
//            Data.Teams.manager.remove();
//
//        for (Entity entity : Bukkit.getWorld("TNT 3 - Rework").getEntities()) {
//            if (entity instanceof ArmorStand armorStand) {
//                if (armorStand.getScoreboardTags().contains("4x4")) {
//                    armorStand.remove();
//                }
//            }
//        }
//    }
//}
