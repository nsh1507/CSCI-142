package edu.rit.cs.grocerystore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nsh1507
 */

public class FIFOTSQueue< E extends TimedObject > implements TSQueue< E > {
    /**
     * the queue
     */
    private final List<E> queue;

    /**
     * Initialize the underlying data structure used for the queue.
     */
    public FIFOTSQueue() {
        this.queue = new ArrayList<>();
    }

    /**
     * Puts the value in the queue, and calls TimedObject.enterQueue() on the value.
     * @param value the value to be enqueued
     * @return the size of the queue after the value was added
     */
    @Override
    public synchronized int enqueue( E value ) {
        this.queue.add(value);
        value.enterQueue();
        this.notifyAll();
        return this.queue.size();
    }

    /**
     * Removes a value from the queue and calls TimedObject.exitQueue() on the value.
     * @return the value that has been in the queue for the longest period of time
     */
    @Override
    public synchronized E dequeue() {
       while (this.queue.isEmpty()) {
           try {
               this.wait();
           }
           catch (InterruptedException ignored){}
       }
       E removed = this.queue.remove(0);
       removed.exitQueue();
       return removed;
    }
}
