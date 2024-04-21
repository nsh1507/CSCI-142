package maze;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * CSCI-142 Computer Science 2 Recitation Presentation
 * 08-GraphIntro
 * Shortet Path
 * <p>
 * This is the representation of a node in a graph.  A node is composed of a
 * unique name, and a list of neighbor Node's.
 *
 * @author RIT CS
 */
public class Node {

    /** Name associated with this node */
    private final Coordinates coordinates;

    /** Neighbors of this node are stored as a set (adjacency list) */
    private final Set<Node> neighbors;

    /**
     * Constructor initializes Node with an empty list of neighbors.
     *
     * @param coordinates string representing the name associated with the node.
     */
    public Node(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.neighbors = new LinkedHashSet<>();
    }

    /**
     * Get the String name associated with this object.
     *
     * @return name.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Method to return the adjacency list for this node containing all 
     * of its neighbors.
     *
     * @return the list of neighbors of the given node
     */
    public Collection<Node> getNeighbors() {
        return Collections.unmodifiableSet(this.neighbors);
    }

    /**
     * Add a neighbor to this node.  Checks if already present, and does not
     * duplicate in this case.
     *
     * @param node: node to add as neighbor.
     */
    public void addNeighbor(Node node) {
        neighbors.add(node);
    }

    /**
     * Method to generate a string associated with the node, including the 
     * name of the node followed by the names of its neighbors.  Overrides
     * Object toString method.
     *
     * @return string associated with the node.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.coordinates + ": ");
        for (Node neighbor: this.neighbors) {
            result.append(neighbor.getCoordinates()).append(" ");
        }
        return result.toString();
    }

    /**
     *  Two Nodes are equal if they have the same name.
     *
     *  @param other The other object to check equality with
     *  @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Node n) {
            result = this.coordinates.equals(n.coordinates);
        }
        return result;
    }

    /**
     * The hash code of a Node is just the hash code of the name, since no
     * two nodes can have the same name, and it is consistent with equals()
     */
    @Override
    public int hashCode() {
        return this.coordinates.hashCode();
    }
}