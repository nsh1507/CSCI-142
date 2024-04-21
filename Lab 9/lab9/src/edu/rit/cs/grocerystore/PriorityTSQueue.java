package edu.rit.cs.grocerystore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nsh1507
 */

public class PriorityTSQueue< E extends TimedObject & Comparable< E > >
        implements TSQueue< E > {
    /**
     * the queue
     */
    private final List<E> queue;

    /**
     * Initialize the underlying data structure used for the queue.
     */
    public PriorityTSQueue() {
        this.queue = new LinkedList<>();
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
     * @return the minimum value in the queue, according to E's natural ordering
     */
    @Override
    public synchronized E dequeue() {
        while (this.queue.isEmpty()) {
            try {
                this.wait();
            }
            catch (InterruptedException ignored){}
        }

        E smallest = Collections.min(queue);
        queue.remove(smallest);
        smallest.exitQueue();
        return smallest;
    }
}
