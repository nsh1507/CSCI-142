package edu.rit.cs.grocerystore;

/**
 * @author nsh1507
 */

public class Customer implements Runnable{
    private final double delay;
    private final Cart cart;
    private final TSQueue<Cart> queue;
    private static int id = 1;
    private final int uniqueId;

    /**
     * Create a Customer object, storing all parameters provided and assigning
     * the next consecutive integer as this object's ID. First Customer is #1.
     * @param delay how many milliseconds to wait before enqueuing the cart at the checkout line
     * @param cart  the cart of groceries (already filled with goodies)
     * @param queue the checkout line
     */
    public Customer(double delay, Cart cart, TSQueue<Cart> queue){
        this.delay = delay;
        this.cart = cart;
        this.queue = queue;
        this.uniqueId = id;
        id +=1;
    }

    /**
     * Sleep for the given delay time.
     * Put the given Cart in the checkout queue.
     * Print a message announcing the above has been done.
     * The format of the message is:
     *      Customer id with cart has entered the line, with N customers in front.
     */
    @Override
    public void run() {
        try {
            Thread.sleep((long) this.delay);
        }
        catch (InterruptedException ignored){}

        int size = this.queue.enqueue(this.cart);
        Utilities.println("Customer " + this.uniqueId + " with " + this.cart + " has entered the line, with " +  (size-1) +" customers in front.");
    }
}
