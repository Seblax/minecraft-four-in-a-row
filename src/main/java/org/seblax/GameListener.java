package org.seblax;

import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.seblax.team.Team;
import org.seblax.utils.SoundManager;

/**
 * Listener class to handle game events in the plugin.
 */
public class GameListener implements Listener {
    private final JavaPlugin plugin;
    private static final double TEAM_JOIN_RADIUS = 0.5;
    private static final int COLUMN_X_LEFT = -235;
    private static final int COLUMN_X_RIGHT = -229;
    private static final int MIN_Z = -49;
    private static final int MAX_Z = -43;

    public GameListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Prevents players with "Player4x4" tag from interacting with inventory.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        Player player = (Player) event.getWhoClicked();
        if (!player.getScoreboardTags().contains("Player4x4")) return;

        event.setCancelled(true);

        if (item.getType() == Material.BARRIER) {
            Data.Teams.manager.playerLeavesTeam(player);
        }
    }

    /**
     * Handles team joining when a player moves near a team's base.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (isNearTeamBase(location)) {
            Data.Teams.manager.playerJoinsTeam(player);
        }
    }

    /**
     * Checks if a player is near either team's base.
     */
    private boolean isNearTeamBase(Location playerLocation) {
        return Data.Teams.TEAM_A_ARMORSTAND_COORD.toLocation().distance(playerLocation) <= TEAM_JOIN_RADIUS ||
                Data.Teams.TEAM_B_ARMORSTAND_COORD.toLocation().distance(playerLocation) <= TEAM_JOIN_RADIUS;
    }

    /**
     * Handles player join events.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.getScoreboardTags().contains("Player4x4")) return;

        player.removeScoreboardTag("Player4x4");
        player.getInventory().clear();
        player.teleport(Data.Teams.TEAMS_EXIT_COORD.toLocation());
    }

    /**
     * Resets the team state when a player quits.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Team team = Data.Teams.manager.getTeamByPlayer(player);
        if (team != null) {
            team.resetTeam();
        }
    }

    /**
     * Prevents block placement for players with "Player4x4" tag and enforces game rules.
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!player.getScoreboardTags().contains("Player4x4")) return;

        event.setCancelled(true);

        Location blockLocation = event.getBlock().getLocation();
        int x = blockLocation.getBlockX();
        int z = blockLocation.getBlockZ();

        if (z < MIN_Z || z > MAX_Z) return;

        int position = MAX_Z - z;
        Team playerTeam = Four.game.getCurrentTurnTeam();

        if (!Four.game.canPlace() || !playerTeam.isPlayersTeam(player)) {
            sendErrorMessage(player, "It's not your turn yet!");
            return;
        }

        boolean placed = (x == COLUMN_X_LEFT || x == COLUMN_X_RIGHT) &&
                Four.game.setTile(position, z, playerTeam.getTeamColor().getColorName());

        if (!placed) {
            sendErrorMessage(player, "You can't place a piece in this column!");
        } else {
            Four.game.canPlace(false);
        }
    }

    /**
     * Sends an error message and plays an error sound for the player.
     */
    private void sendErrorMessage(Player player, String message) {
        player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + message);
        playErrorSound(player);
    }

    /**
     * Plays error sounds for invalid actions.
     */
    private void playErrorSound(Player player) {
        SoundManager.Player.playSound(player, Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.PLAYERS, 1, 0);
        SoundManager.Player.playSound(player, Sound.UI_TOAST_OUT, SoundCategory.PLAYERS);
    }

    /**
     * Handles falling blocks landing and triggers the next round.
     */
    @EventHandler
    public void onFallingBlockLand(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof FallingBlock)) return;

        FallingBlock fallingBlock = (FallingBlock) event.getEntity();
        if (!fallingBlock.getScoreboardTags().contains("4x4")) return;

        Location blockLocation = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
        Team currentTeam = Four.game.getCurrentTurnTeam();

        // Spawn particle effect
        Data.CURRENT_WORLD.spawnParticle(
                Particle.DUST,
                blockLocation,
                100,
                0.5,
                0.5,
                0.5,
                new Particle.DustOptions(currentTeam.getTeamColor().getColor(), 1)
        );

        playLandingSounds(blockLocation);

        // Schedule next round
        Bukkit.getScheduler().runTaskLater(plugin, Four.game::nextRound, 10L);
    }

    /**
     * Plays sounds when a block lands.
     */
    private void playLandingSounds(Location location) {
        SoundManager.playSound(location, Sound.BLOCK_ANVIL_PLACE);
        SoundManager.playSound(location, Sound.ENTITY_VILLAGER_HURT);
        SoundManager.playSound(location, Sound.BLOCK_AMETHYST_BLOCK_FALL);
    }
}

//package org.seblax;
//
//import org.bukkit.*;
//import org.bukkit.entity.FallingBlock;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.event.entity.EntityChangeBlockEvent;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerMoveEvent;
//import org.bukkit.event.player.PlayerQuitEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.plugin.java.JavaPlugin;
//import org.seblax.team.Team;
//import org.seblax.utils.SoundManager;
//
//public class GameListener implements Listener {
//
//    private final JavaPlugin plugin;
//
//    public GameListener(JavaPlugin plugin) {
//        this.plugin = plugin;
//    }
//
//    @EventHandler
//    public void onPlayerInteractWithInventory(InventoryClickEvent event) {
//        ItemStack item = event.getCurrentItem();
//        Player player = (Player) event.getWhoClicked();
//
//        if (!player.getScoreboardTags().contains("Player4x4")) return;
//
//        event.setCancelled(true);
//
//        if (item.getType() != Material.BARRIER) return;
//        Data.Teams.manager.playerLeavesTeam(player);
//    }
//
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        Player player = event.getPlayer();
//        Location playerLocation = player.getLocation();
//
//        double distancePlayerToA = Data.Teams.TEAM_A_ARMORSTAND_COORD.toLocation().distance(playerLocation);
//        double distancePlayerToB = Data.Teams.TEAM_B_ARMORSTAND_COORD.toLocation().distance(playerLocation);
//
//        if (distancePlayerToA <= 0.5 || distancePlayerToB <= 0.5) {
//            Data.Teams.manager.playerJoinsTeam(player);
//        }
//    }
//
//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        Player player = event.getPlayer();
//
//        if (!player.getScoreboardTags().contains("Player4x4")) return;
//
//        player.removeScoreboardTag("Player4x4");
//        player.getInventory().clear();
//        player.teleport(Data.Teams.TEAMS_EXIT_COORD.toLocation());
//    }
//
//    @EventHandler
//    public void onPlayerQuit(PlayerQuitEvent event) {
//        Player player = event.getPlayer();
//
//        Data.Teams.manager.getTeamByPlayer(player).resetTeam();
//    }
//
//    @EventHandler
//    public void onPlayerSetBlock(BlockPlaceEvent event) {
//        Location blockLocation = event.getBlock().getLocation();
//        Player player = event.getPlayer();
//
//        if (!player.getScoreboardTags().contains("Player4x4")) return;
//
//        event.setCancelled(true);
//
//        int x = blockLocation.getBlockX();
//        int z = blockLocation.getBlockZ();
//
//        if (z <= -43 && z >= -49) {
//            int position = -43 - z;
//            Team playerTeam = Four.game.getCurrentTurnTeam();
//            boolean placed = false;
//
//            if (!Four.game.canPlace() || !playerTeam.isPlayersTeam(player)) {
//                player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "It's not your turn yet!");
//                ErrorSound(player);
//                return;
//            }
//            ;
//
//            if (x == -235 || x == -229) {
//                placed = Four.game.setTile(position, z, playerTeam.getTeamColor().getColorName());
//                Four.game.canPlace(!placed);
//            }
//
//            if (!placed) {
//                player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You can't place a piece in this column!");
//                ErrorSound(player);
//            }
//        }
//    }
//
//    void ErrorSound(Player player){
//        SoundManager.Player.playSound(player, Sound.ENTITY_VILLAGER_TRADE, SoundCategory.PLAYERS);
//        SoundManager.Player.playSound(player, Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS);
//        SoundManager.Player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.PLAYERS, 1 ,0);
//        SoundManager.Player.playSound(player, Sound.UI_TOAST_OUT, SoundCategory.PLAYERS);
//    }
//
//    @EventHandler
//    public void SetPieceInGround(EntityChangeBlockEvent event) {
//        if (event.getEntity() instanceof FallingBlock && event.getEntity().getScoreboardTags().contains("4x4")) {
//            Location blockLocation = event.getBlock().getLocation().add(0.5,0.5,0.5);
//
//            Data.CURRENT_WORLD.spawnParticle(
//                    Particle.DUST,
//                    blockLocation,
//                    100,
//                    0.5,
//                    0.5,
//                    0.5,
//                    new Particle.DustOptions(
//                            Four.game.getCurrentTurnTeam().getTeamColor().getColor(),
//                            1));
//
//            SoundManager.playSound(blockLocation, Sound.BLOCK_ANVIL_PLACE);
//            SoundManager.playSound(blockLocation, Sound.ENTITY_VILLAGER_HURT);
//            SoundManager.playSound(blockLocation, Sound.BLOCK_AMETHYST_BLOCK_FALL);
//
//            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
//                Four.game.nextRound();
//            }, 10L);
//        }
//    }
//}