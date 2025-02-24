package org.seblax;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.seblax.team.Team;
import org.seblax.team.TeamColor;
import org.seblax.utils.types.Coord;
import org.seblax.utils.SoundManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the game logic and board state for the 4x4 game, including tracking player turns,
 * checking win conditions, and handling game events.
 */
public class Game {
    private Team currentTurnTeam; // The team currently taking their turn
    private final int[][] board; // 2D array representing the game board
    private final List<Coord> winningLine; // List of coordinates for the winning line
    private boolean canPlace; // Flag to check if players can place tiles on the board

    private static final int WINNING_COUNT = 4; // The number of tiles needed in a row to win
    private static final Location CENTER_LOCATION = Data.BOARD.BOARD_POSITION.add(new Coord(0, Data.BOARD.ROWS/2, Data.BOARD.COLUMNS/2)).toLocation(); // Center location for sound and particle effects

    /**
     * Constructs a new game, initializing the board and setting the first team randomly.
     */
    public Game() {
        this.board = new int[Data.BOARD.COLUMNS][Data.BOARD.ROWS]; // Initialize the game board dimensions
        this.winningLine = new ArrayList<>(); // List to store coordinates of the winning line
        this.canPlace = true; // Initially, players can place tiles
        // Randomly choose the first team to play
        this.currentTurnTeam = Math.random() > 0.5 ? Data.Teams.MANAGER.getTeamA() : Data.Teams.MANAGER.getTeamB();
        clearBoard(); // Clear the board at the start of the game
    }

    /**
     * Clears the board by resetting all tiles and removing blocks in the world.
     */
    public void clearBoard() {
        boolean erased = false;

        for (int i = 0; i < Data.BOARD.COLUMNS; i++) {
            for (int j = 0; j < Data.BOARD.ROWS; j++) {
                board[i][j] = 0; // Reset the board values
                Location loc = Data.BOARD.BOARD_POSITION.add( 0, j, i).toLocation(); // Location of the block to clear
                Block block = loc.getBlock(); // Remove the block

                if(block.getType() != Material.AIR){
                    erased = true;
                    block.setType(Material.AIR);
                }
            }
        }

        if(!erased) return;

        Data.CURRENT_WORLD.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,CENTER_LOCATION,50, 1, (double) Config.getRows() /2, (double) Config.getColumns() /2, 0);
        SoundManager.playSound(CENTER_LOCATION,Sound.ENTITY_GUARDIAN_DEATH, SoundCategory.AMBIENT, 0.5f);
    }

    /**
     * Places a tile for the current team in the specified column.
     *
     * @param x         The column where the tile should be placed
     * @param colorName The color name for the tile
     * @return True if the tile was placed successfully, false if the column is full
     */
    public boolean setTile(int x, String colorName) {
        if (!isColumnAvailable(x)) return false; // Check if the column is available

        for (int j = 0; j < Data.BOARD.ROWS; j++) {
            if (board[x][j] == 0) { // Find the first available row in the column
                summonFallingSand(x, colorName); // Summon a falling block for the tile
                board[x][j] = getCurrentTeamValue(); // Mark the board with the current team's value
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the value of the tile at the specified position on the board.
     *
     * @param x The column index
     * @param y The row index
     * @return The tile value (0 for empty, 1 for team A, 2 for team B)
     */
    public int getTile(int x, int y) {
        return board[x][y];
    }

    /**
     * Set canPlace value to b
     *
     * @param b         The column where the tile should be placed
     */
    public void canPlace(boolean b){
        this.canPlace = b;
    }

    /**
     * Gets the value of canPlace.
     *
     * @return if a player can place a block
     */
    public boolean canPlace(){
        return this.canPlace;
    }

    /**
     * Gets the current turn team
     *
     * @return Current team
     */
    public Team getCurrentTurnTeam(){
        return this.currentTurnTeam;
    }

    /**
     * Advances to the next round, checking for win conditions or a draw.
     */
    public void nextRound() {
        if (checkWin()) {
            handleWin(); // Handle a win if the current team has won
        } else if (isBoardFull()) {
            handleDraw(); // Handle a draw if the board is full with no winner
        }

        this.canPlace = true; // Allow the next player to place a tile
        this.currentTurnTeam = Data.Teams.MANAGER.getEnemyTeamByUUID(this.currentTurnTeam); // Switch the turn to the opposing team
    }

    /**
     * Handles the logic when the current team wins the game.
     */
    private void handleWin() {
        highlightWinningLine(); // Highlight the winning line on the board
        displayWinMessage(); // Show a win message to players
        playWinSounds(); // Play sound effects for a win
        this.canPlace = false; // Prevent further tile placement after the game is over
    }

    /**
     * Handles the logic for a draw game, when no more moves are possible and there's no winner.
     */
    private void handleDraw() {
        displayDrawMessage(); // Show a draw message to players
        playDrawSounds(); // Play sound effects for a draw
        this.canPlace = false; // Prevent further tile placement after the game is over
    }

    /**
     * Highlights the winning line on the game board by turning the winning tiles into emerald blocks.
     */
    private void highlightWinningLine() {
        for (Coord c : winningLine) {
            Location loc = Data.BOARD.BOARD_POSITION.add(0, c.y, Data.BOARD.COLUMNS - c.z).toLocation(); // Get the location of each winning tile
            loc.getBlock().setType(Material.EMERALD_BLOCK); // Turn the tile into an emerald block
            // Spawn happy villager particles around the winning tile
            Data.CURRENT_WORLD.spawnParticle(Particle.HAPPY_VILLAGER, loc.add(0.5, 0.5, 0.5), 100, 0.5, 0.5, 0.5);
        }
    }

    /**
     * Displays the win message to the players, showing the name of the winning team.
     */
    private void displayWinMessage() {
        TeamColor winnerColor = currentTurnTeam.getTeamColor(); // Get the color of the winning team
        String title = ChatColor.DARK_GREEN + "-=" + winnerColor.getChatColor() + ChatColor.BOLD + "Player " + winnerColor.getColorName() + ChatColor.DARK_GREEN + "=-";
        String subtitle = ChatColor.DARK_GREEN + "-=" + ChatColor.GOLD + ChatColor.BOLD + "WIN" + ChatColor.RESET + ChatColor.DARK_GREEN + "=-";
        sendTitleToPlayers(title, subtitle); // Send the win message to nearby players
    }

    /**
     * Displays a draw message to the players when the game ends in a tie.
     */
    private void displayDrawMessage() {
        String title = ChatColor.DARK_AQUA + "-=" + ChatColor.GOLD + ChatColor.BOLD + "DRAW" + ChatColor.DARK_AQUA + "=-";
        sendTitleToPlayers(title, title); // Send the draw message to players
    }

    /**
     * Plays a series of sound effects to indicate a win.
     */
    private void playWinSounds() {
        playSounds(Sound.UI_TOAST_CHALLENGE_COMPLETE, Sound.ENTITY_PLAYER_LEVELUP, Sound.ENTITY_VILLAGER_YES);
    }

    /**
     * Plays sound effects for a draw game.
     */
    private void playDrawSounds() {
        playSounds(Sound.UI_TOAST_CHALLENGE_COMPLETE, Sound.ENTITY_VILLAGER_NO, Sound.BLOCK_NOTE_BLOCK_BIT);
    }

    /**
     * Plays a series of sounds at the center location of the game world.
     *
     * @param sounds The sound effects to play
     */
    private void playSounds(Sound... sounds) {
        for (Sound sound : sounds) {
            SoundManager.playSound(CENTER_LOCATION, sound, SoundCategory.AMBIENT); // Play the sounds in the specified location
        }
    }

    /**
     * Sends a title message to all players within a certain distance from the center of the game.
     *
     * @param title    The main title to display
     * @param subtitle The subtitle to display
     */
    private void sendTitleToPlayers(String title, String subtitle) {
        List<Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getLocation().distance(CENTER_LOCATION) <= 15) // Get players within 10 blocks of the center location
                .map(OfflinePlayer::getPlayer)
                .toList();

        players.forEach(p -> p.sendTitle(title, subtitle, 20, 200, 20)); // Send the title to each player
    }

    /**
     * Checks if the current team has won the game by completing a line of 4 tiles.
     *
     * @return True if the current team has won, otherwise false
     */
    private boolean checkWin() {
        winningLine.clear(); // Clear any previous winning line
        int tileValue = getCurrentTeamValue(); // Get the current team's tile value

        // Check every tile on the board for a winning condition
        for (int i = 0; i < Data.BOARD.COLUMNS; i++) {
            for (int j = 0; j < Data.BOARD.ROWS; j++) {
                if (board[i][j] == tileValue && (checkLine(i, j) || checkDiagonal(i, j))) {
                    return true; // Return true if a winning line is found
                }
            }
        }
        return false; // Return false if no winning line is found
    }

    /**
     * Checks for a horizontal or vertical line of 4 matching tiles starting from a given position.
     *
     * @param x The column index to start checking from
     * @param y The row index to start checking from
     * @return True if a line is found, otherwise false
     */
    boolean checkLine(int x, int y) {
        winningLine.clear(); // Clear the previous winning line
        boolean row = false;
        boolean column = false;
        int tileValue = getCurrentTeamValue(); // Get the current team's tile value

        // Check for a horizontal line
        if (Data.BOARD.COLUMNS - x >= WINNING_COUNT) {
            row = true;
            for (int i = 0; i < WINNING_COUNT; i++) {
                if (getTile(x + i, y) != tileValue) { // Check if the tiles match
                    row = false; // If not, break the loop
                    break;
                }
            }

            if (row) {
                // If a horizontal line is found, store the coordinates of the winning tiles
                for (int i = 0; i < WINNING_COUNT; i++) {
                    winningLine.add(new Coord(0, y, x + i));
                }
            }
        }

        // Check for a vertical line
        if (Data.BOARD.ROWS - y >= WINNING_COUNT) {
            column = true;
            for (int i = 0; i < WINNING_COUNT; i++) {
                if (getTile(x, y + i) != tileValue) {
                    column = false; // If not, break the loop
                    break;
                }
            }

            if (column) {
                // If a vertical line is found, store the coordinates of the winning tiles
                for (int i = 0; i < WINNING_COUNT; i++) {
                    winningLine.add(new Coord(0, y + i, x));
                }
            }
        }

        return column || row; // Return true if either a horizontal or vertical line is found
    }

    /**
     * Checks for a diagonal line of WINNING_COUNT matching tiles starting from a given position.
     *
     * @param x The column index to start checking from
     * @param y The row index to start checking from
     * @return True if a diagonal line is found, otherwise false
     */
    boolean checkDiagonal(int x, int y) {
        winningLine.clear(); // Clear the previous winning line
        boolean diagonalPrincipal = false;
        boolean diagonalSecundaria = false;

        // Check the main diagonal (top-left to bottom-right)
        if (Data.BOARD.COLUMNS - x >= WINNING_COUNT && Data.BOARD.ROWS - y >= WINNING_COUNT) {
            diagonalPrincipal = true;
            for (int i = 0; i < WINNING_COUNT; i++) {
                if (getTile(x + i, y + i) != getCurrentTeamValue()) {
                    diagonalPrincipal = false; // If the tiles don't match, break the loop
                    break;
                }
            }

            if (diagonalPrincipal) {
                // If the main diagonal is found, store the coordinates of the winning tiles
                for (int i = 0; i < WINNING_COUNT; i++) {
                    winningLine.add(new Coord(0, y + i, x + i));
                }
            }
        }

        // Check the secondary diagonal (top-right to bottom-left)
        if (x >= 3 && Data.BOARD.ROWS - y >= WINNING_COUNT) {
            diagonalSecundaria = true;
            for (int i = 0; i < WINNING_COUNT; i++) {
                if (getTile(x - i, y + i) != getCurrentTeamValue()) {
                    diagonalSecundaria = false; // If the tiles don't match, break the loop
                    break;
                }
            }

            if (diagonalSecundaria) {
                // If the secondary diagonal is found, store the coordinates of the winning tiles
                for (int i = 0; i < WINNING_COUNT; i++) {
                    winningLine.add(new Coord(0, y + i, x - i));
                }
            }
        }

        return diagonalPrincipal || diagonalSecundaria; // Return true if either diagonal is found
    }

    /**
     * Checks if the game board is full, meaning no more tiles can be placed.
     *
     * @return True if the board is full, otherwise false
     */
    private boolean isBoardFull() {
        for (int i = 0; i < Data.BOARD.COLUMNS; i++) {
            if (board[i][Data.BOARD.ROWS - 1] == 0) {
                return false; // Return false if there's still space in the column
            }
        }
        return true; // Return true if the board is completely full
    }

    /**
     * Checks if the specified column is available for placing a tile.
     *
     * @param i The column index
     * @return True if the column is available, otherwise false
     */
    private boolean isColumnAvailable(int i) {
        return board[i][Data.BOARD.ROWS - 1] == 0; // Check if the top-most row of the column is empty
    }

    /**
     * Gets the value representing the current team (1 for team A, 2 for team B).
     *
     * @return The current team's value (1 or 2)
     */
    private int getCurrentTeamValue() {
        return (currentTurnTeam.equals(Data.Teams.MANAGER.getTeamA())) ? 1 : 2;
    }

    /**
     * Summons a falling block (sand) at a specific position, representing the tile being placed.
     *
     * @param offset    The vertical offset for the falling block
     * @param colorName The color of the block to be placed
     */
    private void summonFallingSand(int offset, String colorName) {
        Location fallingBlockLocation = Data.BOARD.BOARD_POSITION.add(0.5, 7, offset + 0.5).toLocation(); // Location where the falling block will appear
        System.out.println(fallingBlockLocation);
        Material material = Material.valueOf((colorName + "_concrete_powder").toUpperCase()); // Set the material type based on the color name
        FallingBlock fallingBlock = Data.CURRENT_WORLD.spawnFallingBlock(fallingBlockLocation, material.createBlockData()); // Spawn the falling block
        fallingBlock.addScoreboardTag("4x4"); // Add a custom scoreboard tag for the block
        fallingBlock.setDropItem(false); // Prevent the falling block from dropping an item
    }
}