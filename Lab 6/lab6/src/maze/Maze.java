package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Student implementation of the maze.
 *
 * @author Nam Huynh
 */
public class Maze implements IMaze {
    /**
     * the maze represents as a 2D array
     */
    private final Node[][] maze;
    /**
     * the starting position for searching treasures
     */
    private final Coordinates home;
    /**
     * the list of treasures to be found
     */
    private final ArrayList<Treasure> treasures;
    /**
     * the rows of the 2D array
     */
    private final int rows;
    /**
     * the columns of the 2D array
     */
    private final int columns;
    /**
     * the maze represents as a 2D array
     */
    private final String[][] stringmaze;


    /**
     * Create a new maze from a file.
     * @param filename the name of the maze file
     * @throws IOException if a problem is encountered reading the file
     */
    public Maze(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();
            String[] fields = line.strip().split("\\s+");
            if (Integer.parseInt(fields[0]) <= 0){
                System.err.println("MazeException: rows must be greater than zero: " + Integer.parseInt(fields[0]));
                System.exit(-1);
            }
            if (Integer.parseInt(fields[1]) <= 0){
                System.err.println("MazeException: columns must be greater than zero: " + Integer.parseInt(fields[1]));
                System.exit(-1);
            }
            rows = Integer.parseInt(fields[0]);
            columns = Integer.parseInt(fields[1]);
            this.stringmaze = new String[rows][columns];
            this.maze = new Node[rows][columns];
            String[][] string2d = new String[rows][columns];
            for (int row = 0; row < rows; row++){
                String[] grid = in.readLine().strip().split("\\s+");
                for (int column = 0; column < columns; column++) {
                    this.maze[row][column] = new Node(new Coordinates(row, column));
                    string2d[row][column] = grid[column];
                    stringmaze[row][column] = CELL;
                }
            }
            for (int row = 0; row< rows; row++){
                for (int column = 0; column < columns; column++) {
                    String str = string2d[row][column];
                    Node node = maze[row][column];
                    String error = "";
                    try{
                        if (str.contains(NORTH)){
                            error = "north";
                            node.addNeighbor(maze[row-1][column]);
                        }
                        if (str.contains(WEST)){
                            error = "west";
                            node.addNeighbor(maze[row][column-1]);
                        }
                        if (str.contains(EAST)){
                            error = "east";
                            node.addNeighbor(maze[row][column+1]);
                        }
                        if (str.contains(SOUTH)){
                            error = "south";
                            node.addNeighbor(maze[row+1][column]);
                        }
                    }
                    catch (IndexOutOfBoundsException e){
                        System.err.println("MazeException: cannot have a " + error + " neighbor: " + node.getCoordinates());
                        System.exit(-1);
                    }
                }
            }
            line = in.readLine();
            fields = line.strip().split("\\s+");
            if (fields.length < 2){
                System.err.println("MazeException: home position not specified!");
                System.exit(-1);
            }
            home = new Coordinates(fields[1]);
            stringmaze[home.row()][home.col()] = HOME;
            line = in.readLine();
            int numTreasure = Integer.parseInt(line);
            treasures = new ArrayList<>();
            for (int i=0; i<numTreasure;i++){
                line = in.readLine();
                fields = line.strip().split("\\s+");
                Coordinates treasureCoord = new Coordinates(fields[1]);
                treasures.add(new Treasure(fields[0], treasureCoord));
                stringmaze[treasureCoord.row()][treasureCoord.col()] = fields[0];
            }
        }
    }

    /**
     * Get the number of rows in the maze.
     * @return number of rows
     */
    @Override
    public int getRows() {
        return rows;
    }

    /**
     * Get the number of columns in the maze.
     * @return number of columns
     */
    @Override
    public int getCols() {
        return columns;
    }

    /**
     * Is a location in the maze valid or not?
     * @param location the location
     * @return whether the cell is valid or not
     */
    @Override
    public boolean hasCoordinates(Coordinates location) {
        return location.row() >= 0 && location.row() < rows && location.col() >= 0 && location.col() < columns;
    }

    /**
     * Check whether dest are neighbors of src or not
     * @param src the source cell
     * @param dest the destination cell
     * @return whether they are neighbors or not
     */
    @Override
    public boolean isNeighbor(Coordinates src, Coordinates dest) {
        Node srcnode = maze[src.row()][src.col()];
        Node destnode = maze[dest.row()][dest.col()];
        return srcnode.getNeighbors().contains(destnode);
    }

    /**
     * Get the string value of the cell at a location in the maze.
     * @param location the location
     * @return the string at that location
     */
    @Override
    public String getCell(Coordinates location) {
        for (Treasure treasure: treasures){
            if (treasure.isCollected()){
                stringmaze[treasure.getLocation().row()][treasure.getLocation().col()] = CELL;
            }
        }
        return stringmaze[location.row()][location.col()];
    }

    /**
     * Get the home position (must exist).
     * @return home position
     */
    @Override
    public Coordinates getHome() {
        return home;
    }

    /**
     * Is there a treasure at a specific location?
     * @param location the location
     * @return whether there is a treasure at this location or not
     */
    @Override
    public boolean hasTreasure(Coordinates location) {
        for (Treasure treasure : treasures) {
            if (treasure.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the collection of all treasures in the maze.
     * @return the treasure collection
     */
    @Override
    public Collection<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * Get the neighbors of a cell in the maze.
     * @param location the cell
     * @return the cell's neighbors
     */
    @Override
    public Collection<Coordinates> getNeighbors(Coordinates location) {
        Collection<Node> nodeNeighbors = maze[location.row()][location.col()].getNeighbors();
        Collection<Coordinates> coordinates = new ArrayList<>();
        for (Node node: nodeNeighbors){
            coordinates.add(node.getCoordinates());
        }
        return coordinates;
    }
}
