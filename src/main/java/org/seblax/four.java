package org.seblax;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.seblax.animations.AnimatorManager;
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
        game = new Game();

        FileData.plugin = this.getDataFolder();
        FileData save = new FileData(ArmorStandUtil.path);
        if(!save.exist){
            save.Set("1",1);
            save.Set("2",2);
        }

        Data.Initialize();
        getServer().getPluginManager().registerEvents(new ArmorStandListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // Crear el rotador y agregar el ArmorStand
        animatorManager = new AnimatorManager(); // Velocidad de rotación: 5 grados por tick
        animatorManager.addArmorStand(Data.team1.armorStandUtil);
        animatorManager.addArmorStand(Data.team2.armorStandUtil);
        animatorManager.startAnimation();
    }

    @Override
    public void onDisable() {
        RemoveArmorStands();
    }

    public void RemoveArmorStands(){
        if(Data.CurrentWorld.getEntities().isEmpty()) return;

        for (Entity entity : Data.CurrentWorld.getEntities()) {
            if (entity instanceof ArmorStand armorStand) {
                if (armorStand.getScoreboardTags().contains("4x4")) {
                    armorStand.remove();
                }
            }
        }
    }
}
