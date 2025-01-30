package org.seblax.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileData {

    private final YamlConfiguration modyFile;
    public static File plugin;

    File file;
    public boolean exist;

    public FileData(String f){

        File dataFolder = plugin;

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        this.file =  new File(plugin, f);

        this.exist = file.exists();

        if(!this.exist){
            try{
                 file.createNewFile();
            }catch (IOException e){
                System.out.println("Can't load file! Error: " + e);
            }
        }

        this.modyFile = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String k, Object v) {
        this.modyFile.set(k,v);
        try {
            this.modyFile.save(this.file);
        }catch (IOException e){
            System.out.println("Error saving file!");
        }
    }

    public Object get(String k) {
        return this.modyFile.get(k);
    }
}
