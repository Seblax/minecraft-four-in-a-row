package org.seblax;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.seblax.team.Team;
import org.seblax.team.TeamColor;

import java.util.UUID;

public class PlayerMinigame {

    UUID player;
    Team team;

    public PlayerMinigame(UUID p, Team t){
        this.player = p;
        this.team = t;

        SetInventory();
    }

    public Entity GetEntity(){
        return Bukkit.getEntity(this.player);
    }

    void SetInventory(){
        Player p = (Player)GetEntity();
        TeamColor teamcolor = team.armorStandUtil.CurrentColor();
        String pieceName = (teamcolor.getColorName() + "_concrete_powder").toUpperCase();
        String ui_1 = ("black_stained_glass_pane").toUpperCase();
        String ui_2 = (teamcolor.getColorName() + "_stained_glass_pane").toUpperCase();
        String exit = ("barrier").toUpperCase();

        for (int i = 0; i < 36; i++){
            ItemStack item = i % 2 == 0 ? SetItem(ui_1, " " ) :
                    SetItem(ui_2, " " ) ;

            p.getInventory().setItem(i,item);
        }

        for (int i = 0; i < 9; i++){
//            ItemStack item = SetItem(pieceName, teamcolor.getChatColor()+ "" + ChatColor.BOLD + "Piece");
//            p.getInventory().setItem(i,item);

            String command = "item replace entity " + p.getName() +
                    " container." + i  + " with " + teamcolor.getColorName() + "_concrete_powder" +
                    "[can_place_on={predicates:[{blocks:[\"minecraft:black_terracotta\",\"minecraft:gray_terracotta\"]}],show_in_tooltip:true},custom_name='[\"\",{\"text\":\"Piece\",\"italic\":false,\"color\":\"" +
                    teamcolor.getChatColor().name().toLowerCase() +
                    "\",\"bold\":true}]']";

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),command);
        }

        ItemStack item = SetItem(exit, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Exit");
        p.getInventory().setItem(22,item);

        ItemStack helmet = SetItem((teamcolor.getColorName() + "_stained_glass").toUpperCase(), team.armorStandUtil.CurrentColor().getChatColor()+ "" + ChatColor.BOLD + team.armorStandUtil.name);
        ItemStack chestPlate = SetArmor(teamcolor.getColor(), Material.LEATHER_CHESTPLATE);
        ItemStack leggings = SetArmor(teamcolor.getColor(), Material.LEATHER_LEGGINGS);
        ItemStack boots = SetArmor(teamcolor.getColor(), Material.LEATHER_BOOTS);

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestPlate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);
    }

    ItemStack SetItem(String item, String name){
        ItemStack res = new ItemStack(Material.valueOf(item));
        ItemMeta resMeta = res.getItemMeta();

        resMeta.setItemName(name);
        res.setItemMeta(resMeta);

        return res;
    }

    ItemStack SetArmor(Color color, Material material){
        ItemStack res = new ItemStack(material);
        LeatherArmorMeta resMeta = (LeatherArmorMeta) res.getItemMeta();

        resMeta.setItemName(team.armorStandUtil.CurrentColor().getChatColor()+ "" + ChatColor.BOLD + team.armorStandUtil.name);
        resMeta.setColor(color);

        res.setItemMeta(resMeta);

        return res;
    }
}