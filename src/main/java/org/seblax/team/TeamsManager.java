package org.seblax.team;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.seblax.Data;
import org.seblax.utils.SoundManager;

import java.util.UUID;
import java.util.Optional;

/**
 * Manages teams, player assignments, and team selection logic.
 */
public class TeamsManager {
    private final Team teamA;
    private final Team teamB;

    /**
     * Initializes the TeamsManager with predefined teams.
     */
    public TeamsManager() {
        this.teamA = new Team(Data.Teams.A.TEAM_ARMORSTAND_COORD, "Player 1", Data.SCENARIO_TEAM.A, Data.Teams.A.TEAM_START_COORD);
        this.teamB = new Team(Data.Teams.B.TEAM_ARMORSTAND_COORD, "Player 2", Data.SCENARIO_TEAM.B, Data.Teams.B.TEAM_START_COORD);
    }

    public Team getTeamA(){
        return teamA;
    }

    public Team getTeamB(){
        return  teamB;
    }

    /**
     * Retrieves the team associated with a given UUID.
     *
     * @param uuid The UUID of the team.
     * @return The corresponding Team object, or null if not found.
     */
    public Team getTeamByUUID(UUID uuid) {
        return Optional.ofNullable(uuid)
                .map(id -> teamA.getTeamUUID().equals(id) ? teamA : teamB)
                .orElse(null);
    }

    /**
     * Retrieves the enemy team based on the given team.
     *
     * @param team The team whose enemy is to be found.
     * @return The enemy team, or null if the team is invalid.
     */
    public Team getEnemyTeamByUUID(Team team) {
        return team != null ? getEnemyTeamByUUID(team.getTeamUUID()) : null;
    }

    /**
     * Retrieves the enemy team of a given team UUID.
     *
     * @param uuid The UUID of the team.
     * @return The opposing team, or null if UUID is invalid.
     */
    public Team getEnemyTeamByUUID(UUID uuid) {
        return Optional.ofNullable(uuid)
                .map(id -> teamA.getTeamUUID().equals(id) ? teamB : teamA)
                .orElse(null);
    }

    /**
     * Gets the team a player belongs to.
     *
     * @param player The player whose team is to be found.
     * @return The team of the player, or null if not assigned.
     */
    public Team getTeamByPlayer(Player player) {
        return Optional.ofNullable(player)
                .map(p -> teamA.getPlayerUUID().equals(p.getUniqueId()) ? teamA : teamB)
                .orElse(null);
    }

    /**
     * Handles team selection by a player.
     *
     * @param uuid The UUID of the player selecting a team.
     */
    public void selectTeam(UUID uuid) {
        Team selectedTeam = getTeamByUUID(uuid);
        if (selectedTeam == null || selectedTeam.isLocked()) return;

        Team enemyTeam = getEnemyTeamByUUID(selectedTeam);
        selectedTeam.changeTeamColor(enemyTeam);
        selectedTeam.updateScenario();
        selectedTeam.getArmorStandUtil().getArmorStandParticle().ArmorStandSelected();

        // Play team selection sounds
        playTeamSelectionSounds(selectedTeam);
    }

    /**
     * Assigns a player to the nearest available team.
     *
     * @param player The player joining a team.
     */
    public void playerJoinsTeam(Player player) {
        if (player == null) return;

        Team playerTeam = (player.getLocation().distance(Data.Teams.A.TEAM_ARMORSTAND_COORD.toLocation()) <
                player.getLocation().distance(Data.Teams.B.TEAM_ARMORSTAND_COORD.toLocation())) ? teamA : teamB;

        playerTeam.lock(player);
        playPlayerJoinSounds(player);
    }

    /**
     * Removes a player from their assigned team.
     *
     * @param player The player leaving the team.
     */
    public void playerLeavesTeam(Player player) {
        if (player == null) return;

        Team playerTeam = getTeamByPlayer(player);
        if (playerTeam == null || !playerTeam.isLocked()) return;

        playerTeam.unlock(player);
        playPlayerLeaveSounds(player);
    }

    /**
     * Removes teams and associated entities from the world.
     */
    public void remove() {
        if (!Data.CURRENT_WORLD.getEntities().isEmpty()) {
            teamA.getArmorStand().remove();
            teamB.getArmorStand().remove();
        }
    }

    // ==========================
    // Sound Management Functions
    // ==========================

    /**
     * Plays team selection sounds when a player selects a team.
     *
     * @param team The team that was selected.
     */
    private void playTeamSelectionSounds(Team team) {
        if (team == null || team.getArmorStand() == null) return;

        SoundManager.playSound(team.getArmorStand().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_FALL, SoundCategory.AMBIENT);
        SoundManager.playSound(team.getArmorStand().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.AMBIENT);
        SoundManager.playSound(team.getArmorStand().getLocation(), Sound.UI_TOAST_IN, SoundCategory.AMBIENT, 1, 0);
    }

    /**
     * Plays sound effects when a player joins a team.
     *
     * @param player The player who joined a team.
     */
    private void playPlayerJoinSounds(Player player) {
        if (player == null) return;

        SoundManager.Player.playSound(player, Sound.UI_TOAST_IN, SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.PLAYERS);
        SoundManager.Player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS);
    }

    /**
     * Plays sound effects when a player leaves a team.
     *
     * @param player The player who left a team.
     */
    private void playPlayerLeaveSounds(Player player) {
        if (player == null) return;

        SoundManager.Player.playSound(player, Sound.UI_TOAST_OUT, SoundCategory.PLAYERS, 1, 0);
        SoundManager.Player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.PLAYERS, 1, 0);
        SoundManager.Player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 0);
    }
}


//package org.seblax.team;
//
//import org.bukkit.Sound;
//import org.bukkit.SoundCategory;
//import org.bukkit.entity.Player;
//import org.seblax.Data;
//import org.seblax.utils.SoundManager;
//
//import java.util.UUID;
//
//public class TeamsManager {
//    public Team teamA;
//    public Team teamB;
//
//    public TeamsManager(){
//        this.teamA = new Team(Data.Teams.TEAM_A_ARMORSTAND_COORD, "Player 1", Data.SCENARIO_TEAM.A, Data.Teams.TEAM_A_START_COORD);
//        this.teamB = new Team(Data.Teams.TEAM_B_ARMORSTAND_COORD, "Player 2", Data.SCENARIO_TEAM.B, Data.Teams.TEAM_B_START_COORD);
//    }
//
//    public Team getTeamByUUID(UUID uuid){
//        return teamA.getTeamUUID().equals(uuid) ? teamA : teamB;
//    }
//
//    public Team getEnemyTeamByUUID(Team t){
//        return getEnemyTeamByUUID(t.getTeamUUID());
//    }
//
//    public Team getEnemyTeamByUUID(UUID uuid){
//        return teamA.getTeamUUID() != uuid ? teamA : teamB;
//    }
//
//    public Team getTeamByPlayer(Player player){
//        return teamA.getPlayerUUID().equals(player.getUniqueId()) ?
//                teamA : teamB;
//    }
//
//    public void TeamSelected(UUID uuid){
//        Team teamSelected = getTeamByUUID(uuid);
//
//        if (teamSelected.isLocked()) return;
//
//        teamSelected.changeTeamColor(getEnemyTeamByUUID(teamSelected));
//        teamSelected.updateScenario();
//        teamSelected.getArmorStandUtil().getArmorStandParticle().ArmorStandSelected();
//
//        //Select sound effect
//        SoundManager.playSound(teamSelected.getArmorStand().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_FALL, SoundCategory.AMBIENT);
//        SoundManager.playSound(teamSelected.getArmorStand().getLocation(), Sound.UI_BUTTON_CLICK,SoundCategory.AMBIENT);
//        SoundManager.playSound(teamSelected.getArmorStand().getLocation(), Sound.UI_TOAST_IN,SoundCategory.AMBIENT, 1, 0);
//    }
//
//    public void PlayerJoinsTeam(Player player){
//        Team playerTeam = player.getLocation()
//                .distance(Data.Teams.TEAM_A_ARMORSTAND_COORD.toLocation()) < player.getLocation().distance(Data.Teams.TEAM_B_ARMORSTAND_COORD.toLocation()) ? teamA : teamB;
//        playerTeam.lock(player);
//
//        SoundManager.Player.playSound(player,Sound.UI_TOAST_IN,SoundCategory.PLAYERS);
//        SoundManager.Player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BIT,SoundCategory.PLAYERS);
//        SoundManager.Player.playSound(player,Sound.ENTITY_ENDERMAN_TELEPORT,SoundCategory.PLAYERS);
//    }
//
//    public void PlayerLeavesTeam(Player player){
//        Team playerTeam = getTeamByPlayer(player);
//        if(!playerTeam.isLocked()) return;
//        playerTeam.unlock(player);
//
//        SoundManager.Player.playSound(player,Sound.UI_TOAST_OUT,SoundCategory.PLAYERS,1,0);
//        SoundManager.Player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BIT,SoundCategory.PLAYERS,1,0);
//        SoundManager.Player.playSound(player,Sound.ENTITY_ENDERMAN_TELEPORT,SoundCategory.PLAYERS,1,0);
//    }
//
//    public void remove(){
//        if(Data.CURRENT_WORLD.getEntities().isEmpty()) return;
//
//        teamA.getArmorStand().remove();
//        teamB.getArmorStand().remove();
//    }
//}