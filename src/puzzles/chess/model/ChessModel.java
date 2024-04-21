package puzzles.chess.model;

import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Model for the PTUI and GUI of the chess puzzle
 * @author Alex Eng
 */

public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private ChessConfig currentConfig;

    /**
     * the coordinate location of the current selected piece
     */
    private Coordinates currentPiece;

    /**
     * denotes if the model is on the first select or the second
     */
    private boolean firstSelect;

    /**
     * Common BFS solver for generating the current path
     */
    private Solver solver;

    /**
     * Name of the file currently being used
     */
    private String currentFile;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<ChessModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * Constructor method
     * @param filename Name of the file that the user wants to use
     * @throws IOException invalid file name or file does not exist
     */
    public ChessModel(String filename) throws IOException {
        System.out.println("Loaded: " + filename);
        this.currentFile = filename;
        this.currentConfig = new ChessConfig(currentFile);
        this.currentPiece = new Coordinates(0,0);
        this.solver = new Solver();
        this.firstSelect = true;
        System.out.println(this);
    }

    /**
     * Advances the puzzle to the next step for the user, if there is a valid solution to the puzzle
     */
    public void getHint(){
        List<Configuration> path = solver.solve(currentConfig);
        if (path.size() != 0){
            if (path.size() > 1){
                this.currentConfig = (ChessConfig) path.get(1);
                this.alertObservers("Next step!");
            }
            else {
                this.alertObservers("Puzzle already solved!");
            }
        }
        else{
            this.alertObservers("No solution!");
        }
    }

    /**
     * Selects the piece chosen by the user
     * @param cmdStr String of the user's command follows format: "s(elect) ROW COL"
     */
    public void select(String cmdStr){
        String[] fields = cmdStr.split("\\s+");
        Coordinates coordinates = new Coordinates(fields[1], fields[2]);
        if (firstSelect){
            if (validSelect(coordinates)){
                this.currentPiece = coordinates;
                this.firstSelect = false;
            }

        } else {
            if (validCapture(this.currentPiece, coordinates)){
                capture(this.currentPiece, coordinates);
            }
            this.firstSelect = true;
        }
    }

    /**
     * Loads the file chosen by the user
     * @param cmdStr String of the user's command follows format: "l(oad) FILEPATH"
     */
    public void load(String cmdStr) {
        String[] fields = cmdStr.split("\\s+");
        this.currentFile = fields[1];
        ChessConfig lastConfig = this.currentConfig;
        try {
            this.currentConfig = new ChessConfig(this.currentFile);
            this.alertObservers("Loaded: " + this.currentFile);
        } catch (IOException e){
            this.currentConfig = lastConfig;
            this.alertObservers("Failed to load: " + this.currentFile);
        }
    }

    /**
     * Sets the puzzle back to its original state
     */
    public void reset(){
        this.load("l " + currentFile);
        alertObservers("Puzzle reset!");
    }

    /**
     * Captures a piece and alerts observers
     * @param src Coordinates of the capturing piece
     * @param dest Coordinates of the piece being captured
     */
    public void capture(Coordinates src, Coordinates dest){
        this.currentConfig = new ChessConfig(this.currentConfig, src, dest);
        this.alertObservers("Captured from " + src + " to " + dest);
    }

    /**
     * Checks if a capture is valid
     * @param src Coordinates of the capturing piece
     * @param dest Coordinates of the piece being captured
     * @return True if the capture is valid, false otherwise
     */
    public boolean validCapture(Coordinates src, Coordinates dest){
        Character piece = this.currentConfig.getPiece(src);
        Set<Coordinates> moves = this.currentConfig.getMoves(src, piece);
        if (this.currentConfig.getPiece(dest) == '.' || !moves.contains(dest)){
            alertObservers("Can't capture from " + src + " to " + dest);
            return false;
        }
        return true;
    }

    /**
     * Checks if a selection is valid
     * @param selected Coordinates of the selected piece
     * @return True if the selection is valid, false otherwise
     */
    public boolean validSelect(Coordinates selected){
        int[] sel = selected.coordToArray();
        int[] dim = this.currentConfig.getDimensions();
        if ((sel[0] < 0) || (sel[0] >= dim[0]) ||
                (sel[1] < 0) || (sel[1] >= dim[1])){
            alertObservers("Invalid selection " + selected);
            return false;
        }
        if (this.currentConfig.getPiece(selected) == '.') {
            alertObservers("Invalid selection " + selected);
            return false;
        }

        this.alertObservers("Selected " + selected);
        return true;
    }

    /**
     * Generates a string representation of the board
     * @return string representation of the board
     */
    public String configToString(){
        return this.currentConfig.toString();
    }

    /**
     * Generates a string representation of the model
     * @return String representation of the model
     */
    @Override
    public String toString(){
        int[] dim = this.currentConfig.getDimensions();

        StringBuilder result = new StringBuilder();
        result.append("   ");
        for (int i = 0; i < dim[1]; i++){
            result.append(i).append(" ");
        }
        result.append(System.lineSeparator());
        result.append("  ");
        result.append("-".repeat(Math.max(0, dim[1] * 2)));
        result.append(System.lineSeparator());
        for (int r = 0; r < dim[0]; r++){
            for (int c = 0; c < dim[1]; c++){
                Coordinates loc = new Coordinates(r, c);
                if (c == 0){
                    result.append(r).append("| ");
                }
                result.append(currentConfig.getPiece(loc)).append(" ");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

}
