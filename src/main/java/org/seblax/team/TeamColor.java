package org.seblax.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Enum representing the different team colors.
 * Each color has an associated value, chat color, RGB color, and color name.
 */
public enum TeamColor {
    White(0),      // White team color
    Red(1),        // Red team color
    Orange(2),     // Orange team color
    Yellow(3),     // Yellow team color
    Green(4),      // Green team color
    Cyan(5),       // Cyan team color
    Blue(6),       // Blue team color
    Magenta(7);    // Magenta team color

    private final int value;

    // Constructor to associate a value with the color.
    TeamColor(final int newValue) {
        value = newValue;
    }

    // Getter for the color value.
    public int getValue() { return value; }

    /**
     * Returns the ChatColor associated with the team color.
     *
     * @return ChatColor for the team color.
     */
    public ChatColor getChatColor() {
        return switch (value) {
            case 0 -> ChatColor.WHITE;
            case 1 -> ChatColor.RED;
            case 2 -> ChatColor.GOLD;
            case 3 -> ChatColor.YELLOW;
            case 4 -> ChatColor.GREEN;
            case 5 -> ChatColor.AQUA;
            case 6 -> ChatColor.BLUE;
            case 7 -> ChatColor.LIGHT_PURPLE;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    /**
     * Returns the RGB color associated with the team color.
     *
     * @return Color object for the team color.
     */
    public Color getColor() {
        return switch (value) {
            case 0 -> Color.WHITE;
            case 1 -> Color.RED;
            case 2 -> Color.ORANGE;
            case 3 -> Color.YELLOW;
            case 4 -> Color.GREEN;
            case 5 -> Color.AQUA;
            case 6 -> Color.BLUE;
            case 7 -> Color.FUCHSIA;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    /**
     * Returns the name of the color as a string (used for naming purposes).
     *
     * @return String name of the team color.
     */
    public String getColorName() {
        return switch (value) {
            case 0 -> "white";
            case 1 -> "red";
            case 2 -> "orange";
            case 3 -> "yellow";
            case 4 -> "lime";
            case 5 -> "light_blue";
            case 6 -> "blue";
            case 7 -> "magenta";
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    /**
     * Returns a TeamColor from an integer ID (loops back if the ID exceeds 7).
     *
     * @param id The integer ID representing the team color.
     * @return Corresponding TeamColor based on the given ID.
     */
    public static TeamColor of(int id) {
        int colorID = id % 8;  // Ensures that the ID is within the range [0-7]
        for (TeamColor color : TeamColor.values()) {
            if (color.value == colorID) {
                return color;
            }
        }

        return White;  // Default color is White if no match is found
    }
}