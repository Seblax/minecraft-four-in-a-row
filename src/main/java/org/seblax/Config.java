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

//package org.seblax;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.World;
//import org.seblax.utils.FileData;
//import org.seblax.utils.types.Coord;
//
//public class Config {
//    private static FileData file;
//
//    Config(){
//        file = new FileData("config.yml", Four.getInstance().getDataFolder());
//
//        if(file.isEmpty()){
//            LoadDefaultConfig();
//        }
//    }
//
//    public static void initialize(){
//        new Config();
//    }
//
//    private void LoadDefaultConfig(){
//        setTeam(1);
//        setTeam(2);
//        setBoard();
//        setWorld("world");
//
//        setLocation("exitPosition", Coord.zero());
//    }
//
//    //Location
//    static public void setLocation(String path, Coord coord){
//        setLocation(path, coord.x, coord.z, coord.y ,coord.yaw);
//    }
//
//    static public void setLocation(String path, Location location){
//        setLocation(path, location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw());
//    }
//
//    static public void setLocation(String path, double x, double y, double z, double yaw){
//        String xkey = path + ".x";
//        String ykey = path + ".y";
//        String zkey = path + ".z";
//        String yawkey = path + ".yaw";
//
//        file.setDouble(xkey, x);
//        file.setDouble(ykey, y);
//        file.setDouble(zkey, z);
//        file.setDouble(yawkey, yaw);
//    }
//
//    static public Coord getCoord(String path){
//        String xkey = path + ".x";
//        String ykey = path + ".y";
//        String zkey = path + ".z";
//        String yawkey = path + ".yaw";
//
//        double x = file.getDouble(xkey);
//        double y = file.getDouble(ykey);
//        double z = file.getDouble(zkey);
//        double yaw = file.getDouble(yawkey);
//
//        return new Coord(x,y,z, (float) yaw);
//    }
//
//    static public Location getLocation(String path){
//        return getCoord(path).toLocation();
//    }
//
//    //Color
//    static public void setColor(int teamID, Integer value){
//        String key = "team" + teamID + ".color";
//        file.set(key, value);
//    }
//
//    static public int getColor(int teamID){
//        String key = "team" + teamID + ".color";
//        return (int)file.get(key);
//    }
//
//    //world
//    static public void setWorld(String worldName){
//        String key = "world";
//        file.set(key, worldName);
//    }
//
//    static public World getWorld(){
//        String key = "world";
//        return Bukkit.getWorlds().stream()
//                .filter(x -> x.getName().equals(file.get(key)))
//                .findFirst()
//                .get();
//    }
//
//    //team
//    void setTeam(int teamID){
//        String key = String.format("team%s.",teamID);
//
//        setLocation(key + "startPosition", Coord.zero());
//        setLocation(key + "armorStandPosition", Coord.zero());
//        setColor(teamID, teamID);
//    }
//
//    //Board
//    void setBoard(){
//        String key = "board.";
//        file.set(key + "columns", 7);
//        file.set(key + "rows", 6);
//        setLocation(key + "position", Coord.zero());
//    }
//
//    public static int getColums(){
//        return file.getInt("board.columns");
//    }
//
//    public static int getRows(){
//        return file.getInt("board.rows");
//    }
//}
