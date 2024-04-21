package rit.stu;

import org.junit.jupiter.api.Test;
import rit.cs.Queue;
import rit.cs.QueueList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A Junit Stack tester fpr StackNode and StackList
 *
 * @author RIT CS
 */
public class QueueTester {
    /** Test a String based Queue. */
    public void testQueueString(Queue<String> strQueue) {
        assertTrue(strQueue.empty());

        strQueue.enqueue("A");
        assertFalse(strQueue.empty());
        assertEquals("A", strQueue.front());
        assertEquals("A", strQueue.back());

        assertEquals("A", strQueue.dequeue());
        assertTrue(strQueue.empty());
        assertThrows(AssertionError.class, strQueue::front);
        assertThrows(AssertionError.class, strQueue::back);

        strQueue.enqueue("A");
        strQueue.enqueue("B");
        strQueue.enqueue("C");
        assertFalse(strQueue.empty());
        assertEquals("A", strQueue.front());
        assertEquals("C", strQueue.back());
        assertEquals("A", strQueue.dequeue());

        strQueue.enqueue("D");
        assertEquals("B", strQueue.front());
        assertEquals("D", strQueue.back());

        assertEquals("B", strQueue.dequeue());
        assertEquals("C", strQueue.dequeue());
        assertEquals("D", strQueue.dequeue());
        assertTrue(strQueue.empty());
    }

    /** Test an integer based Queue. */
    public void testQueueInt(Queue<Integer> strQueue) {
        assertTrue(strQueue.empty());

        strQueue.enqueue(1);
        assertFalse(strQueue.empty());
        assertEquals(1, strQueue.front());
        assertEquals(1, strQueue.back());

        assertEquals(1, strQueue.dequeue());
        assertTrue(strQueue.empty());
        assertThrows(AssertionError.class, strQueue::front);
        assertThrows(AssertionError.class, strQueue::back);

        strQueue.enqueue(1);
        strQueue.enqueue(2);
        strQueue.enqueue(3);
        assertFalse(strQueue.empty());
        assertEquals(1, strQueue.front());
        assertEquals(3, strQueue.back());
        assertEquals(1, strQueue.dequeue());

        strQueue.enqueue(4);
        assertEquals(2, strQueue.front());
        assertEquals(4, strQueue.back());

        assertEquals(2, strQueue.dequeue());
        assertEquals(3, strQueue.dequeue());
        assertEquals(4, strQueue.dequeue());
        assertTrue(strQueue.empty());
    }

    @Test
    public void queueNodeTest() {
        Queue<String> queueStr = new QueueNode<>();
        testQueueString(queueStr);

        Queue<Integer> queueInt = new QueueNode<>();
        testQueueInt(queueInt);
    }

    @Test
    public void queueListTest() {
        Queue<String> queueStr = new QueueList<>();
        testQueueString(queueStr);

        Queue<Integer> queueInt = new QueueList<>();
        testQueueInt(queueInt);
    }
}
