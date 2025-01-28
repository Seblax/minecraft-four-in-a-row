package org.seblax.team;

import org.bukkit.entity.Player;
import org.seblax.Data;

import java.util.UUID;

public class TeamsManager {
    public Team teamA;
    public Team teamB;

    public TeamsManager(){
        this.teamA = new Team(Data.Teams.TEAM_A_ARMORSTAND_COORD, "Player 1", Data.SCENARIO_TEAM.A, Data.Teams.TEAM_A_START_COORD);
        this.teamB = new Team(Data.Teams.TEAM_B_ARMORSTAND_COORD, "Player 2", Data.SCENARIO_TEAM.B, Data.Teams.TEAM_B_START_COORD);
    }

    public Team getTeamByUUID(UUID uuid){
        return teamA.getTeamUUID().equals(uuid) ? teamA : teamB;
    }

    public Team getEnemyTeamByUUID(Team t){
        return getEnemyTeamByUUID(t.getTeamUUID());
    }

    public Team getEnemyTeamByUUID(UUID uuid){
        return teamA.getTeamUUID() != uuid ? teamA : teamB;
    }

    public Team getTeamByPlayer(Player player){
        return teamA.getPlayerUUID().equals(player.getUniqueId()) ?
                teamA : teamB;
    }

    public void TeamSelected(UUID uuid){
        Team teamSelected = getTeamByUUID(uuid);

        if (teamSelected.isLocked()) return;

        teamSelected.changeTeamColor(getEnemyTeamByUUID(teamSelected));
        teamSelected.updateScenario();
        teamSelected.getArmorStandUtil().getArmorStandParticle().ArmorStandSelected();
    }

    public void setPlayerTeamByLocation(Player player){
        Team playerTeam = player.getLocation()
                .distance(Data.Teams.TEAM_A_ARMORSTAND_COORD.toLocation()) < player.getLocation().distance(Data.Teams.TEAM_B_ARMORSTAND_COORD.toLocation()) ? teamA : teamB;
        playerTeam.lock(player);
    }

    public void PlayerLeavesTeam(Player player){
        Team playerTeam = getTeamByPlayer(player);
        if(!playerTeam.isLocked()) return;
        playerTeam.unlock(player);
    }

    public void remove(){
        if(Data.CURRENT_WORLD.getEntities().isEmpty()) return;

        teamA.getArmorStand().remove();
        teamB.getArmorStand().remove();
    }
}