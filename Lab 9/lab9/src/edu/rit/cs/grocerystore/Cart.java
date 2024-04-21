package edu.rit.cs.grocerystore;

/**
 * @author nsh1507
 */

public class Cart extends TimedObject implements Comparable< Cart > {
    /**
     * number of items in the cart
     */
    private final int numItems;

    /**
     * Construct the Sentinel Cart that is enqueued to
     * indicate that it is the end of the simulation.
     */
    public Cart() {
        this.numItems = Integer.MAX_VALUE;
    }

    /**
     * The normal customer use to create grocery carts in the simulation.
     * @param numItems the number of groceries in the cart
     */
    public Cart( int numItems ) {
        this.numItems = numItems;
    }

    /**
     * How much of a load is in this cart?
     * @return the number of items, as specified in the constructor
     */
    public int getCartSize() {
        return this.numItems;
    }

    /**
     * Compare two carts, for use in a priority queue.
     * @param other the object to be compared.
     * @return a positive number if this cart has more groceries,
     * a negative number if the other cart has more groceries, otherwise, 0
     */
    @Override
    public int compareTo( Cart other ) {
        return this.numItems - other.numItems;
    }

    /**
     * @return Compare two carts, for use in a priority queue.
     */
    @Override
    public String toString() {
        return "Cart(" + this.numItems +")";
    }

}
