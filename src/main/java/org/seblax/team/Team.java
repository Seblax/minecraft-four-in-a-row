package org.seblax.team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.seblax.Data;
import org.seblax.Game;
import org.seblax.player.PlayerMinigame;
import org.seblax.four;
import org.seblax.utils.ArmorStandUtil;
import org.seblax.utils.types.Coord;

import java.util.UUID;
/**
 * Represents a team in the minigame with associated properties like color, start position,
 * and scenario configuration. Manages players and updates in the game.
 */
public class Team {
    private final ArmorStandUtil armorStandUtil; // Utility for managing the team's ArmorStand
    private final Location startPosition; // Player's starting position in the team
    private final Coord[] scenario; // Coordinates defining the team's scenario in the game
    private final String name; // Team name

    private PlayerMinigame playerMinigame;
    private TeamColor teamColor; // Current team color
    private TeamColor previousColor; // Last color before a change
    private boolean isLocked; // Whether the team is currently locked to a player

    public TeamColor getTeamColor() {
        return this.teamColor;
    }

    public String getName() {
        return this.name;
    }

    public boolean isLocked(){
        return this.isLocked;
    }

    public ArmorStand getArmorStand(){
        return this.armorStandUtil.getArmorStand();
    }

    public ArmorStandUtil getArmorStandUtil(){
        return this.armorStandUtil;
    }


    /**
     * Main constructor to initialize a team.
     *
     * @param coord         Initial coordinates for the ArmorStand.
     * @param name          Name of the team.
     * @param scenario      Array of coordinates for the team's scenario.
     * @param startPosition Starting position for the team's player.
     */
    public Team(Coord coord, String name, Coord[] scenario, Coord startPosition) {
        this.armorStandUtil = new ArmorStandUtil(coord, name);
        this.scenario = scenario;
        this.teamColor = armorStandUtil.getCurrentColor();
        this.name = armorStandUtil.getName();
        this.startPosition = startPosition.toLocation();
        this.playerMinigame = new PlayerMinigame(this.armorStandUtil.getUUID(), this);
    }

    /** Gets the UUID of the ArmorStand associated with the team. */
    public UUID getTeamUUID() {
        return this.armorStandUtil.getUUID();
    }

    /** Gets the UUID of the player currently associated with the team. */
    public UUID getPlayerUUID() {
        return this.playerMinigame.getPlayerUUID();
    }

    /**
     * Changes the team's color, avoiding conflicts with the enemy team's color.
     *
     * @param enemyTeam The enemy team used to validate the color change.
     */
    public void changeTeamColor(Team enemyTeam) {
        previousColor = teamColor; // Save the current color

        TeamColor newColor = TeamColor.of(teamColor.getValue() + 1);
        if (enemyTeam.teamColor == newColor) {
            newColor = TeamColor.of(newColor.getValue() + 1);
        }

        this.teamColor = newColor;
        armorStandUtil.setCurrentColor(this.teamColor);
    }

    /**
     * Updates the team's scenario in the game world by replacing blocks based on the team's color.
     */
    public void updateScenario() {
        if (previousColor == teamColor) return;

        String oldColorName = previousColor.getColorName();
        String newColorName = teamColor.getColorName();

        Data.SCENARIO_TEAM.BLOCKS.forEach(
                blockType -> executeFillCommands(oldColorName + blockType, newColorName + blockType)
        );
    }

    /**
     * Builds and executes fill commands to replace blocks within the team's scenario.
     *
     * @param oldBlock The block type to be replaced.
     * @param newBlock The block type to replace with.
     */
    private void executeFillCommands(String oldBlock, String newBlock) {
        for (int i = 0; i < scenario.length; i += 2) {
            String command = fillParser(scenario[i], scenario[i + 1], newBlock, oldBlock);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    /**
     * Generates a fill command to replace blocks between two coordinates.
     *
     * @param firstCoord  The starting coordinate.
     * @param secondCoord The ending coordinate.
     * @param newBlock    The new block type.
     * @param oldBlock    The old block type.
     * @return A formatted fill command string.
     */
    private String fillParser(Coord firstCoord, Coord secondCoord, String newBlock, String oldBlock) {
        return String.format("fill %d %d %d %d %d %d %s replace %s",
                firstCoord.x.intValue(), firstCoord.y.intValue(), firstCoord.z.intValue(),
                secondCoord.x.intValue(), secondCoord.y.intValue(), secondCoord.z.intValue(),
                newBlock, oldBlock);
    }

    /**
     * Checks if a given player is currently assigned to this team.
     *
     * @param player The player to check.
     * @return True if the player belongs to this team, otherwise false.
     */
    public boolean isPlayersTeam(Player player) {
        return player.getUniqueId().equals(playerMinigame.getPlayerUUID());
    }

    /**
     * Locks the team to a player, setting their position, scoreboard tags, and initializing the game.
     *
     * @param player The player joining the team.
     */
    public void lock(Player player) {
        player.teleport(startPosition);
        player.addScoreboardTag("Player4x4");

        this.playerMinigame = new PlayerMinigame(player.getUniqueId(), this);

        four.game = new Game();
        four.game.clearBoard();

        // Update the team's ArmorStand
        this.armorStandUtil.setHead("27548362a24c0fa8453e4d93e68c5969ddbde57bf6666c0319c1ed1e84d89065");
        armorStandUtil.ArmorStandTeleport(startPosition);

        this.isLocked = true;
    }

    /**
     * Unlocks the team when a player leaves, resetting their inventory and team state.
     *
     * @param player The player leaving the team.
     */
    public void unlock(Player player) {
        player.teleport(Data.Teams.TEAMS_EXIT_COORD.toLocation());
        player.removeScoreboardTag("Player4x4");
        player.getInventory().clear();

        resetTeam();
    }

    /**
     * Resets the team's state, including the ArmorStand and player association.
     */
    public void resetTeam() {
        this.armorStandUtil.ArmorStandTeleport(Data.Teams.TEAMS_EXIT_COORD.toLocation());
        this.armorStandUtil.updateArmorStandSkin();
        this.isLocked = false;
        this.playerMinigame = new PlayerMinigame(this.armorStandUtil.getUUID(), this);
    }
}