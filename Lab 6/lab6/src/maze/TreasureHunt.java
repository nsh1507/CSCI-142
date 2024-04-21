package maze;

import java.io.IOException;
import java.util.*;

/**
 * The main program for the treasure hunt.  It is run on the command
 * line with the maze file, e.g.:
 * $ java TreasureHunt data/maze-3.txt
 *
 * @author RIT CS
 * @author Nam Huynh
 */
public class TreasureHunt {
    /** the maze */
    private final IMaze maze;

    /**
     * Create the instance and construct the maze from the file.
     *
     * @param filename the maze file
     * @throws IOException if there is a problem reading the file
     */
    public TreasureHunt(String filename) throws IOException {
        this.maze = new Maze(filename);
        this.maze.display();
    }


    /**
     * Find all the treasures in the maze.  For each treasure,
     * find the shortest path from the home position to the treasure
     * position and collect the treasure.  While collecting
     * treasures, the total number of steps taken to collect
     * each treasure and return it home is computed.
     */
    public void findTreasures() {
        Collection<Treasure> treasureCollection = maze.getTreasures();
        List<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> predecessors = new HashMap<>();

        queue.add(maze.getHome());
        predecessors.put(maze.getHome(), maze.getHome());
        int steps = 0;

        while (!queue.isEmpty()) {
            Coordinates current = queue.remove(0);
            if (maze.hasTreasure(current)) {
                for (Treasure treasure: treasureCollection){
                    if (treasure.getLocation().equals(current)){
                        System.out.println("Collecting " + treasure);
                        treasure.collect();}}
                List<Coordinates> path = constructPath(predecessors, maze.getHome(), current);
                System.out.println("\tPath:" + path);
                System.out.println("\tSteps: " + (path.size()*2 -1));
                steps += path.size()*2 -1;
                path.clear();
                maze.display();
            }
            for (Coordinates nbr : maze.getNeighbors(current)) {
                if(!predecessors.containsKey(nbr)) {
                    predecessors.put(nbr, current);
                    queue.add(nbr);
                }
            }
        }
        for (Treasure treasure: treasureCollection){
            if (!treasure.isCollected()){
                System.out.println("Collecting: " + treasure);
                System.out.println("\tNo path!");
                maze.display();
            }
        }
        System.out.println("Total steps: " + steps);
    }

    /**
     * Construct the shortest path to get to a treasure
     * @param predecessors the predecessors map containing coordinates and its predecessors
     * @param startNode the home coordinates
     * @param finishNode the coordinates of the treasure
     * @return list of the shortest path containing coordinates
     */
    private List<Coordinates> constructPath( Map<Coordinates,Coordinates> predecessors, Coordinates startNode, Coordinates finishNode) {
        List<Coordinates> path = new LinkedList<>();
        if(predecessors.containsKey(finishNode)) {
            Coordinates currNode = finishNode;
            while (currNode != startNode) {
                path.add(0, currNode);
                currNode = predecessors.get(currNode); }
            path.add(0, startNode); }
        return path;
    }

    /**
     * The main method.
     *
     * @param args command line arguments (maze file)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TreasureHunt maze-file");
        } else {
            try {
                // construct the maze
                TreasureHunt treasureHunt = new TreasureHunt(args[0]);
                // perform searches over our maze
                treasureHunt.findTreasures();
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        }
    }
}
