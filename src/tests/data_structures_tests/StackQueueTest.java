package data_structures_tests;

import data_structures.implementations.ListStack;
import data_structures.implementations.ListQueue;
import data_structures.interfaces.Stack;
import data_structures.interfaces.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackQueueTest {


    @Test
    public void testStackPushPop() {
        Stack<Integer> stack = new ListStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(3, stack.size());
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testStackPeek() {
        Stack<String> stack = new ListStack<>();

        stack.push("first");
        stack.push("second");

        assertEquals("second", stack.peek());
        assertEquals(2, stack.size());
        assertEquals("second", stack.pop());
    }

    @Test
    public void testStackEmpty() {
        Stack<Integer> stack = new ListStack<>();

        assertTrue(stack.isEmpty());
        assertThrows(IllegalStateException.class, () -> stack.pop());
        assertThrows(IllegalStateException.class, () -> stack.peek());
    }

    @Test
    public void testStackClear() {
        Stack<String> stack = new ListStack<>();

        stack.push("A");
        stack.push("B");
        stack.clear();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }


    @Test
    public void testQueueEnqueueDequeue() {
        Queue<Integer> queue = new ListQueue<>();

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(3, queue.size());
        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testQueuePeek() {
        Queue<String> queue = new ListQueue<>();

        queue.enqueue("first");
        queue.enqueue("second");

        assertEquals("first", queue.peek());
        assertEquals(2, queue.size());
        assertEquals("first", queue.dequeue());
    }

    @Test
    public void testQueueEmpty() {
        Queue<Integer> queue = new ListQueue<>();

        assertTrue(queue.isEmpty());
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
        assertThrows(IllegalStateException.class, () -> queue.peek());
    }

    @Test
    public void testQueueClear() {
        Queue<String> queue = new ListQueue<>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }


    @Test
    public void testStackAndQueueTogether() {
        Stack<Integer> stack = new ListStack<>();
        Queue<Integer> queue = new ListQueue<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }
}