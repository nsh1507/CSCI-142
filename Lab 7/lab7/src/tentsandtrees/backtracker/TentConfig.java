package tentsandtrees.backtracker;

import tentsandtrees.test.ITentsAndTreesTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *  The full representation of a configuration in the TentsAndTrees puzzle.
 *  It can read an initial configuration from a file, and supports the
 *  Configuration methods necessary for the Backtracker solver.
 *
 *  @author RIT CS
 *  @author Nam Huynh
 */
public class TentConfig implements Configuration, ITentsAndTreesTest {
    /** square dimension of field */
    private static int DIM;

    /**
     * list of number of tents for rows in the grid
     */
    private static int[] rows;
    /**
     * list of number of tents for columns in the grid
     */
    private static int[] cols;
    /**
     * the 2d array of grid
     */
    private final char[][] grid;
    /**
     * cursor row location.
     */
    private final int cursorRow;
    /**
     * cursor column location.
     */
    private final int cursorCol;

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:
     * <pre>
     * 3        # square dimension of field
     * 2 0 1    # row looking values, top to bottom
     * 2 0 1    # column looking values, left to right
     * . % .    # row 1, .=empty, %=tree
     * % . .    # row 2
     * . % .    # row 3
     * </pre>
     * @param filename the name of the file to read from
     * @throws IOException if the file is not found or there are errors reading
     */
    public TentConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // get the field dimension
            DIM = Integer.parseInt(in.readLine());
            this.cursorRow = 0;
            this.cursorCol = -1;
            rows = new int[DIM];
            cols = new int[DIM];
            this.grid = new char[DIM][DIM];

            String[] fields = in.readLine().strip().split("\\s+");
            for (int i = 0; i < fields.length; i++){
                rows[i] = Integer.parseInt(fields[i]);
            }
            fields = in.readLine().strip().split("\\s+");
            for (int i = 0; i < fields.length; i++){
                cols[i] = Integer.parseInt(fields[i]);
            }
            for (int row = 0; row < DIM; row++){
                fields = in.readLine().strip().split("\\s+");
                for (int col = 0; col < fields.length; col++) {
                    grid[row][col] = fields[col].charAt(0);
                }
            }
        } // <3 Jim
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    private TentConfig(TentConfig other, int cursorRow, int cursorCol, char type) {
        this.cursorRow = cursorRow;
        this.cursorCol = cursorCol;
        this.grid = new char[DIM][DIM];
        for (int r=0; r < DIM; r++) {
            System.arraycopy(other.grid[r], 0, this.grid[r], 0, DIM);
        }
        this.grid[cursorRow][cursorCol] = type;
    }

    /**
     * Get the collection of successors from the current one.
     * @return All successors, valid and invalid
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new LinkedList<>();
        int tempRow = cursorRow;
        int tempCol;
        if (cursorCol < DIM - 1) {
            tempCol = cursorCol + 1;
        }
        else{
            tempRow = cursorRow + 1;
            tempCol = 0;
        }
        if (this.grid[tempRow][tempCol] == TREE){
            successors.add(new TentConfig(this, tempRow, tempCol, TREE));
        }
        else {
            successors.add( new TentConfig(this, tempRow, tempCol, GRASS));
            successors.add(new TentConfig(this, tempRow, tempCol, TENT));
        }
        return successors;
    }

    /**
     * Is the current configuration valid or not?
     * @return true if valid; false otherwise
     */
    @Override
    public boolean isValid() {
            if (!this.treeCheck()){
                return false;
            }
            if(!this.tentCheck()){
                return false;
            }

            if (cursorRow == DIM -1 && cursorCol == DIM -1){
                for (int rowTent = 0; rowTent < DIM; rowTent++){
                    int numbRow= 0;
                    for (int colTent = 0; colTent < DIM; colTent++){
                        if (this.grid[rowTent][colTent] == TENT){numbRow +=1;}
                    }
                    if (numbRow != rows[rowTent]){return false;}
                }

                for (int rowTent = 0; rowTent < DIM; rowTent++){
                    int numbCol = 0;
                    for (int colTent = 0; colTent < DIM ; colTent++){
                        if (this.grid[colTent][rowTent] == TENT){numbCol +=1;}
                    }
                    if (numbCol != cols[rowTent]){return false;}
                }
                for (int rowTree = 0; rowTree <= cursorRow; rowTree++){
                    for (int colTree = 0; colTree <= cursorCol; colTree++){
                        if (this.grid[rowTree][colTree] == TREE) {
                            boolean north = false, left = false, right = false, south = false;
                            if (rowTree != 0) {
                                north = this.grid[rowTree - 1][colTree] == TENT;
                            }
                            if (colTree != 0) {
                                left = this.grid[rowTree][colTree - 1] == TENT;
                            }
                            if (rowTree != DIM - 1) {
                                south = this.grid[rowTree + 1][colTree] == TENT;
                            }
                            if (colTree != DIM - 1) {
                                right = this.grid[rowTree][colTree + 1] == TENT;
                            }
                            if (!north && !left && !right && !south){
                                return false;
                            }
                        }
                    }
                }
            }
        return true;
    }

    /**
     * Each time a tent is placed, check that there is at least one neighboring tree.
     * @return true if valid
     */
    private boolean treeCheck(){
        if (this.grid[this.cursorRow][this.cursorCol] == TENT) {
            boolean north = false, left = false, right = false, south = false;
            if (this.cursorRow != 0) {
                north = this.grid[cursorRow - 1][cursorCol] == TREE;
            }
            if (this.cursorCol != 0) {
                left = this.grid[this.cursorRow][this.cursorCol - 1] == TREE;
            }
            if (this.cursorRow != DIM - 1) {
                south = this.grid[this.cursorRow + 1][this.cursorCol] == TREE;
            }
            if (this.cursorCol != DIM - 1) {
                right = this.grid[this.cursorRow][this.cursorCol + 1] == TREE;
            }
            return north || left || right || south;
        }
        return true;
    }

    /**
     * Each time a tent is placed you must check that there are no adjacent surrounding tents.
     * @return true if valid
     */
    private boolean tentCheck(){
        if (cursorRow != 0){
            if (this.grid[cursorRow][this.cursorCol] == TENT && this.grid[cursorRow-1][this.cursorCol] == TENT){
                return false;}
            if (this.cursorCol != DIM - 1){
                if (this.grid[this.cursorRow][this.cursorCol] == TENT && this.grid[cursorRow-1][this.cursorCol+1] == TENT){
                    return false;}}
            if (cursorCol !=0){
                if (this.grid[cursorRow][this.cursorCol] == TENT && this.grid[cursorRow-1][cursorCol - 1] == TENT){return false;}
            }
        }
        if (cursorCol !=0){
            return this.grid[cursorRow][this.cursorCol] != TENT || this.grid[cursorRow][cursorCol - 1] != TENT;
        }
        return true;
    }

    /**
     * Is the current configuration a goal?
     * @return true if valid
     */
    @Override
    public boolean isGoal() {
        return this.cursorCol == DIM -1 && this.cursorRow == DIM -1;
    }

    /**
     * @return representation of the grid
     */
    @Override
    public String toString() {
        return getDisplay();
    }

    /**
     * @return the dimension of the grid
     */
    @Override
    public int getDIM() {
        return DIM;
    }

    /**
     * Get the number of tents for a row.
     * @param row the row
     * @return number of tents
     */
    @Override
    public int getTentsRow(int row) {
        return rows[row];
    }

    /**
     * Get the number of tents for a column.
     * @param col the row
     * @return number of tents
     */
    @Override
    public int getTentsCol(int col) {
        return cols[col];
    }

    /**
     * Get the contents at a cell.
     * @param row the row
     * @param col the column
     * @return the contents
     */
    @Override
    public char getCell(int row, int col) {
        return this.grid[row][col];
    }

    /**
     * Get the cursor row location.
     * @return cursor row
     */
    @Override
    public int getCursorRow() {
        return cursorRow;
    }

    /**
     * Get the cursor column location
     * @return cursor column
     */
    @Override
    public int getCursorCol() {
        return cursorCol;
    }
}
