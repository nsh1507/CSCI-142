package maze;

import java.util.Objects;

/**
 * A single treasure that exists in the maze.
 *
 * @author RIT CS
 */
public class Treasure implements Comparable<Treasure> {
    /** treasure name */
    private final String name;
    /** treasure location */
    private final Coordinates location;
    /** has the treasure been collected or not */
    private boolean collected;

    /**
     * Create a new treasure that has not been collected yet.
     *
     * @param name treasure name
     * @param location treasure location
     */
    public Treasure(String name, Coordinates location) {
        this.name = name;
        this.location = location;
        this.collected = false;
    }

    /**
     * Get the treasure name.
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the treasure location.
     *
     * @return location
     */
    public Coordinates getLocation() {
        return this.location;
    }

    /**
     * Mark the treasure as being collected.
     */
    public void collect() {
        this.collected = true;
    }

    /**
     * Tell whether the treasure has been collected or not.
     *
     * @return collected or not
     */
    public boolean isCollected() {
        return this.collected;
    }

    /**
     * Two treasures are equal iff they have the same name, location
     * and collected status.
     *
     * @param o the other object
     * @return whether they are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treasure treasure = (Treasure) o;
        return collected == treasure.collected && name.equals(treasure.name) && location.equals(treasure.location);
    }

    /**
     * Uses all the fields of the treasure and hash them together.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, location, collected);
    }

    /**
     * Returns a string with the treasure name and location, e.g. "A(0,0)".
     *
     * @return the string
     */
    @Override
    public String toString() {
        return this.name + this.location;
    }

    /**
     * Treasures naturally compare themselves alphabetically by name.
     *
     * @param other the object to be compared.
     * @return a value less than, equal, or greater to zero when comparing
     * this treasure to the other treasure
     */
    @Override
    public int compareTo(Treasure other) {
        return this.name.compareTo(other.name);
    }
}
