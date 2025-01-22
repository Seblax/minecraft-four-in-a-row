package org.seblax;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.seblax.team.Team;
import org.seblax.utils.Coord;

public class ArmorStandListener  implements Listener {

    private JavaPlugin plugin;

    public ArmorStandListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractWithArmorStand(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (entity instanceof ArmorStand armorStand && armorStand.getScoreboardTags().contains("4x4")) {

            event.setCancelled(true);

            Team currentTeam = Data.team1;
            Team enemyTeam = Data.team2;

            if(armorStand.getScoreboardTags().contains("2")){
                currentTeam = Data.team2;
                enemyTeam = Data.team1;
            }

            if(currentTeam.block) return;

            currentTeam.ChangePlayerColor(enemyTeam);
            currentTeam.UpdateScenario();
        }
    }
}
