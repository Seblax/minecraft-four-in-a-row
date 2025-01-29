package org.seblax.utils;

import org.bukkit.*;

public class SoundManager {
    public static class  Player{

        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed){
            player.playSound(location, sound, soundCategory, volume, pitch, seed);
        }

        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch){
            playSound(player, location, sound, soundCategory, volume, pitch, 0);
        }

        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory, float volume){
            playSound(player, location, sound, soundCategory, volume, 1, 0);
        }

        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound, SoundCategory soundCategory){
            playSound(player, location, sound, soundCategory, 1, 1, 0);
        }

        public static void playSound(org.bukkit.entity.Player player, Location location, Sound sound){
            playSound(player, location, sound, SoundCategory.AMBIENT, 1, 1, 0);
        }

        public static void playSound(org.bukkit.entity.Player player, Sound sound){
            playSound(player, player.getLocation(), sound, SoundCategory.AMBIENT, 1, 1, 0);
        }


        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed){
            player.playSound(player.getLocation(), sound, soundCategory, volume, pitch, seed);
        }

        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory, float volume, float pitch){
            playSound(player, player.getLocation(), sound, soundCategory, volume, pitch, 0);
        }

        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory, float volume){
            playSound(player, player.getLocation(), sound, soundCategory, volume, 1, 0);
        }

        public static void playSound(org.bukkit.entity.Player player, Sound sound, SoundCategory soundCategory){
            playSound(player, player.getLocation(), sound, soundCategory, 1, 1, 0);
        }
    }

    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed){
        world.playSound(location, sound, soundCategory, volume, pitch, seed);
    }

    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch){
        playSound(world,location, sound, soundCategory, volume, pitch, 0);
    }

    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory, float volume){
        playSound(world,location, sound, soundCategory, volume, 1, 0);
    }

    public static void playSound(World world, Location location, Sound sound, SoundCategory soundCategory){
        playSound(world,location, sound, soundCategory, 1, 1, 0);
    }

    public static void playSound(World world, Location location, Sound sound){
        playSound(world,location, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }

    public static void playSound(World world, Sound sound){
        Location loc = new Location(world,0,0,0);
        playSound(world,loc, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }

    public static void playSound(Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch, long seed){
        playSound(Bukkit.getWorlds().get(0),location, sound, soundCategory, volume, pitch, seed);
    }

    public static void playSound(Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch){
        playSound(Bukkit.getWorlds().get(0),location, sound, soundCategory, volume, pitch, 0);
    }

    public static void playSound(Location location, Sound sound, SoundCategory soundCategory, float volume){
        playSound(Bukkit.getWorlds().get(0),location, sound, soundCategory, volume, 1, 0);
    }

    public static void playSound(Location location, Sound sound, SoundCategory soundCategory){
        playSound(Bukkit.getWorlds().get(0),location, sound, soundCategory, 1, 1, 0);
    }

    public static void playSound(Location location, Sound sound){
        playSound(Bukkit.getWorlds().get(0),location, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }

    public static void playSound(Sound sound){
        World world  = Bukkit.getWorlds().get(0);
        Location loc = new Location(Bukkit.getWorlds().get(0),0,0,0);
        playSound(world,loc, sound, SoundCategory.AMBIENT, 1, 1, 0);
    }
}
