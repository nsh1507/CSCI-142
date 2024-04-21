package test;

import maze.Coordinates;
import maze.IMaze;
import maze.Maze;
import maze.Treasure;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMaze {
    /** Used to test that expected System.out print's happen */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    // used for neighbor checking
    private Map<Coordinates, Set<Coordinates>> neighbors;
    private Map<Coordinates, Set<Coordinates>> invalidNeighbors;

    /**
     * This helper function builds the complete collection of neighbors
     * for every cell in the 3x4 maze from maze3.txt.
     * @param maze the maze
     * @return full collection of neighbors
     */
    private void generateNeighbors(IMaze maze) {
        // build a 2-D grid of coordinates for easy access
        Coordinates[][] grid = new Coordinates[maze.getRows()][maze.getCols()];
        for (int row=0; row<maze.getRows(); ++row) {
            for (int col=0; col<maze.getCols(); ++col) {
                grid[row][col] = new Coordinates(row, col);
            }
        }

        // build the adjacency list that associates a cell with neighbor cells
        this.neighbors = new LinkedHashMap<>();
        // row 0
        this.neighbors.put(grid[0][0], Stream.of(grid[1][0])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[0][1], Stream.of(grid[0][2], grid[1][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[0][2], Stream.of(grid[0][1], grid[0][3])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[0][3], Stream.of(grid[0][2])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        // row 1
        this.neighbors.put(grid[1][0], Stream.of(grid[0][0], grid[2][0])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[1][1], Stream.of(grid[0][1], grid[1][2], grid[2][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[1][2], Stream.of(grid[1][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[1][3], Stream.of(grid[2][3])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        // row 2
        this.neighbors.put(grid[2][0], Stream.of(grid[1][0], grid[2][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[2][1], Stream.of(grid[1][1], grid[2][0], grid[2][2])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[2][2], Stream.of(grid[2][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.neighbors.put(grid[2][3], Stream.of(grid[1][3])
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        // now generate some invalid neighbors
        this.invalidNeighbors = new LinkedHashMap<>();
        // row 0
        this.invalidNeighbors.put(grid[0][0], Stream.of(grid[2][0], grid[0][3], grid[0][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.invalidNeighbors.put(grid[0][1], Stream.of(grid[0][0], grid[2][1])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.invalidNeighbors.put(grid[0][2], Stream.of(grid[2][2], grid[1][2])
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        this.invalidNeighbors.put(grid[0][3], Stream.of(grid[0][0], grid[2][3], grid[1][3])
                .collect(Collectors.toCollection(LinkedHashSet::new)));

    }

    @Test
    @Order(1)
    public void testDimensions() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");
            assertEquals(3, maze.getRows());
            assertEquals(4, maze.getCols());
        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }

    }

    @Test
    @Order(2)
    public void testHasCoordinates() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");
            for (int row=0; row<maze.getRows(); ++row) {
                for (int col=0; col<maze.getCols(); ++col) {
                    assertTrue(maze.hasCoordinates(new Coordinates(row, col)));
                }
            }
        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }
    }

    @Test
    @Order(3)
    public void testNeighbors() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");
            generateNeighbors(maze);
            // all these neighbors are valid and should exist
            for (Coordinates src : this.neighbors.keySet()) {
                for (Coordinates dest : this.neighbors.get(src)) {
                    assertTrue(maze.isNeighbor(src, dest));
                }
            }

            // all these neighbors are invalid and should not exist
            for (Coordinates src : this.invalidNeighbors.keySet()) {
                for (Coordinates dest : this.invalidNeighbors.get(src)) {
                    assertFalse(maze.isNeighbor(src, dest));
                }
            }

            // now check getNeighbors
            for (Coordinates src : this.neighbors.keySet()) {
                for (Coordinates dest : this.neighbors.get(src)) {
                    assertTrue(maze.getNeighbors(src).contains(dest));
                }
            }
        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }
    }

    @Test
    @Order(4)
    public void testHome() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");
            assertEquals(new Coordinates(0,0), maze.getHome());
        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }
    }

    @Test
    @Order(5)
    public void testTreasures() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");

            Set<Treasure> treasures = new LinkedHashSet<>();
            treasures.add(new Treasure("A", new Coordinates(0, 3)));
            treasures.add(new Treasure("B", new Coordinates(1, 2)));
            treasures.add(new Treasure("C", new Coordinates(2, 3)));

            // getTreasures
            for (Treasure treasure : maze.getTreasures()) {
                assertTrue(treasures.contains(treasure));
            }

            // hasTreasures - check cells for treasure or no treasure
            // row 0
            assertFalse(maze.hasTreasure(new Coordinates(0, 0)));
            assertFalse(maze.hasTreasure(new Coordinates(0, 1)));
            assertFalse(maze.hasTreasure(new Coordinates(0, 2)));
            assertTrue(maze.hasTreasure(new Coordinates(0, 3)));
            // row 1
            assertFalse(maze.hasTreasure(new Coordinates(1, 0)));
            assertFalse(maze.hasTreasure(new Coordinates(1, 1)));
            assertTrue(maze.hasTreasure(new Coordinates(1, 2)));
            assertFalse(maze.hasTreasure(new Coordinates(1, 3)));
            // row 2
            assertFalse(maze.hasTreasure(new Coordinates(2, 0)));
            assertFalse(maze.hasTreasure(new Coordinates(2, 1)));
            assertFalse(maze.hasTreasure(new Coordinates(2, 2)));
            assertTrue(maze.hasTreasure(new Coordinates(2, 3)));
        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }
    }

    @Test
    @Order(6)
    public void testCells() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");
            // row 0
            assertEquals(IMaze.HOME, maze.getCell(new Coordinates(0,0)));
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(0,1)));
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(0,2)));
            assertEquals("A", maze.getCell(new Coordinates(0,3)));
            // row 1
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(1,0)));
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(1,1)));
            assertEquals("B", maze.getCell(new Coordinates(1,2)));
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(1,3)));
            // row 2
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(2,0)));
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(2,1)));
            assertEquals(IMaze.CELL, maze.getCell(new Coordinates(2,2)));
            assertEquals("C", maze.getCell(new Coordinates(2,3)));

        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }
    }

    @Test
    @Order(7)
    public void testDisplay() {
        try {
            IMaze maze = new Maze("data/maze-3.txt");
            String expected =
                    "  0 1 2 3 " + System.lineSeparator() +
                            "  -------" + System.lineSeparator() +
                            "0|*|. . A|" + System.lineSeparator() +
                            "      - - " + System.lineSeparator() +
                            "1|.|. B|.|" + System.lineSeparator() +
                            "      -   " + System.lineSeparator() +
                            "2|. . .|C|" + System.lineSeparator() +
                            "  -------" + System.lineSeparator();
            outContent.reset();
            maze.display();
            assertEquals(expected, outContent.toString());
        } catch (Exception ioe) {
            System.err.println("Error: " + ioe);
        }
    }
}