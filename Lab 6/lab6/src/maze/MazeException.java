package maze;

/**
 * A custom exception class for problems encountered while running
 * the TreasureHunt application.
 *
 * @author RIT CS
 */
public class MazeException extends Exception {
    /**
     * Construct a new instance.
     *
     * @param msg the associated error message
     */
    public MazeException(String msg) {
        super("MazeException: " + msg);
    }
}
