package org.seblax.utils;

import org.bukkit.*;

/**
 * This class handles the playback of sounds in Minecraft.
 * It provides methods for playing sounds to a specific player, world, or location.
 */
public class SoundManager {

    public static class Player {

        /**
         * Plays a sound at a given location for a player with specific sound category, volume, pitch, and seed.
         */
        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed) {
            player.playSound(location, sound, soundCategory, volume, pitch, seed);
        }

        /**
         * Plays a sound at a given location for a player with specific sound category, volume, and pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch) {
            playSound(player, location, sound, soundCategory, volume, pitch, 0);
        }

        /**
         * Plays a sound at a given location for a player with specific sound category and volume, default pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory, float volume) {
            playSound(player, location, sound, soundCategory, volume, 1, 0);
        }

        /**
         * Plays a sound at a given location for a player with specific sound category, default volume and pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory) {
            playSound(player, location, sound, soundCategory, 1, 1, 0);
        }

        /**
         * Plays a sound at a given location for a player with default sound category, volume, and pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound) {
            playSound(player, location, sound, SoundCategory.AMBIENT, 1, 1, 0);
        }

        /**
         * Plays a sound at the player's current location with default sound category, volume, and pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Sound sound) {
            playSound(player, player.getLocation(), sound, SoundCategory.AMBIENT, 1, 1, 0);
        }

        /**
         * Plays a sound at the player's current location with a specified sound category, volume, pitch, and seed.
         */
        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed) {
            player.playSound(player.getLocation(), sound, soundCategory, volume, pitch, seed);
        }

        /**
         * Plays a sound at the player's current location with a specified sound category, volume, and pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory, float volume, float pitch) {
            playSound(player, player.getLocation(), sound, soundCategory, volume, pitch, 0);
        }

        /**
         * Plays a sound at the player's current location with a specified sound category and volume, default pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory, float volume) {
            playSound(player, player.getLocation(), sound, soundCategory, volume, 1, 0);
        }

        /**
         * Plays a sound at the player's current location with a specified sound category and default volume and pitch.
         */
        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory) {
            playSound(player, player.getLocation(), sound, soundCategory, 1, 1, 0);
        }
    }

    /**
     * Plays a sound at a specific location in a world with a given sound category, volume, pitch, and seed.
     */
    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed) {
        world.playSound(location, sound, soundCategory, volume, pitch, seed);
    }

    /**
     * Plays a sound at a specific location in a world with a given sound category, volume, and pitch.
     */
    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch) {
        playSound(world, location, sound, soundCategory, volume, pitch, 0);
    }

    /**
     * Plays a sound at a specific location in a world with a given sound category and volume, default pitch.
     */
    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory, float volume) {
        playSound(world, location, sound, soundCategory, volume, 1, 0);
    }

    /**
     * Plays a sound at a specific location in a world with a given sound category, default volume and pitch.
     */
    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory) {
        playSound(world, location, sound, soundCategory, 1, 1, 0);
    }

    /**
     * Plays a sound at a specific location in a world with default sound category, volume, and pitch.
     */
    public static void playSound(World world, Location location, Sound sound) {
        playSound(world, location, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }

    /**
     * Plays a sound at a default location (0, 0, 0) in a world with default sound category, volume, and pitch.
     */
    public static void playSound(World world, Sound sound) {
        Location loc = new Location(world, 0, 0, 0);
        playSound(world, loc, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }

    /**
     * Plays a sound at a given location in the first world with a specified sound category, volume, pitch, and seed.
     */
    public static void playSound(Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed) {
        playSound(Bukkit.getWorlds().get(0), location, sound, soundCategory, volume, pitch, seed);
    }

    /**
     * Plays a sound at a given location in the first world with a specified sound category, volume, and pitch.
     */
    public static void playSound(Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch) {
        playSound(Bukkit.getWorlds().get(0), location, sound, soundCategory, volume, pitch, 0);
    }

    /**
     * Plays a sound at a given location in the first world with a specified sound category and volume, default pitch.
     */
    public static void playSound(Location location, Sound sound, SoundCategory soundCategory, float volume) {
        playSound(Bukkit.getWorlds().get(0), location, sound, soundCategory, volume, 1, 0);
    }

    /**
     * Plays a sound at a given location in the first world with a specified sound category, default volume and pitch.
     */
    public static void playSound(Location location, Sound sound, SoundCategory soundCategory) {
        playSound(Bukkit.getWorlds().get(0), location, sound, soundCategory, 1, 1, 0);
    }

    /**
     * Plays a sound at a given location in the first world with default sound category, volume, and pitch.
     */
    public static void playSound(Location location, Sound sound) {
        playSound(Bukkit.getWorlds().get(0), location, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }

    /**
     * Plays a sound at the default location (0, 0, 0) in the first world with default sound category, volume, and pitch.
     */
    public static void playSound(Sound sound) {
        World world = Bukkit.getWorlds().get(0);
        Location loc = new Location(world, 0, 0, 0);
        playSound(world, loc, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }
}