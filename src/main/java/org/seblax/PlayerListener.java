package org.seblax;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.seblax.team.Team;
import org.seblax.team.TeamColor;
import org.seblax.utils.Coord;

public class PlayerListener implements Listener {

    private JavaPlugin plugin;

    public PlayerListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractWithInventory(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if(!player.getScoreboardTags().contains("Player4x4")) return;

        event.setCancelled(true);

        if (item != null && item.getType() == Material.BARRIER) {

            Team currentTeam = player.getScoreboardTags().contains("1") ? Data.team1 : Data.team2;
            if(!currentTeam.block) return;

            currentTeam.UnLock(player);
            player.getInventory().clear();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        ArmorStand armorStandTeam1 = Data.team1.armorStandUtil.GetArmorStand();
        ArmorStand armorStandTeam2 = Data.team2.armorStandUtil.GetArmorStand();

            if (armorStandTeam1 != null && !Data.team1.block && armorStandTeam1.getLocation().distance(playerLocation) <= 0.5) {
                Data.team1.Lock(player,new Coord(-237.5, 68.0, -45.5), -90);
            }else if(armorStandTeam2 != null && !Data.team2.block&& armorStandTeam2.getLocation().distance(playerLocation) <= 0.5){
                Data.team2.Lock(player,new Coord(-225.5, 68.0, -45.5), 90);
        }
    }

    @EventHandler
    public void onPlayerSetBlock(BlockPlaceEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        Player player = event.getPlayer();

        if(!player.getScoreboardTags().contains("Player4x4")) return;

        event.setCancelled(true);

        if (blockLocation.getBlockZ() <= -43 && blockLocation.getBlockZ() >= -49) {
            int offset = blockLocation.getBlockZ();

            if(blockLocation.getBlockX() == -235) {
                SummonFallingSand(offset,Data.team1.armorStandUtil.CurrentColor());
                four.game.SetCasilla(blockLocation.getBlockZ(),1);
            }

            if(blockLocation.getBlockX() == -229){
                SummonFallingSand(offset,Data.team2.armorStandUtil.CurrentColor());
                four.game.SetCasilla(blockLocation.getBlockZ(),2);
            }
        }
    }

    void SummonFallingSand(int offset, TeamColor color){
        Coord FallingSandLocation = new Coord(-231.5, 76., offset + 0.5);
        Material fallingBlockMaterial = Material.valueOf((color.getColorName() + "_concrete_powder").toUpperCase());
        FallingBlock fallingBlock = Data.CurrentWorld.spawnFallingBlock(FallingSandLocation.toLocation(), fallingBlockMaterial.createBlockData());

        // Configurar propiedades del FallingBlock
        fallingBlock.setDropItem(false); // Evita que suelte el bloque al caer
        fallingBlock.setHurtEntities(true); // Opcional: puede dañar entidades al impactar
    }
}
