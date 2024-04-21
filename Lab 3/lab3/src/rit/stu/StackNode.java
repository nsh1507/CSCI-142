package rit.stu;

import rit.cs.Node;
import rit.cs.Stack;

/**
 * A stack implementation that uses a Node to represent the structure.
 *
 * @param <T> The type of data the stack will hold
 * @author RIT CS
 */
public class StackNode<T> implements Stack<T> {
    private Node<T> stack;
    /**
     * Create an empty stack.
     */
    public StackNode() {
        this.stack = null;
    }

    /**
     * Check for an empty stack
     * @return boolean whether the stack is null
     */
    @Override
    public boolean empty() {
        return this.stack == null;
    }

    /**
     * Remove a node from the top of the stack
     * @return T the data of the node being removed
     */
    @Override
    public T pop() {
        assert !empty();
        T pop = this.stack.getData();
        stack = stack.getNext();
        return pop;
    }

    /**
     * Add a node to the top of the stack
     * @param element the data of the node to be added
     */
    @Override
    public void push(T element) {
        this.stack = new Node<>(element, this.stack);
    }

    /**
     * @return T the data of the front node.
     */
    @Override
    public T top() {
        assert !empty();
        return this.stack.getData();
    }
}
