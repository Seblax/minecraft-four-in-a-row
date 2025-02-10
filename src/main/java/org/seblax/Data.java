package org.seblax;

import org.bukkit.World;
import org.seblax.team.TeamsManager;
import org.seblax.utils.types.Coord;

import java.util.List;

/**
 * This class holds all the global static data related to the game world, team configurations, and scenario coordinates.
 * It serves as a container for various game settings, constants, and static references that are used throughout the game.
 */
public class Data {

    public static World CURRENT_WORLD = Config.getWorld();  // The current world instance where the game takes place
    public static String TEAM_HEAD_ITEM = "_shulker_box";  // The item type for team heads (used in the game)

    /**
     * Nested class that contains board dimensions used for the game grid.
     */
    public static class BOARD {
        public static int COLUMNS = Config.getColums();  // The number of columns in the game board
        public static int ROWS = Config.getRows();     // The number of rows in the game board

        public static Coord BOARD_POSITION = Config.getCoord("board.position");
    }

    /**
     * Nested class that contains the coordinates for the scenario setup of each team and block types used in the game.
     * These values define the scenario structures in the world for each team and how they are configured.
     */
    public static class SCENARIO_TEAM {

        // Coordinates for Team A's scenario setup
        public static Coord[] A = {
                new Coord(-255, 64, -37),
                new Coord(-221, 75, -69),
                new Coord(-256, 70, -57),
                new Coord(-256, 64, -52),
                new Coord(-223, 105, -53),
                new Coord(-222, 96, -39)
        };

        // Coordinates for Team B's scenario setup
        public static Coord[] B = {
                new Coord(-255, 64, -55),
                new Coord(-221, 75, -23),
                new Coord(-256, 70, -35),
                new Coord(-256, 64, -40),
                new Coord(-223, 105, -53),
                new Coord(-222, 96, -39)
        };

        // List of block types used for creating the scenario for each team
        public static final List<String> BLOCKS = List.of(
                "_wool",               // Wool blocks
                "_concrete_powder",    // Concrete powder blocks
                "_concrete",           // Concrete blocks
                "_carpet",             // Carpet blocks
                "_terracotta",         // Terracotta blocks
                "_shulker_box"         // Shulker box blocks
        );
    }

    /**
     * Nested class that defines the start and armor stand coordinates for both teams.
     * It also holds a reference to the manager responsible for handling teams.
     */
    public static class Teams {

        public static class A{
            // Starting coordinates for Team A
            public static final Coord TEAM_START_COORD = Config.getCoord("team1.startPosition"); //new Coord(-237.5, 68.0, -45.5, -90);

            // Coordinates for the armor stand of Team A
            public static final Coord TEAM_ARMORSTAND_COORD = Config.getCoord("team1.armorStandPosition"); //(new Coord(-245.5, 65.0, -51.5);
        }

        public static class B{
            // Starting coordinates for Team B
            public static final Coord TEAM_START_COORD = Config.getCoord("team2.startPosition"); //(new Coord(-225.5, 68.0, -45.5, 90);

            // Coordinates for the armor stand of Team B
            public static final Coord TEAM_ARMORSTAND_COORD = Config.getCoord("team2.armorStandPosition"); //( new Coord(-245.5, 65.0, -39.5);
        }

        // Coordinates for where teams will exit
        public static final Coord TEAMS_EXIT_COORD = Config.getCoord("exitPosition"); //(new Coord(-253.5, 65.0, -45.5, -90);

        public static TeamsManager MANAGER;  // The manager responsible for handling the teams in the game
    }
}

//package org.seblax;
//
//import org.bukkit.World;
//import org.seblax.team.TeamsManager;
//import org.seblax.utils.types.Coord;
//
//import java.util.List;
//
//public class Data {
//    public static World CURRENT_WORLD;
//    public static String WORLD_PATH_FOLDER = "TNT 3 - Rework";
//    public static String TEAM_HEAD_ITEM = "_shulker_box";
//
//    public static  class BOARD{
//        public static int COLUMNS = 7;
//        public static int ROWS = 6;
//    }
//
//    public static class SCENARIO_TEAM {
//        public static Coord[] A = {
//                new Coord(-255, 64, -37),
//                new Coord(-221, 75, -69),
//                new Coord(-256, 70, -57),
//                new Coord(-256, 64, -52),
//                new Coord(-223, 105, -53),
//                new Coord(-222, 96, -39)
//        };
//
//        public static Coord[] B = {
//                new Coord(-255, 64, -55),
//                new Coord(-221, 75, -23),
//                new Coord(-256, 70, -35),
//                new Coord(-256, 64, -40),
//                new Coord(-223, 105, -53),
//                new Coord(-222, 96, -39)
//        };
//
//        public static final List<String> BLOCKS = List.of(
//                "_wool",
//                "_concrete_powder",
//                "_concrete",
//                "_carpet",
//                "_terracotta",
//                "_shulker_box"
//        );
//    }
//
//    public static class Teams {
//        public static final Coord TEAM_A_START_COORD = new Coord(-237.5, 68.0, -45.5, -90);
//        public static final Coord TEAM_B_START_COORD = new Coord(-225.5, 68.0, -45.5, 90);
//        public static final Coord TEAMS_EXIT_COORD = new Coord(-253.5, 65.0, -45.5, -90);
//
//        public static final Coord TEAM_A_ARMORSTAND_COORD = new Coord(-245.5, 65.0, -51.5);
//        public static final Coord TEAM_B_ARMORSTAND_COORD = new Coord(-245.5, 65.0, -39.5);
//
//        public static TeamsManager manager;
//    }
//}
