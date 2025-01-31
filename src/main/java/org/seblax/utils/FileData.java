package org.seblax.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling YAML-based configuration files.
 */
public class FileData {
    private static final Logger LOGGER = Logger.getLogger(FileData.class.getName());

    private final YamlConfiguration configFile;
    private final File file;

    /**
     * Initializes a new FileData instance.
     *
     * @param fileName        The name of the YAML file.
     * @param pluginDirectory The directory where the file is stored.
     * @throws IllegalArgumentException if the plugin directory is null.
     */
    public FileData(String fileName, File pluginDirectory) {
        if (pluginDirectory == null) {
            throw new IllegalArgumentException("Plugin directory cannot be null.");
        }

        if (!pluginDirectory.exists() && !pluginDirectory.mkdirs()) {
            LOGGER.warning("Failed to create plugin directory: " + pluginDirectory.getAbsolutePath());
        }

        this.file = new File(pluginDirectory, fileName);

        // Create file if it does not exist
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    LOGGER.info("Created new file: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Cannot create file: " + file.getAbsolutePath(), e);
            }
        }

        this.configFile = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Checks if the file exists.
     *
     * @return true if the file exists, false otherwise.
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * Saves a key-value pair to the YAML file.
     *
     * @param key   The configuration key.
     * @param value The value to be stored.
     */
    public synchronized void set(String key, Object value) {
        configFile.set(key, value);
        save();
    }

    /**
     * Retrieves a value from the YAML file.
     *
     * @param key The configuration key.
     * @return The stored value, or null if the key is not found.
     */
    public synchronized Object get(String key) {
        return configFile.get(key);
    }

    /**
     * Retrieves a value from the YAML file with a default fallback.
     *
     * @param key          The configuration key.
     * @param defaultValue The default value to return if the key is not found.
     * @param <T>          The expected return type.
     * @return The stored value or the default if the key does not exist.
     */
    public synchronized <T> T getOrDefault(String key, T defaultValue) {
        Object value = configFile.get(key);
        return (value != null) ? (T) value : defaultValue;
    }

    /**
     * Saves the current state of the YAML file.
     */
    private synchronized void save() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving file: " + file.getAbsolutePath(), e);
        }
    }
}

//package org.seblax.utils;
//
//import org.bukkit.configuration.file.YamlConfiguration;
//
//import java.io.File;
//import java.io.IOException;
//
//public class FileData {
//
//    private final YamlConfiguration modyFile;
//    public static File plugin;
//
//    File file;
//    public boolean exist;
//
//    public FileData(String f){
//
//        File dataFolder = plugin;
//
//        if (!dataFolder.exists()) {
//            dataFolder.mkdirs();
//        }
//
//        this.file =  new File(plugin, f);
//
//        this.exist = file.exists();
//
//        if(!this.exist){
//            try{
//                 file.createNewFile();
//            }catch (IOException e){
//                System.out.println("Can't load file! Error: " + e);
//            }
//        }
//
//        this.modyFile = YamlConfiguration.loadConfiguration(file);
//    }
//
//    public void set(String k, Object v) {
//        this.modyFile.set(k,v);
//        try {
//            this.modyFile.save(this.file);
//        }catch (IOException e){
//            System.out.println("Error saving file!");
//        }
//    }
//
//    public Object get(String k) {
//        return this.modyFile.get(k);
//    }
//}
