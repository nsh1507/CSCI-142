package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.*;
import java.util.*;

/**
 * @author nsh1507
 */

public class HoppersConfig implements Configuration{
    /**
     * char representation of the green frog
     */
    public static final char GREEN_FROG = 'G';
    /**
     * char representation of the red frog
     */
    public static final char RED_FROG = 'R';
    /**
     * char representation of the water
     */
    public static final char WATER = '*';
    /**
     * char representation of the lily pad
     */
    public static final char LILY_PAD = '.';
    /**
     * column size of the board
     */
    private static int cols;
    /**
     * row size of the board
     */
    private static int rows;
    /**
     * the board
     */
    private final char[][] grid;

    /**
     * constructor that takes in a file name, parse through the input and build the map
     * @param filename the name of file to read
     * @throws IOException if the file is not found
     */
    public HoppersConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String[] sizeList = in.readLine().strip().split("\\s+");
            rows = Integer.parseInt(sizeList[0]);
            cols = Integer.parseInt(sizeList[1]);
            this.grid = new char[rows][cols];
            for (int r = 0; r < rows; r++) {
                String[] line = in.readLine().strip().split("\\s+");
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = line[c].charAt(0);
                }
            }
        }
    }

    /**
     * Copy constructor that takes in the current board, the initial row & column position, and the
     * destination of the row and column to generate a successor of the current board
     * @param other the current board
     * @param cursorCols the initial column
     * @param cursorRows the initial row
     * @param destCol the destination column
     * @param destRow the destination row
     */
    public HoppersConfig(HoppersConfig other, int cursorCols, int cursorRows, int destCol, int destRow) {
        this.grid = new char[rows][cols];
        for (int r=0; r < rows; r++) {
            System.arraycopy(other.grid[r], 0, this.grid[r], 0, cols);
        }
        this.grid[destRow][destCol] = this.grid[cursorRows][cursorCols];
        this.grid[((destRow + cursorRows)/2)][ ((destCol + cursorCols)/2) ] = LILY_PAD;
        this.grid[cursorRows][cursorCols] = LILY_PAD;
    }

    /**
     * @return the column size of the board
     */
    public int getCols(){
        return cols;
    }

    /**
     * @return the row size of the board
     */
    public int getRows(){
        return rows;
    }

    /**
     * @return the char representation at specified position
     */
    public char getCell(int row, int col){
        return this.grid[row][col];
    }

    /**
     * @return whether the current board is the solution
     */
    @Override
    public boolean isSolution() {
        for(int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (this.grid[r][c] == GREEN_FROG) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return get all the possible neighbors of the current board
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        List<Configuration> successors = new LinkedList<>();
        for(int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                if (this.grid[r][c] == GREEN_FROG || this.grid[r][c] == RED_FROG){
                    successors.addAll(generateAllJump(r, c));
                }
            }
        }
        return successors;
    }

    /**
     * Generate all the possible locations that a frog can move are valid or not
     * @param r the current row position
     * @param c the current column position
     * @return a list of successors of the current board
     */
    private Collection<Configuration> generateAllJump(int r, int c){
        ArrayList<Configuration> neighbors = new ArrayList<>();
        // get all the possible neighbors

        int[][] coordList = {{-4,0},{4,0},{0,4},{0,-4},{-2,-2}, {2,2}, {-2,2}, {2,-2}};

        for (int[] ints : coordList) {
            if (isValidJump(r, c, r + ints[0], c + ints[1])) {
                HoppersConfig hoppersConfig = new HoppersConfig(this, c, r, c + ints[1], r + ints[0]);
                neighbors.add(hoppersConfig);
            }
        }

        return neighbors;
    }

    /**
     * Check whether a jump is valid or not using the initial position and the destination.
     * @param r the current row
     * @param c the current column
     * @param destRow the destination row
     * @param destCol the destination column
     * @return whether a jump is valid or not
     */
    private boolean isValidJump(int r, int c, int destRow, int destCol){
        //check if capture is valid
        if (destCol < 0 || destCol >= cols || destRow < 0 || destRow >= rows) {
            return false;
        }
        if (grid[((destRow + r)/2)][ ((destCol + c)/2) ] != GREEN_FROG){
            return false;
        }
        if (grid[destRow][destCol] == WATER){
            return false;
        }
        return grid[destRow][destCol] == LILY_PAD;
    }

    /**
     * @return string representation of the board
     */
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(System.lineSeparator());
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                result.append(grid[r][c]).append(" ");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * @return a unique hashcode of the board
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.grid);
    }

    /**
     * @return whether two boards are the same
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof HoppersConfig o){
            for (int r = 0; r < rows; r++){
                for (int c = 0; c < cols; c++){
                    if (o.grid[r][c] != this.grid[r][c]){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
