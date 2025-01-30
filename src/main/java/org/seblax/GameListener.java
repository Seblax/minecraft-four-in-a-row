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

public class GameListener implements Listener {

    private final JavaPlugin plugin;

    public GameListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractWithInventory(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (!player.getScoreboardTags().contains("Player4x4")) return;

        event.setCancelled(true);

        if (item.getType() != Material.BARRIER) return;
        Data.Teams.manager.PlayerLeavesTeam(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        double distancePlayerToA = Data.Teams.TEAM_A_ARMORSTAND_COORD.toLocation().distance(playerLocation);
        double distancePlayerToB = Data.Teams.TEAM_B_ARMORSTAND_COORD.toLocation().distance(playerLocation);

        if (distancePlayerToA <= 0.5 || distancePlayerToB <= 0.5) {
            Data.Teams.manager.PlayerJoinsTeam(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.getScoreboardTags().contains("Player4x4")) return;

        player.removeScoreboardTag("Player4x4");
        player.getInventory().clear();
        player.teleport(Data.Teams.TEAMS_EXIT_COORD.toLocation());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Data.Teams.manager.getTeamByPlayer(player).resetTeam();
    }

    @EventHandler
    public void onPlayerSetBlock(BlockPlaceEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        Player player = event.getPlayer();

        if (!player.getScoreboardTags().contains("Player4x4")) return;

        event.setCancelled(true);

        int x = blockLocation.getBlockX();
        int z = blockLocation.getBlockZ();

        if (z <= -43 && z >= -49) {
            int position = -43 - z;
            Team playerTeam = four.game.getCurrentTurnTeam();
            boolean placed = false;

            if (!four.game.canPlace() || !playerTeam.isPlayersTeam(player)) {
                player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "It's not your turn yet!");
                ErrorSound(player);
                return;
            }
            ;

            if (x == -235 || x == -229) {
                placed = four.game.setTile(position, z, playerTeam.getTeamColor().getColorName());
                four.game.canPlace(!placed);
            }

            if (!placed) {
                player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You can't place a piece in this column!");
                ErrorSound(player);
            }
        }
    }

    void ErrorSound(Player player){
        SoundManager.Player.playSound(player, Sound.ENTITY_VILLAGER_TRADE, SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player, Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.PLAYERS, 1 ,0);
        SoundManager.Player.playSound(player, Sound.UI_TOAST_OUT, SoundCategory.PLAYERS);
    }

    @EventHandler
    public void SetPieceInGround(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock && event.getEntity().getScoreboardTags().contains("4x4")) {
            Location blockLocation = event.getBlock().getLocation().add(0.5,0.5,0.5);

            Data.CURRENT_WORLD.spawnParticle(
                    Particle.DUST,
                    blockLocation,
                    100,
                    0.5,
                    0.5,
                    0.5,
                    new Particle.DustOptions(
                            four.game.getCurrentTurnTeam().getTeamColor().getColor(),
                            1));

            SoundManager.playSound(blockLocation, Sound.BLOCK_ANVIL_PLACE);
            SoundManager.playSound(blockLocation, Sound.ENTITY_VILLAGER_HURT);
            SoundManager.playSound(blockLocation, Sound.BLOCK_AMETHYST_BLOCK_FALL);

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                four.game.nextRound();
            }, 10L);
        }
    }
}