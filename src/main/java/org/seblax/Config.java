package org.seblax;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.seblax.utils.FileData;
import org.seblax.utils.types.Coord;

public class Config {
    private static FileData file;

    /**
     * Initializes the configuration file and loads default values if necessary.
     */
    public static void initialize() {
        if (file == null) {
            file = new FileData("config.yml", Four.getInstance().getDataFolder());
        }

        if (file.isEmpty()) {
            loadDefaultConfig();
        }
    }

    /**
     * Loads the default configuration values.
     */
    private static void loadDefaultConfig() {
        setWorld("world");
        setLocation("exitPosition", Coord.zero());

        for (int i = 1; i <= 2; i++) {
            setTeam(i);
        }

        setBoard();
    }

    /**
     *  Location Handling.
     */
    public static void setLocation(String path, Coord coord) {
        setLocation(path, coord.x, coord.y, coord.z, coord.yaw);
    }

    public static void setLocation(String path, Location location) {
        setLocation(path, location.getX(), location.getY(), location.getZ(), location.getYaw());
    }

    public static void setLocation(String path, double x, double y, double z, double yaw) {
        ensureFileLoaded();
        file.set(path + ".x", x);
        file.set(path + ".y", y);
        file.set(path + ".z", z);
        file.set(path + ".yaw", yaw);
    }

    public static Coord getCoord(String path) {
        ensureFileLoaded();
        if (!file.contains(path)) {
            Bukkit.getLogger().warning("[Config] Missing coordinate: " + path);
            return Coord.zero();
        }
        return new Coord(
                file.getDouble(path + ".x"),
                file.getDouble(path + ".y"),
                file.getDouble(path + ".z"),
                file.getDouble(path + ".yaw").floatValue()
        );
    }

    public static Location getLocation(String path) {
        return getCoord(path).toLocation();
    }

    /**
     *  Team Color Handling.
     */
    public static void setColor(int teamID, int value) {
        ensureFileLoaded();
        file.set("team" + teamID + ".color", value);
    }

    public static int getColor(int teamID) {
        ensureFileLoaded();
        Object color = file.get("team" + teamID + ".color");
        if (color instanceof Integer) {
            return (int) color;
        }
        Bukkit.getLogger().warning("[Config] Invalid team color format for team " + teamID);
        return 0; // Default fallback color
    }

    /**
     *  World Handling.
     */
    public static void setWorld(String worldName) {
        ensureFileLoaded();
        file.set("world", worldName);
    }

    public static World getWorld() {
        ensureFileLoaded();
        String worldName = (String) file.get("world");
        if (worldName == null) {
            Bukkit.getLogger().warning("[Config] World name is not set in config!");
            return null;
        }
        return Bukkit.getWorld(worldName);
    }

    /**
     *  Team Set Up.
     */
    private static void setTeam(int teamID) {
        String key = "team" + teamID + ".";
        setLocation(key + "startPosition", Coord.zero());
        setLocation(key + "armorStandPosition", Coord.zero());
        setColor(teamID, teamID);
    }

    /**
     *  Board Configuration.
     */
    private static void setBoard() {
        String key = "board.";
        ensureFileLoaded();
        file.set(key + "columns", 7);
        file.set(key + "rows", 6);
        setLocation(key + "position", Coord.zero());
    }

    public static int getColumns() {
        ensureFileLoaded();
        return file.getInt("board.columns");
    }

    public static int getRows() {
        ensureFileLoaded();
        return file.getInt("board.rows");
    }

    /**
     * Ensures that the configuration file is loaded before accessing it.
     */
    private static void ensureFileLoaded() {
        if (file == null) {
            initialize();
        }
    }
}