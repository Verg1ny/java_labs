package com.company;

import java.util.Objects;

/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /**Overridden equals() method
     * Compares this Location to another one**/
    public boolean equals(Object o) {
        //If o is Location
        if (o instanceof Location){
            //Casting to Location type
            Location other = (Location) o;
            return xCoord == other.xCoord && yCoord == other.yCoord;
        }
        //In case of unsatisfied statement
        return false;
    }

    /**Overridden hashcode() method
     * Provides hashcode for each location**/
    public int hashCode(){
        int code = 23;
        code = 7 * code + xCoord;
        code = 7 * code + yCoord;
        return code;
    }

    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
}
