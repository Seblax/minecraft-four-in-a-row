package org.seblax.team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.seblax.Data;
import org.seblax.PlayerMinigame;
import org.seblax.utils.ArmorStandUtil;
import org.seblax.utils.Coord;

public class Team {
    public ArmorStandUtil armorStandUtil;

    TeamColor teamColor;
    TeamColor oldColor;

    Coord[] scenario;
    public boolean block;

    public Team(Coord coord, String name, Coord[] scenario){
        armorStandUtil = new ArmorStandUtil(coord, name);

        this.scenario = scenario;
        this.teamColor = armorStandUtil.CurrentColor();
    }

    public void ChangePlayerColor(Team p){
        oldColor = teamColor;

        TeamColor res = TeamColor.of(teamColor.getValue() + 1);
        if(p.teamColor.getValue() == res.getValue()){
            res = TeamColor.of(res.getValue() + 1);
        }

        this.teamColor = res;
        armorStandUtil.CurrentColor(this.teamColor);
        armorStandUtil.SetAmorStandSkin();
    }

    public void UpdateScenario(){
        if(oldColor == teamColor) return;
        String oldColorName = oldColor.getColorName();
        String newColorName = teamColor.getColorName();

        Data.ScenarioBlocks.forEach(
                x -> Fill(oldColorName + x, newColorName + x)
        );
    }

    void Fill(String o, String n){
        String commandScneario = String.format("fill %d %d %d %d %d %d %s replace %s",
                scenario[0].x.intValue(), scenario[0].y.intValue(), scenario[0].z.intValue(),
                scenario[1].x.intValue(), scenario[1].y.intValue(), scenario[1].z.intValue(),
                n, o);

        String commandDoor = String.format("fill %d %d %d %d %d %d %s replace %s",
                scenario[2].x.intValue(), scenario[2].y.intValue(), scenario[2].z.intValue(),
                scenario[3].x.intValue(), scenario[3].y.intValue(), scenario[3].z.intValue(),
                n, o);

        String commandMark = String.format("fill %d %d %d %d %d %d %s replace %s",
                scenario[4].x.intValue(), scenario[4].y.intValue(), scenario[4].z.intValue(),
                scenario[5].x.intValue(), scenario[5].y.intValue(), scenario[5].z.intValue(),
                n, o);

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),commandScneario);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),commandDoor);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),commandMark);
    }

    public void Lock(Player player, Coord coord, float Yaw){
        Location position = coord.toLocation();
        position.setYaw(Yaw);
        player.teleport(position);
        player.addScoreboardTag("Player4x4");
        player.addScoreboardTag(this.armorStandUtil.name.split(" ")[1]);

        new PlayerMinigame(player.getUniqueId(), this);
        this.armorStandUtil.SetHead("27548362a24c0fa8453e4d93e68c5969ddbde57bf6666c0319c1ed1e84d89065");

        armorStandUtil.particles.ArmorStandTeleport(coord);

        this.block = true;
    }

    public void UnLock(Player jugador){
        jugador.teleport(new Location(Data.CurrentWorld,-253.5, 65.00, -45.5, -90, 0));
        jugador.removeScoreboardTag("Player4x4");
        jugador.removeScoreboardTag(this.armorStandUtil.name.split(" ")[1]);
        armorStandUtil.particles.ArmorStandTeleport(-253.5, 65.00, -45.5);

        this.armorStandUtil.SetAmorStandSkin();
        this.block = false;
    }
}