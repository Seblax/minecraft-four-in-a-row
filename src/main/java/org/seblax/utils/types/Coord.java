package org.seblax.utils.types;

import org.bukkit.Location;
import org.seblax.Data;

/**
 * This class represents a 3D coordinate (x, y, z) and optionally a yaw value.
 * It extends from the `Triple` class, which holds three values of a specified type (in this case, `Double`).
 */
public class Coord extends Triple<Double> {
    // The yaw value, used to represent the horizontal rotation of the coordinate.
    public float yaw = 0f;

    /**
     * Constructor to create a Coord object with x, y, and z values.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public Coord(Double x, Double y, Double z) {
        super(x, y, z);  // Calls the parent constructor of the Triple class.
    }

    /**
     * Constructor to create a Coord object with x, y, z values and a yaw value.
     *
     * @param x   The x-coordinate.
     * @param y   The y-coordinate.
     * @param z   The z-coordinate.
     * @param yaw The yaw value (horizontal rotation).
     */
    public Coord(Double x, Double y, Double z, float yaw) {
        super(x, y, z);  // Calls the parent constructor of the Triple class.
        this.yaw = yaw;  // Sets the yaw value.
    }

    /**
     * Constructor to create a Coord object using Integer values (converts to Double).
     *
     * @param x The x-coordinate as Integer.
     * @param y The y-coordinate as Integer.
     * @param z The z-coordinate as Integer.
     */
    public Coord(Integer x, Integer y, Integer z) {
        super(x.doubleValue(), y.doubleValue(), z.doubleValue());  // Converts Integer to Double.
    }

    public Coord add(Coord coord){
        return add(coord.x, coord.y, coord.z);
    }

    public Coord add(double x, double y, double z){
        return new Coord(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Constructor to create a Coord with all value to zero.
     *
     */
    public static Coord zero(){
        return new Coord(0.,0.,0.);
    }

    /**
     * Converts this Coord object into a Bukkit `Location` object.
     *
     * @return A Location object representing the coordinate in the current world.
     */
    public Location toLocation() {
        return new Location(Data.CURRENT_WORLD, x, y, z, yaw, 0);  // Returns a Location object.
    }

    /**
     * Returns a string representation of this Coord object.
     *
     * @return A string of the form "[x, y, z, yaw]".
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + "]";  // Custom string format.
    }
}
