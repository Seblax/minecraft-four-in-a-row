package org.seblax;

import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.seblax.team.Team;
import org.seblax.team.TeamColor;
import org.seblax.utils.Coord;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Team currentTurnTeam;
    int[][] board = new int[7][6];
    List<Coord> winningLine = new ArrayList<>();
    boolean canPlace = true;

    public Game(){
        this.currentTurnTeam = Math.random() > 0.5 ?
                Data.Teams.manager.teamA : Data.Teams.manager.teamB;
        newBoard();
    }

    public void newBoard(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void clearBoard(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++) {
                Location loc = new Location(Data.CURRENT_WORLD, -232, 69+j, -43-i);
                loc.getBlock().setType(Material.AIR);
            }
        }
    }

    public boolean setTile(int x, int offset, String colorName){
        if(!checkColum(x)) return false;

        for(int j = 0; j < 6; j++) {
            if(board[x][j] == 0){
                summonFallingSand(offset, colorName);
                board[x][j] = getCurrentTeamValue();
                return true;
            }
        }
        return true;
    }

    public int getTile(int x, int y) {
        return board[x][y];
    }

    public void NextRound(){
        if(Win()){
            GameWon();
        }else if(FullWall()){
            GameDraw();
        }

        this.canPlace = true;
        this.currentTurnTeam = Data.Teams.manager.getEnemyTeamByUUID(this.currentTurnTeam);
    }

    void GameWon(){
        for (Coord c : winningLine){
            Location loc = new Location(Data.CURRENT_WORLD, -232, 69+c.y, -43-c.z);
            loc.getBlock().setType(Material.EMERALD_BLOCK);
            Data.CURRENT_WORLD.spawnParticle(Particle.HAPPY_VILLAGER, loc.add(0.5,0.5,0.5), 100,0.5,0.5,0.5);
            Data.CURRENT_WORLD.playSound(loc,Sound.ENTITY_PLAYER_LEVELUP,SoundCategory.AMBIENT,1,(float)(Math.random()));
        }

        TeamColor winnerColor = currentTurnTeam.getTeamColor();

        String title = ChatColor.DARK_GREEN + "-=" +
                winnerColor.getChatColor() + ChatColor.BOLD + "Player " + winnerColor.getColorName()
                + ChatColor.DARK_GREEN + "=-";
        String subtitle = ChatColor.DARK_GREEN + "-=" +
                ChatColor.GOLD + ChatColor.BOLD + "WIN"
                + ChatColor.DARK_GREEN + "=-";

        EndTitle(title,subtitle);
        this.canPlace = false;
    }

    void GameDraw(){
        Location loc = new Coord(-232, 71, -46).toLocation();
        Data.CURRENT_WORLD.spawnParticle(Particle.HAPPY_VILLAGER, loc.add(0.5,0.5,0.5), 100,0.5,3,3);
        Data.CURRENT_WORLD.playSound(loc,Sound.ENTITY_PLAYER_LEVELUP,SoundCategory.AMBIENT,1,0f);

        String title = ChatColor.GOLD + "-=" +
                ChatColor.DARK_AQUA + ChatColor.BOLD + "DRAW"
                + ChatColor.GOLD + "=-";
        EndTitle(title,title);

        this.canPlace = false;
    }

    void EndTitle(String title, String subtitle){
        Location center = new Coord(-232, 68, -46).toLocation();

        List<Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(x -> x.getLocation().distance(center) <= 10)
                .map(OfflinePlayer::getPlayer)
                .toList();

        for(Player p: players){
            p.sendTitle(title, subtitle, 20,10*20, 20);
        }

    }

    boolean Win(){
        winningLine.clear();

        int tileValue = getCurrentTeamValue();

        for (int i = 0; i < 7; i ++)
        {
            for (int j = 0; j < 6; j ++)
            {
                if(getTile(i,j) == tileValue){
                    if (checkLine(i,j)) return true;
                    if (checkDiagonal(i,j)) return true;
                }
            }
        }
        return false;
    }

    boolean checkLine(int x, int y) {
        winningLine.clear();
        boolean row = false;
        boolean column = false;
        int tileValue = getCurrentTeamValue();

        if (7 - x >= 4) {
            row = true;
            for (int i = 0; i < 4; i++) {
                if (getTile(x + i, y) != tileValue) {
                    row = false;
                    break;
                }
            }

            if (row) {
                for (int i = 0; i < 4; i++) {
                    winningLine.add(new Coord(0, y,x + i));
                }
            }
        }

        if (6 - y >= 4) {
            column = true;
            for (int i = 0; i < 4; i++) {
                if (getTile(x, y + i) != tileValue) {
                    column = false;
                    break;
                }
            }

            if (column) {
                for (int i = 0; i < 4; i++) {
                    winningLine.add(new Coord(0, y + i,x));
                }
            }
        }

        return column || row;
    }


    boolean checkDiagonal(int x, int y) {
        winningLine.clear();
        boolean diagonalPrincipal = false;
        boolean diagonalSecundaria = false;

        if (7 - x >= 4 && 6 - y >= 4) {
            diagonalPrincipal = true;
            for (int i = 0; i < 4; i++) {
                if (getTile(x + i, y + i) !=  getCurrentTeamValue()) {
                    diagonalPrincipal = false;
                    break;
                }
            }

            if (diagonalPrincipal) {
                for (int i = 0; i < 4; i++) {
                    winningLine.add(new Coord(0, y + i,x + i));
                }
            }
        }

        if (x >= 3 && 6 - y >= 4) {
            diagonalSecundaria = true;
            for (int i = 0; i < 4; i++) {
                if (getTile(x - i, y + i) !=  getCurrentTeamValue()) {
                    diagonalSecundaria = false;
                    break;
                }
            }

            if (diagonalSecundaria) {
                for (int i = 0; i < 4; i++) {
                    winningLine.add(new Coord(0, y + i,x - i));
                }
            }
        }

        return diagonalPrincipal || diagonalSecundaria;
    }


    //Detectar si los bloques de -232 74 -49 a -232 74 -43 son aire;
    boolean FullWall(){
        boolean res = true;

        for (int i = 0; i < 7 ; i++){
            res = res & board[i][5] != 0;
        }

        return res;
    }

    boolean checkColum(int i){
        return board[i][5] == 0;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < 6; j ++)
        {
            for (int i = 0; i < 7; i ++)
            {
                res.append(board[i][5 - j]);
            }
            res.append("\n");
        }

        return res.toString();
    }

    int getCurrentTeamValue(){
        return this.currentTurnTeam.equals(Data.Teams.manager.teamA) ? 1 : 2;

    }

    void summonFallingSand(int offset, String colorName){
        Coord fallingBlockLocation = new Coord(-231.5, 76., offset + 0.5);
        Material fallingBlockMaterial = Material.valueOf((colorName + "_concrete_powder").toUpperCase());
        FallingBlock fallingBlock = Data.CURRENT_WORLD.spawnFallingBlock(fallingBlockLocation.toLocation(), fallingBlockMaterial.createBlockData());

        Color particleColor = this.currentTurnTeam.getTeamColor().getColor();

        Data.CURRENT_WORLD.spawnParticle(Particle.DUST, fallingBlockLocation.toLocation(), 100,0.5,0.5,0.5, new Particle.DustOptions(particleColor, (float) (1 + 5*Math.random())));

        fallingBlock.addScoreboardTag("4x4");
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(true);
    }
}
