package rit.stu;

import org.junit.jupiter.api.Test;
import rit.cs.Stack;
import rit.cs.StackList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A Junit Stack tester fpr StackNode and StackList
 *
 * @author RIT CS
 */
public class StackTester {
    /** Test a String based Stack. */
    public void testStackString(Stack<String> strStack) {
        assertTrue(strStack.empty());

        strStack.push("A");
        assertFalse(strStack.empty());
        assertEquals("A", strStack.top());

        assertEquals("A", strStack.pop());
        assertTrue(strStack.empty());
        assertThrows(AssertionError.class, strStack::pop);
        assertThrows(AssertionError.class, strStack::top);

        strStack.push("A");
        strStack.push("B");
        strStack.push("C");
        assertFalse(strStack.empty());
        assertEquals("C", strStack.pop());

        strStack.push("D");
        assertEquals("D", strStack.top());

        assertEquals("D", strStack.pop());
        assertEquals("B", strStack.pop());
        assertEquals("A", strStack.pop());
        assertTrue(strStack.empty());
    }

    /** Test an integer based Stack. */
    public void testStackInt(Stack<Integer> intStack) {
        assertTrue(intStack.empty());

        intStack.push(1);
        assertFalse(intStack.empty());
        assertEquals(1, intStack.top());

        assertEquals(1, intStack.pop());
        assertTrue(intStack.empty());
        assertThrows(AssertionError.class, intStack::pop);
        assertThrows(AssertionError.class, intStack::top);

        intStack.push(1);
        intStack.push(2);
        intStack.push(3);
        assertFalse(intStack.empty());
        assertEquals(3, intStack.pop());

        intStack.push(4);
        assertEquals(4, intStack.top());

        assertEquals(4, intStack.pop());
        assertEquals(2, intStack.pop());
        assertEquals(1, intStack.pop());
        assertTrue(intStack.empty());
    }

    @Test
    public void stackNodeTest() {
        Stack<String> stackStr = new StackNode<>();
        testStackString(stackStr);

        Stack<Integer> stackInt = new StackNode<>();
        testStackInt(stackInt);
    }

    @Test
    public void stackListTest() {
        Stack<String> stackStr = new StackList<>();
        testStackString(stackStr);

        Stack<Integer> stackInt = new StackList<>();
        testStackInt(stackInt);
    }
}
