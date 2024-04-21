package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nsh1507
 */

public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;
    /**
     * the current filename
     */
    private String currentFile;
    /**
     * whether the user already select a coordinate before or not
     */
    private boolean firstSelect = true;
    /**
     * the initial row for the select method
     */
    private int firstRow;
    /**
     * the initial column for the select method
     */
    private int firstCol;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }

    /**
     * Constructor that takes in a filename, generate the new board and display the status
     * @param filename the filename to parse
     * @throws IOException if the file is found or not
     */
    public HoppersModel(String filename) throws IOException {
        currentConfig = new HoppersConfig(filename);
        this.currentFile = filename;
        String[] nameList = filename.split("/");
        System.out.println("Loaded: " + nameList[2]);
        System.out.println(this);
    }

    /**
     * Takes in a filename and load the new board of that file
     * @param filename the filename to load the new board
     */
    public void load(String filename) {
        HoppersConfig tempConfig = currentConfig;
        String[] nameList = filename.split("/");
        try{
            currentConfig = new HoppersConfig(filename);
            String loaded = "Loaded: " + nameList[2];
            currentFile = filename;
            this.alertObservers(loaded);

        }
        catch (IOException ioException){
            String failed = "Failed to load: " + filename;
            currentConfig = tempConfig;
            this.alertObservers(failed);
            }
    }

    /**
     * Get the hint of the current board
     */
    public void hint(){
        Solver solver = new Solver();
        List<Configuration> path = solver.solve(currentConfig);
        if (path.size() != 0){
            if (path.size() == 1){
                this.alertObservers("Already solved!");
            }
            else {
                currentConfig = (HoppersConfig) path.get(1);
                this.alertObservers("Next step!");
            }
        }
        else{
            this.alertObservers("No solution!");
        }
    }

    /**
     * reset the current board
     */
    public void reset(){
        load(currentFile);
        this.alertObservers("Puzzle reset!");
    }

    /**
     * Select a position of the board, check if the select is true. If true, wait for
     * the destination and check if it's valid or not. If true, perform the jump action.
     * @param row the initial row
     * @param col the initial column
     */
    public void select(int row, int col){
        if (firstSelect){
            if (this.isValidSelect(row,col)){
                firstRow = row;
                firstCol = col;
                firstSelect = false;
            }
        }
        else {
            if (this.isValidJump(firstRow, firstCol, row, col)) {
                this.jump(firstRow, firstCol, row, col);
            }
            firstSelect =  true;
        }
    }

    /**
     * Perform the jump action
     * @param initialRow the initial row position
     * @param initialCol the initial column position
     * @param destRow the destination of the row
     * @param destCol the destination of the column
     */
    public void jump(int initialRow, int initialCol, int destRow, int destCol ){
        currentConfig = new HoppersConfig(currentConfig, initialCol, initialRow, destCol, destRow);
        this.alertObservers("Jumped from (" + initialRow + ", " + initialCol + ")  to (" + destCol + ", " + destRow + ")");
    }

    /**
     * Check if the selected position a valid one
     * @param row the row position
     * @param col the column position
     * @return whether the selected position is valid
     */
    public boolean isValidSelect(int row, int col){
        if (col < 0 || col >= currentConfig.getCols() || row < 0 || row >= currentConfig.getRows()) {
            this.alertObservers("No frog at (" + row + ", " + col + ")");
            return false;
        }
        if (currentConfig.getCell(row, col) == '.'|| (currentConfig.getCell(row, col) == '*')){
            this.alertObservers("No frog at (" + row + ", " + col + ")");
            return false;
        }
        this.alertObservers("Selected (" + row + ", " + col + ")");
        return true;
    }

    /**
     * Check whether the position to jump to is valid
     * @param row the initial row position
     * @param col the initial column position
     * @param destRow the destination of the row
     * @param destCol the destination of the column
     * @return whether the position to jump to is valid
     */
    public boolean isValidJump(int row, int col, int destRow, int destCol){
        //check if capture is valid
        if (destCol < 0 || destCol >= currentConfig.getCols() || destRow < 0 || destRow >= currentConfig.getRows()) {
            this.alertObservers("Can't jump from (" + row + ", " + col + ") to " + "(" + destRow + ", " + destCol + ")");
            return false;
        }
        if (currentConfig.getCell(((destRow + row)/2), ((destCol + col)/2)) == '.' || currentConfig.getCell(((destRow + row)/2), ((destCol + col)/2)) == '*'){
            this.alertObservers("Can't jump from (" + row + ", " + col + ") to " + "(" + destRow + ", " + destCol + ")");
            return false;
        }
        if (currentConfig.getCell(((destRow + row)/2), ((destCol + col)/2)) != 'G' ){
            this.alertObservers("Can't jump from (" + row + ", " + col + ") to " + "(" + destRow + ", " + destCol + ")");
            return false;
        }
        if (currentConfig.getCell(destRow,destCol) != '.'){
            this.alertObservers("Can't jump from (" + row + ", " + col + ") to " + "(" + destRow + ", " + destCol + ")");
            return false;
        }

        return true;
    }

    /**
     * @return the current configuration
     */
    public HoppersConfig getCurrentConfig(){
        return currentConfig;
    }

    /**
     * @return string representation of the current configuration
     */
    @Override
    public String toString(){
        int rows = currentConfig.getRows();
        int cols = currentConfig.getCols();

        StringBuilder result = new StringBuilder();
        result.append("   ");
        for (int i = 0; i < cols; i++){
            result.append(i).append(" ");
        }
        result.append(System.lineSeparator());
        result.append("  ");
        result.append("-".repeat(Math.max(0, cols * 2)));
        result.append(System.lineSeparator());
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                if (c == 0){
                    result.append(r).append("| ");
                }
                result.append(currentConfig.getCell(r,c)).append(" ");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
