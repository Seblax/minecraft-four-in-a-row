package org.seblax;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ArmorStandListener  implements Listener {

    // This event its called when a player interact with an Entity.
    @EventHandler
    public void onPlayerInteractWithArmorStand(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();

        /// Checks if the entity's an Instance of ArmorStand.
        if (entity instanceof ArmorStand armorStand) {
            // Checks the entity's tags to see if contains the tag "4x4".
            if(!armorStand.getScoreboardTags().contains("4x4")){
                return;
            }

            event.setCancelled(true);
            Data.Teams.manager.TeamSelected(armorStand.getUniqueId());
        }
    }
}
