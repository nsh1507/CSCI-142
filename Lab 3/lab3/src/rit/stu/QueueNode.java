package rit.stu;

import rit.cs.Node;
import rit.cs.Queue;

/**
 * A queue implementation that uses a Node to represent the structure.
 *
 * @param <T> The type of data the queue will hold
 * @author RIT CS
 */
public class QueueNode<T> implements Queue<T> {
    private Node<T> front;
    private Node<T> back;

    /**
     * Create an empty queue.
     */
    public QueueNode() {
        this.front = null;
        this.back = null;
    }

    /**
     * @return T the data of back node
     */
    @Override
    public T back() {
        assert !empty();
        return this.back.getData();
    }

    /**
     * Remove a node from the queue
     * @return T the data of the node being removed
     */
    @Override
    public T dequeue() {
        assert !empty();
        T element = this.front.getData();
        this.front = this.front.getNext();
        if (empty()) {
            this.back = null;
        }
        return element;
    }

    /**
     * Check for an empty queue
     * @return boolean whether the queue is null
     */
    @Override
    public boolean empty() {
        return this.front == null;
    }

    /**
     * Add a node to the end of the queue
     * @param element the data of a node being added to the queue
     */
    @Override
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element, null);
        if(empty()){
            this.front = newNode;
        }
        else{
            this.back.setNext(newNode);
        }
        this.back = newNode;
    }

    /**
     * @return T the data of the front node.
     */
    @Override
    public T front() {
        assert !empty();
        return this.front.getData();
    }
}
