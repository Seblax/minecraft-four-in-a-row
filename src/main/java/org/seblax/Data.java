package org.seblax;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.seblax.team.Team;
import org.seblax.utils.Coord;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static World CurrentWorld;
    public static String Heads = "_shulker_box";


    static public Coord[] ScenarioP1 = {
            new Coord(-255 , 64, -37),
            new Coord(-221, 75, -69),
            new Coord(-256 ,70, -57),
            new Coord(-256, 64, -52),
            new Coord(-223, 105, -53),
            new Coord(-222, 96, -39)
    };

    static public Coord[] ScenarioP2 = {
            new Coord(-255, 64, -55),
            new Coord(-221, 75, -23),
            new Coord(-256, 70, -35),
            new Coord(-256, 64, -40),
            new Coord(-223, 105, -53),
            new Coord(-222, 96, -39)
    };

    static public List<String> ScenarioBlocks = new ArrayList<String>() {{
        add("_wool");
        add("_concrete_powder");
        add("_concrete");
        add("_carpet");
        add("_terracotta");
        add("_shulker_box");
    }};

     public static Team team1;
     public static Team team2;

    public static void Initialize(){
        World world = Bukkit.getWorld("TNT 3 - Rework"); // Obtén el mundo donde se usarán los ArmorStands
        if (world == null) {
            System.out.println("El mundo no está disponible.");
            return;
        }

        Data.CurrentWorld = world;

        Data.team1 = new Team(new Coord(-245.5d, 65d, -51.5d), "Player 1", ScenarioP1);
        Data.team2 = new Team(new Coord(-245.5d, 65d, -39.5d), "Player 2", ScenarioP2);
    }
}
