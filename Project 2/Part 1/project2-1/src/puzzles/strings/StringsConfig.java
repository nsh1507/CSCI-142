package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Nam Huynh
 */

public class StringsConfig implements Configuration {
    /** the starting string*/
    private final String start;
    /** the desired string*/
    private final String finish;
    /** the list of possible configurations of the starting string*/
    private final Collection<Configuration> neighbors;

    /**
     * The constructor that initialize the starting and desired string.
     * @param start the starting string
     * @param finish the desired string
     */
    public StringsConfig(String start, String finish){
        this.start = start;
        this.finish = finish;
        this.neighbors = new LinkedList<>();
    }

    /**
     * @return whether the current string is the desired string
     */
    @Override
    public boolean isSolution() {
        return this.start.equals(this.finish);
    }

    /**
     * @return generate and return a list of neighbors of the current configuration.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        char[] charArray = start.toCharArray();
        char[] endArray = finish.toCharArray();
        for (int i = 0; i < charArray.length; i++){
            int greater = (int) charArray[i] + 1;
            if (greater > 90){
                greater = 65;
            }
            char chGreater = (char) greater;
            String letterGreater = String.valueOf(chGreater);
            String stringGreater = start.substring(0,i) + letterGreater + start.substring(i+1);

            int lesser = (int) charArray[i] - 1;
            if (lesser < 65){
                lesser = 90;
            }
            char chLesser = (char) lesser;
            String letterLesser = String.valueOf(chLesser);
            String stringLesser = start.substring(0,i) + letterLesser + start.substring(i+1);
            int absLesser = Math.abs(chLesser - endArray[i]);
            int absGreater = Math.abs(chGreater - endArray[i]);
            if (absLesser <= 13 || lesser == 65){
                this.neighbors.add(new StringsConfig(stringLesser, finish));
            }
            if (absGreater <= 13 || greater == 90) {
                this.neighbors.add(new StringsConfig(stringGreater, finish));
            }
        }
        return this.neighbors;
    }

    /**
     * @param other other configuration to compare to
     * @return whether two configuration are the same
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof StringsConfig o){
            return this.start.equals(o.start) && this.finish.equals(o.finish);
        }
        return false;
    }

    /**
     * @return generate a unique hash code for the current configuration
     */
    @Override
    public int hashCode() {
        return this.start.hashCode() + this.finish.hashCode();
    }

    /**
     * @return a string representation of the starting string
     */
    @Override
    public String toString() {
        return String.valueOf(this.start);
    }
}
