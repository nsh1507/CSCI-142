package puzzles.clock;

import puzzles.common.solver.Configuration;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Nam Huynh
 */

public class ClockConfig implements Configuration {
    /** Total number of hours on the clock */
    private final int hours;
    /** The start hour */
    private final int start;
    /** The destination hour */
    private final int end;
    /** The list of neighbors of the current hour */
    private final Collection<Configuration> neighbors = new LinkedList<>();

    /**
     * Constructor that initialize start, end, and hours.
     * @param hours Total number of hours on the clock
     * @param start The start hour
     * @param end The destination hour
     */
    public ClockConfig(int hours, int start, int end){
        this.hours = hours;
        this.start = start;
        this.end = end;
    }

    /**
     * @return whether the current configuration is goal or not
     */
    @Override
    public boolean isSolution() {
        return this.start == this.end;
    }

    /**
     * @return generate and return a list of neighbors of the current configuration.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        int moreStart = (start + 1) % hours;
        int lessStart = (start - 1) % hours;
        if (moreStart == 0){
            moreStart = hours;
        }
        if (lessStart == 0){
            lessStart = hours;
        }

        this.neighbors.add(new ClockConfig(hours, moreStart, end));
        this.neighbors.add(new ClockConfig(hours, lessStart, end));

        return this.neighbors;
    }

    /**
     * @param other other configuration to compare to
     * @return whether two configuration are the same
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof ClockConfig o){
            return this.start == o.start && this.end == o.end && this.hours == o.hours;
        }
        return false;
    }

    /**
     * @return generate a unique hash code for the current configuration
     */
    @Override
    public int hashCode() {
        return this.start + this.end + this.hours;
    }

    /**
     * @return a string representation of the start hour
     */
    @Override
    public String toString() {
        return String.valueOf(this.start);
    }
}
