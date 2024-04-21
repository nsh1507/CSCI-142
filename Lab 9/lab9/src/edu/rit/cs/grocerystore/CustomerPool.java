package edu.rit.cs.grocerystore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author nsh1507
 */

public class CustomerPool {
    /**
     * the provided queue of Carts
     */
    private final TSQueue<Cart> checkoutLine;
    /**
     * the number of customers to create
     */
    private final int numCustomers;
    /**
     * the mean number of groceries in each Cart
     */
    private final int avgLoad;
    /**
     * the mean amount of time between Customer arrivals at checkout
     */
    private final double avgDelay;

    /**
     * Store all the parameter values for later use. Also, initialize a Random number generator.
     * @param checkoutLine the provided queue of Carts
     * @param numCustomers the number of customers to create
     * @param avgLoad the mean number of groceries in each Cart
     * @param avgDelay the mean amount of time between Customer arrivals at checkout
     */

    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers, int avgLoad, double avgDelay){
        this.checkoutLine = checkoutLine;
        this.numCustomers = numCustomers;
        this.avgDelay = avgDelay;
        this.avgLoad = avgLoad;
    }

    /**
     * Create the given number of customers, start them all up on separate threads,
     * and wait for them to finish. In the process, a Cart will be created for each Customer.
     * Each cart has a random number of groceries placed in it.
     * Each customer is told to wait a random amount of time more than the previous customer.
     * @return the list of Cart objects made for all the Customers.
     */
    public List<Cart> simulateCustomers(){
        List<Thread> threadsList = new ArrayList<>();
        List<Cart> cartList = new ArrayList<>();
        double waitTime = 0;
        for (int i = 0; i < numCustomers; i++){
            Cart cart = new Cart( (int) Utilities.sinePDFDelay(new Random(), avgLoad));
            Customer customer = new Customer(waitTime, cart, checkoutLine);
            waitTime += Utilities.sinePDFDelay(new Random(), avgDelay);
            Thread customerThread = new Thread(customer);
            threadsList.add(customerThread);
            cartList.add(cart);
            customerThread.start();
        }
        for (Thread thread: threadsList){
            try{
                thread.join();
            } catch (InterruptedException ignored){}
        }

        return cartList;
    }

}
