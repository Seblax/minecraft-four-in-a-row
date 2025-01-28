package org.seblax;

import org.bukkit.World;
import org.seblax.team.TeamsManager;
import org.seblax.utils.Coord;

import java.util.List;

public class Data {
    public static World CURRENT_WORLD;
    public static String WORLD_PATH_FOLDER = "TNT 3 - Rework";
    public static String TEAM_HEAD_ITEM = "_shulker_box";

    public static class SCENARIO_TEAM {
        public static Coord[] A = {
                new Coord(-255, 64, -37),
                new Coord(-221, 75, -69),
                new Coord(-256, 70, -57),
                new Coord(-256, 64, -52),
                new Coord(-223, 105, -53),
                new Coord(-222, 96, -39)
        };

        public static Coord[] B = {
                new Coord(-255, 64, -55),
                new Coord(-221, 75, -23),
                new Coord(-256, 70, -35),
                new Coord(-256, 64, -40),
                new Coord(-223, 105, -53),
                new Coord(-222, 96, -39)
        };

        public static final List<String> BLOCKS = List.of(
                "_wool",
                "_concrete_powder",
                "_concrete",
                "_carpet",
                "_terracotta",
                "_shulker_box"
        );
    }

    public static class Teams {
        public static final Coord TEAM_A_START_COORD = new Coord(-237.5, 68.0, -45.5, -90);
        public static final Coord TEAM_B_START_COORD = new Coord(-225.5, 68.0, -45.5, 90);
        public static final Coord TEAMS_EXIT_COORD = new Coord(-253.5, 65.0, -45.5, -90);

        public static final Coord TEAM_A_ARMORSTAND_COORD = new Coord(-245.5, 65.0, -51.5);
        public static final Coord TEAM_B_ARMORSTAND_COORD = new Coord(-245.5, 65.0, -39.5);

        public static TeamsManager manager;
    }
}
