package org.seblax;

import org.seblax.utils.FileData;

public class Config {
    private static FileData file;

    Config(){
        file = new FileData("config.yml", Four.getInstance().getDataFolder());
    }

    public static Config initialize(){
        return new Config();
    }

    static public void setColor(int teamID, Integer value){
        String key = "team_" + teamID;
        file.set(key, value);
    }

    static public int getColor(int teamID){
        String key = "team_" + teamID;
        return (int)file.get(key);
    }
}
