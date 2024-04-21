package puzzles.chess.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of one configuration of a chess puzzle.
 * @author Alex Eng
 */

public class ChessConfig implements Configuration {

    /**
     * Array of the different pieces that can populate a space on the board
     */
    private static final char[] PIECE_ARRAY = {'K', 'Q', 'R', 'B', 'N', 'P'};

    /**
     * char representation of an empty space
     */
    private static final char EMPTY = '.';
    /**
     * The number of rows in the puzzle
     */
    private final int rows;
    /**
     * The number of cols in the puzzle
     */
    private final int cols;
    /**
     * Map of all the pieces currently on the board
     */
    private final HashMap<Coordinates, Character> pieces;

    /**
     * 2D array representation of the chess board
     */
    private final char[][] board;

    /**
     * Constructor method
     * @param filename the name of the file being read
     * @throws IOException If filename is invalid
     */
    public ChessConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            this.rows = Integer.parseInt(fields[0]);
            this.cols = Integer.parseInt(fields[1]);
            this.pieces = new HashMap<>();
            this.board = new char[rows][cols];

            for (int i = 0; i < this.rows; i++){
                line = in.readLine();
                fields = line.split("\\s+");
                for (int j = 0; j < this.cols; j++){
                    this.board[i][j] = fields[j].charAt(0);
                    Coordinates location = new Coordinates(i, j);
                    if (fields[j].charAt(0) != EMPTY){
                        this.pieces.put(location, fields[j].charAt(0));
                    }
                }
            }
        }
    }

    /**
     * Copy constructor for creating neighbor configurations
     * @param other The configuration being copied
     * @param src The location of the piece capturing the other
     * @param target The location of the piece being captured
     */
    public ChessConfig(ChessConfig other, Coordinates src, Coordinates target){
        this.cols = other.cols;
        this.rows = other.rows;
        this.pieces = new HashMap<>();
        for (Coordinates coordinates : other.pieces.keySet()){
            Character ch = other.pieces.get(coordinates);
            this.pieces.put(coordinates, ch);
        }
        this.board = new char[rows][cols];
        for (int r = 0; r < rows; r ++){
            System.arraycopy(other.board[r], 0, this.board[r], 0, cols);
        }
        this.takePiece(src, target);
    }

    /**
     * Gets the dimensions of the board
     * @return int[] of the dimensions of the board [rows, cols]
     */
    public int[] getDimensions(){
        return new int[] {this.rows, this.cols};
    }

    /**
     * Generates all possible legal moves that one piece can make on a board.
     * @param src The piece being checked for moves
     * @param token The char representation of the piece
     * @return Set of all coordinates that the piece can legally move to.
     */
    public Set<Coordinates> getMoves(Coordinates src, char token){
        int[] tmp = src.coordToArray();
        Set<Coordinates> moves = new HashSet<>();
        int r = tmp[0]; // for checking diagonals
        int c = tmp[1]; // for checking diagonals
        switch (token) {
            case 'K' -> {
                moves.add(new Coordinates(tmp[0] + 1, tmp[1])); // south
                moves.add(new Coordinates(tmp[0] - 1, tmp[1])); // north
                moves.add(new Coordinates(tmp[0], tmp[1] + 1)); // east
                moves.add(new Coordinates(tmp[0], tmp[1] - 1)); // west
                moves.add(new Coordinates(tmp[0] + 1, tmp[1] + 1)); // SE
                moves.add(new Coordinates(tmp[0] - 1, tmp[1] - 1)); // NW
                moves.add(new Coordinates(tmp[0] - 1, tmp[1] + 1)); // NE
                moves.add(new Coordinates(tmp[0] + 1, tmp[1] - 1)); // SW

                moves.removeIf(move -> !this.pieces.containsKey(move));
            }
            case 'Q' -> { // Queen only glitches out when there are no more moves available for it to make or its on a border
                while (r < this.rows) { // S
                    r++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                while (r > 0) { // N
                    r--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                while (c < this.cols) { // E
                    c++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                c = tmp[1];
                while (c > 0) { // W
                    c--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                c = tmp[1];
                moves.removeIf(move -> !this.pieces.containsKey(move));

                while (r < this.rows && c < this.cols) { // SE
                    r++;
                    c++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                c = tmp[1];
                while (r > 0 && c > 0) { // NW
                    r--;
                    c--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                c = tmp[1];
                while (r < this.rows && c > 0) { // SW
                    r++;
                    c--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                c = tmp[1];
                while (r > 0 && c < this.cols) { // NE
                    r--;
                    c++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }

                moves.removeIf(move -> !this.pieces.containsKey(move));
            }
            case 'R' -> {
                while (r < this.rows) { // S
                    r++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                while (r > 0) { // N
                    r--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                while (c < this.cols) { // E
                    c++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                c = tmp[1];
                while (c > 0) { // W
                    c--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                moves.removeIf(move -> !this.pieces.containsKey(move));

            }
            case 'B' -> {
                while (r < this.rows && c < this.cols) { // SE
                    r++;
                    c++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                c = tmp[1];
                while (r > 0 && c > 0) { // NW
                    r--;
                    c--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                c = tmp[1];
                while (r < this.rows && c > 0) { // SW
                    r++;
                    c--;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                r = tmp[0];
                c = tmp[1];
                while (r > 0 && c < this.cols) { // NE
                    r--;
                    c++;
                    Coordinates candidate = new Coordinates(r, c);
                    if (this.pieces.containsKey(candidate)){
                        moves.add(candidate);
                        break;
                    }
                }
                moves.removeIf(move -> !this.pieces.containsKey(move));
            }
            case 'N' -> {
                moves.add(new Coordinates(tmp[0] + 2, tmp[1] + 1)); // 3S, 1E
                moves.add(new Coordinates(tmp[0] - 2, tmp[1] + 1)); // 3N, 1E
                moves.add(new Coordinates(tmp[0] + 2, tmp[1] - 1)); // 3S, 1W
                moves.add(new Coordinates(tmp[0] - 2, tmp[1] - 1)); // 3N, 1W
                moves.add(new Coordinates(tmp[0] + 1, tmp[1] + 2)); // 1S, 3E
                moves.add(new Coordinates(tmp[0] - 1, tmp[1] - 2)); // 1N, 3W
                moves.add(new Coordinates(tmp[0] - 1, tmp[1] + 2)); // 1N, 3E
                moves.add(new Coordinates(tmp[0] + 1, tmp[1] - 2)); // 1S, 3W
                moves.removeIf(move -> !this.pieces.containsKey(move));
            }
            case 'P' -> {
                moves.add(new Coordinates(tmp[0] - 1, tmp[1] + 1)); // NE
                moves.add(new Coordinates(tmp[0] - 1, tmp[1] - 1)); // NW
                moves.removeIf(move -> !this.pieces.containsKey(move));
            }
            default -> {
                return new HashSet<>();
            }
        }
        return moves;
    }

    /**
     * Performs the action of making one piece capture another
     * @param src The capturing piece
     * @param target The piece being captured
     */
    public void takePiece(Coordinates src, Coordinates target){
        Character attacker = this.pieces.get(src);
        this.pieces.remove(src);
        this.pieces.put(target, attacker);

        int[] srcArr = src.coordToArray();
        int[] targetArr = target.coordToArray();
        this.board[targetArr[0]][targetArr[1]] = this.board[srcArr[0]][srcArr[1]];
        this.board[srcArr[0]][srcArr[1]] = EMPTY;
    }

    /**
     * Gets the piece at a given coordinate
     * @param location Coordinate location of the piece
     * @return Character representation of the desired piece. If no piece exists, returns '.'
     */
    public Character getPiece(Coordinates location){
        if (!this.pieces.containsKey(location)){
            return EMPTY;
        }
        return this.pieces.get(location);
    }

    /**
     * Checks if the current configuration is a solution
     * @return True if it is, false if it is not
     */
    @Override
    public boolean isSolution() {
        return this.pieces.size() == 1;
    }

    /**
     * Gets the neighbors of the current configuration
     * @return Collection of the neighbors of the current configuration
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Set<Coordinates> moves;
        LinkedList<Configuration> configList = new LinkedList<>();
        for (char c : PIECE_ARRAY) { // Gets the piece from the piece array
            for (Coordinates key : this.pieces.keySet()) { // Looks through each coordinate for a match
                if (this.pieces.get(key) == c) {
                    moves = this.getMoves(key, c); // Construct a set of possible moves for that piece
                    for (Coordinates move : moves) { // For each possible move, create and add a successor to the list
                        configList.add(new ChessConfig(this, key, move));
                    }
                }
            }
        }
        return configList;
    }

    /**
     * Generates a string representation of the board
     * @return String representation of the board
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(System.lineSeparator());
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                Coordinates location = new Coordinates(r, c);
                if (this.pieces.containsKey(location)){
                    result.append(this.pieces.get(location)).append(" ");
                } else {
                    result.append(EMPTY).append(" ");
                }
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Creates a hash code of the configuration
     * @return Hash code of the configuration
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }

    /**
     * Determines if another configuration holds the same data as the current configuration
     * @param other The object being compared
     * @return True if the objects are equal, false if not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof ChessConfig o){
            return this.hashCode() == o.hashCode();
        }
        return false;
    }
}
