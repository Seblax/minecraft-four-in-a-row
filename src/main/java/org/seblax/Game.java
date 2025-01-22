package org.seblax;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    int player;
    int[][] board = new int[7][6];

    public Game(){
        System.out.println("======NEW GAME======");
        this.player = Math.random() > 0 ? 1 : 2;
        System.out.println("First player: " + this.player);
        NewBoard();
        System.out.println(this);
    }

    public void NewBoard(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void SetCasilla(int x, int value){
        int position = -43 - x;
        for(int j = 0; j < 6; j++) {
            if(board[position][j] == 0){
                board[position][j] = value;
                System.out.println(this);
                return;
            }
        }
        System.out.println(this);
    }

    public int GetCasilla(int x, int y) {
        return board[x][y];
    }

    public void NextRound(){
        if(Win() || FullWall()){
            ResetGame();
        }

        this.player = this.player == 1 ? 2 : 1;
    }

    public void ResetGame(){
    }

    boolean Win(){
        for (int i = 0; i < 7; i ++)
        {
            for (int j = 0; j < 6; j ++)
            {
                if(GetCasilla(i,j) == this.player){
                    CheckFiles(i,j);
                    CheckDiagonales(i,j);
                }
            }
        }
        return false;
    }

    boolean CheckFiles(int x, int y){
        boolean res = true;

        for (int i = x; i < x+4; i ++){
            res = res & (GetCasilla(i,y) == this.player);
        }


        for (int j = y; j < y+4; j ++){
            res = res & (GetCasilla(x,j) == this.player);
        }

        return res;
    }

    boolean CheckDiagonales(int x, int y){
        return false;
    }

    //Detectar si los bloques de -232 74 -49 a -232 74 -43 son aire;
    boolean FullWall(){
        boolean res = true;

        for (int i = 0; i < 7 ; i++){
            res = res & board[i][5] != 0;
        }

        return res;
    }

    @Override
    public String toString(){
        String res = "";
        for (int j = 0; j < 6; j ++)
        {
            for (int i = 0; i < 7; i ++)
            {
                res += board[i][5-j];
            }
            res += "\n";
        }

        return res;
    }
}
