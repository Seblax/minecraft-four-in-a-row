package org.seblax.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum TeamColor {
    White(0),
    Red(1),
    Orange(2),
    Yellow(3),
    Green(4),
    Cyan(5),
    Blue(6),
    Magenta(7);

    private final int value;

    TeamColor(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    public ChatColor getChatColor() { return switch (value){
        case 0 -> ChatColor.WHITE;
        case 1 -> ChatColor.RED;
        case 2 -> ChatColor.GOLD;
        case 3 -> ChatColor.YELLOW;
        case 4 -> ChatColor.GREEN;
        case 5 -> ChatColor.AQUA;
        case 6 -> ChatColor.BLUE;
        case 7 -> ChatColor.LIGHT_PURPLE;
        default -> throw new IllegalStateException("Unexpected value: " + value);
    }; }

    public Color getColor() { return switch (value){
        case 0 -> Color.WHITE;
        case 1 -> Color.RED;
        case 2 -> Color.ORANGE;
        case 3 -> Color.YELLOW;
        case 4 -> Color.GREEN;
        case 5 -> Color.AQUA;
        case 6 -> Color.BLUE;
        case 7 -> Color.FUCHSIA;
        default -> throw new IllegalStateException("Unexpected value: " + value);
    }; }

    public String getColorName() { return switch (value){
        case 0 -> "white";
        case 1 -> "red";
        case 2 -> "orange";
        case 3 -> "yellow";
        case 4 -> "lime";
        case 5 -> "light_blue";
        case 6 -> "blue";
        case 7 -> "magenta";
        default -> throw new IllegalStateException("Unexpected value: " + value);
    }; }

    public static TeamColor of(int id) {
        int colorID = id % 8;
        for (TeamColor color : TeamColor.values()) {
            if (color.value == colorID) {
                return color;
            }
        }

        return White;
    }
}