package org.seblax;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * This class listens for player interactions with armor stands.
 * Specifically, it handles events when a player interacts with an armor stand entity
 * that has a specific scoreboard tag ("4x4").
 */
public class ArmorStandListener implements Listener {

    /**
     * This event handler is triggered when a player interacts with an entity, such as an armor stand.
     * It checks if the interacted entity is an armor stand and if it contains the "4x4" tag.
     * If both conditions are true, the player's interaction is processed and handled accordingly.
     *
     * @param event The event triggered when a player interacts with an entity.
     */
    @EventHandler
    public void onPlayerInteractWithArmorStand(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();  // Get the entity the player interacted with

        // Checks if the entity is an instance of ArmorStand
        if (entity instanceof ArmorStand armorStand) {
            // Checks if the armor stand contains the "4x4" tag in its scoreboard tags
            if (!armorStand.getScoreboardTags().contains("4x4")) {
                return;  // If the armor stand doesn't have the "4x4" tag, ignore the interaction
            }

            event.setCancelled(true);  // Cancel the event to prevent default behavior (e.g., mounting, etc.)
            Data.Teams.manager.TeamSelected(armorStand.getUniqueId());  // Call the TeamSelected method with the armor stand's unique ID
        }
    }
}

//package org.seblax;
//
//import org.bukkit.entity.ArmorStand;
//import org.bukkit.entity.Entity;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerInteractAtEntityEvent;
//
//public class ArmorStandListener  implements Listener {
//
//    // This event its called when a player interact with an Entity.
//    @EventHandler
//    public void onPlayerInteractWithArmorStand(PlayerInteractAtEntityEvent event) {
//        Entity entity = event.getRightClicked();
//
//        /// Checks if the entity's an Instance of ArmorStand.
//        if (entity instanceof ArmorStand armorStand) {
//            // Checks the entity's tags to see if contains the tag "4x4".
//            if(!armorStand.getScoreboardTags().contains("4x4")){
//                return;
//            }
//
//            event.setCancelled(true);
//            Data.Teams.manager.TeamSelected(armorStand.getUniqueId());
//        }
//    }
//}
