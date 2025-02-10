package org.seblax;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.seblax.utils.FileData;
import org.seblax.utils.types.Coord;

import java.util.UUID;

public class Config {
    private static FileData file;

    Config(){
        file = new FileData("config.yml", Four.getInstance().getDataFolder());

        if(file.isEmpty()){
            LoadDefaultConfig();
        }
    }

    public static void initialize(){
        new Config();
    }

    private void LoadDefaultConfig(){
        setTeam(1);
        setTeam(2);
        setBoard();
        setWorld("world");

        setLocation("exitPosition", Coord.zero());
    }

    //Location
    static public void setLocation(String path, Coord coord){
        setLocation(path, coord.x, coord.z, coord.y ,coord.yaw);
    }

    static public void setLocation(String path, Location location){
        setLocation(path, location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw());
    }

    static public void setLocation(String path, double x, double y, double z, double yaw){
        String xkey = path + ".x";
        String ykey = path + ".y";
        String zkey = path + ".z";
        String yawkey = path + ".yaw";

        file.setDouble(xkey, x);
        file.setDouble(ykey, y);
        file.setDouble(zkey, z);
        file.setDouble(yawkey, yaw);
    }

    static public Coord getCoord(String path){
        String xkey = path + ".x";
        String ykey = path + ".y";
        String zkey = path + ".z";
        String yawkey = path + ".yaw";

        double x = file.getDouble(xkey);
        double y = file.getDouble(ykey);
        double z = file.getDouble(zkey);
        double yaw = file.getDouble(yawkey);

        return new Coord(x,y,z, (float) yaw);
    }

    static public Location getLocation(String path){
        return getCoord(path).toLocation();
    }

    //Color
    static public void setColor(int teamID, Integer value){
        String key = "team" + teamID + ".color";
        file.set(key, value);
    }

    static public int getColor(int teamID){
        String key = "team" + teamID + ".color";
        return (int)file.get(key);
    }

    //world
    static public void setWorld(String worldName){
        String key = "world";
        file.set(key, worldName);
    }

    static public World getWorld(){
        String key = "world";
        return Bukkit.getWorlds().stream()
                .filter(x -> x.getName().equals(file.get(key)))
                .findFirst()
                .get();
    }

    //team
    void setTeam(int teamID){
        String key = String.format("team%s.",teamID);

        setLocation(key + "startPosition", Coord.zero());
        setLocation(key + "armorStandPosition", Coord.zero());
        setColor(teamID, teamID);
    }

    //Board
    void setBoard(){
        String key = "board.";
        file.set(key + "columns", 7);
        file.set(key + "rows", 6);
        setLocation(key + "position", Coord.zero());
    }

    public static int getColums(){
        return file.getInt("board.columns");
    }

    public static int getRows(){
        return file.getInt("board.rows");
    }
}
