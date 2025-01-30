package org.seblax.utils.types;

/**
 * A generic class that represents a triplet of values.
 * It stores three values of the same type and allows access to each of them.
 */
public class Triple<E> {
    // The three values that the class holds
    public E x;
    public E y;
    public E z;

    /**
     * Constructor to initialize the triplet with values.
     *
     * @param x The first value of type E.
     * @param y The second value of type E.
     * @param z The third value of type E.
     */
    Triple(E x, E y, E z) {
        this.x = x;  // Set the first value
        this.y = y;  // Set the second value
        this.z = z;  // Set the third value
    }
}

//package org.seblax.utils;
//
//public class Triple<E> {
//    public E x;
//    public E y;
//    public E z;
//
//    Triple(E x, E y, E z){
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//}
