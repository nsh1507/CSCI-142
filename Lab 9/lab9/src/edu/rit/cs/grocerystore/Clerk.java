package edu.rit.cs.grocerystore;

/**
 * @author nsh1507
 */
public class Clerk implements Runnable{
    /**
     * the queue from which the clerk gets carts to check out
     */
    private final TSQueue<Cart> checkoutLine;

    /**
     * Create a Clerk and connect it to its checkout line.
     * @param checkoutLine the queue from which the clerk gets carts to check out
     */
    public Clerk(TSQueue<Cart> checkoutLine){
        this.checkoutLine = checkoutLine;
    }

    /**
     * Continuously removes carts from the checkout line and sleeps
     * for a time period to simulate the checkout process. When the
     * sleep time for a cart is complete, it then "tells" the cart
     * that it has finished servicing the cart so that times can be saved.
     */
    @Override
    public void run() {
        Cart removed =  checkoutLine.dequeue();
        while(!removed.equals(Utilities.NO_MORE_CARTS)){
            Utilities.println("Clerk got " + removed);
            try{
                Thread.sleep(removed.getCartSize() * Utilities.TIME_PER_CART_ITEM);
            } catch (InterruptedException ignored){}
            removed.servicingDone();
            removed = checkoutLine.dequeue();
        }
    }
}
