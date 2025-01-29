package org.seblax.team;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.seblax.Data;
import org.seblax.utils.SoundManager;

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

        //Select sound effect
        SoundManager.playSound(teamSelected.getArmorStand().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_FALL, SoundCategory.AMBIENT);
        SoundManager.playSound(teamSelected.getArmorStand().getLocation(), Sound.UI_BUTTON_CLICK,SoundCategory.AMBIENT);
        SoundManager.playSound(teamSelected.getArmorStand().getLocation(), Sound.UI_TOAST_IN,SoundCategory.AMBIENT, 1, 0);
    }

    public void PlayerJoinsTeam(Player player){
        Team playerTeam = player.getLocation()
                .distance(Data.Teams.TEAM_A_ARMORSTAND_COORD.toLocation()) < player.getLocation().distance(Data.Teams.TEAM_B_ARMORSTAND_COORD.toLocation()) ? teamA : teamB;
        playerTeam.lock(player);

        SoundManager.Player.playSound(player,Sound.UI_TOAST_IN,SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BIT,SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player,Sound.ENTITY_ENDERMAN_TELEPORT,SoundCategory.PLAYERS);
    }

    public void PlayerLeavesTeam(Player player){
        Team playerTeam = getTeamByPlayer(player);
        if(!playerTeam.isLocked()) return;
        playerTeam.unlock(player);

        SoundManager.Player.playSound(player,Sound.UI_TOAST_OUT,SoundCategory.PLAYERS,1,0);
        SoundManager.Player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BIT,SoundCategory.PLAYERS,1,0);
        SoundManager.Player.playSound(player,Sound.ENTITY_ENDERMAN_TELEPORT,SoundCategory.PLAYERS,1,0);
    }

    public void remove(){
        if(Data.CURRENT_WORLD.getEntities().isEmpty()) return;

        teamA.getArmorStand().remove();
        teamB.getArmorStand().remove();
    }
}