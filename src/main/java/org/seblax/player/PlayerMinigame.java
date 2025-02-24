package org.seblax.player;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.seblax.team.Team;
import org.seblax.team.TeamColor;

import java.util.UUID;

/**
 * Represents a player in the minigame, handling inventory setup and team assignments.
 */
public class PlayerMinigame {
    private final UUID playerUUID;
    private final Team team;

    /**
     * Constructs a PlayerMinigame instance.
     *
     * @param playerUUID The UUID of the player.
     * @param team       The team the player is assigned to.
     */
    public PlayerMinigame(UUID playerUUID, Team team) {
        this.playerUUID = playerUUID;
        this.team = team;
        setInventory(); // Initialize player inventory and armor setup
    }

    /**
     * @return The UUID of the player.
     */
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    /**
     * @return The player's entity if they are online; otherwise, null.
     */
    public Entity getEntity() {
        return Bukkit.getEntity(playerUUID);
    }

    /**
     * Configures the player's inventory, including UI items, team-based blocks, and armor.
     */
    private void setInventory() {
        Entity entity = getEntity();
        if (!(entity instanceof Player playerEntity)) {
            return; // Exit if the entity is not a valid player
        }

        TeamColor teamColor = team.getTeamColor();
        String ui1 = "BLACK_STAINED_GLASS_PANE";
        String ui2 = (teamColor.getColorName() + "_STAINED_GLASS_PANE").toUpperCase();
        String exitItem = "BARRIER";

        // Inventory UI pattern
        for (int i = 0; i < 36; i++) {
            ItemStack item = (i % 2 == 0) ? createItem(ui1, " ") : createItem(ui2, " ");
            playerEntity.getInventory().setItem(i, item);
        }

        // Assign concrete powder blocks for player movement
        setupMovementBlocks(playerEntity, teamColor);

        // Exit button
        ItemStack exit = createItem(exitItem, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Exit");
        playerEntity.getInventory().setItem(22, exit);

        // Equip player with team-colored armor
        equipArmor(playerEntity, teamColor);
    }

    /**
     * Assigns concrete powder blocks that can be placed on specific blocks.
     *
     * @param playerEntity The player entity.
     * @param teamColor    The team color to determine block type.
     */
    private void setupMovementBlocks(Player playerEntity, TeamColor teamColor) {
        for (int i = 0; i < 9; i++) {
            String command = String.format(
                    "item replace entity %s container.%d with %s_concrete_powder" +
                            "[can_place_on={predicates:[{blocks:[\"minecraft:black_terracotta\",\"minecraft:gray_terracotta\"]}],show_in_tooltip:true}," +
                            "custom_name='[\"\",{\"text\":\"Piece\",\"italic\":false,\"color\":\"%s\",\"bold\":true}]']",
                    playerEntity.getName(), i, teamColor.getColorName(), teamColor.getChatColor().name().toLowerCase()
            );

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    /**
     * Equips the player with team-colored armor.
     *
     * @param playerEntity The player to equip.
     * @param teamColor    The team color determining armor appearance.
     */
    private void equipArmor(Player playerEntity, TeamColor teamColor) {
        ItemStack helmet = createItem((teamColor.getColorName() + "_STAINED_GLASS").toUpperCase(),
                teamColor.getChatColor() + "" + ChatColor.BOLD + team.getName());
        ItemStack chestplate = createArmor(teamColor.getColor(), Material.LEATHER_CHESTPLATE);
        ItemStack leggings = createArmor(teamColor.getColor(), Material.LEATHER_LEGGINGS);
        ItemStack boots = createArmor(teamColor.getColor(), Material.LEATHER_BOOTS);

        playerEntity.getInventory().setHelmet(helmet);
        playerEntity.getInventory().setChestplate(chestplate);
        playerEntity.getInventory().setLeggings(leggings);
        playerEntity.getInventory().setBoots(boots);
    }

    /**
     * Creates an ItemStack of a specified material with a display name.
     *
     * @param materialName The name of the material.
     * @param displayName  The custom display name.
     * @return The created ItemStack, or a BARRIER if the material is invalid.
     */
    private ItemStack createItem(String materialName, String displayName) {
        try {
            Material material = Material.valueOf(materialName);
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(displayName);
                item.setItemMeta(meta);
            }
            return item;
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("Invalid material: " + materialName);
            return new ItemStack(Material.BARRIER);
        }
    }

    /**
     * Creates a piece of leather armor in the given color.
     *
     * @param color    The color for the armor.
     * @param material The type of leather armor.
     * @return The colored armor ItemStack.
     */
    private ItemStack createArmor(Color color, Material material) {
        ItemStack armor = new ItemStack(material);
        ItemMeta meta = armor.getItemMeta();

        if (meta instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(color);
            leatherMeta.setDisplayName(team.getTeamColor().getChatColor() + "" + ChatColor.BOLD + team.getName());
            armor.setItemMeta(leatherMeta);
        }

        return armor;
    }
}