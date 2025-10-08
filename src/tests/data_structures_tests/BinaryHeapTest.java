package data_structures_tests;

import core.entities.Task;
import data_structures.implementations.BinaryHeap;
import data_structures.interfaces.Heap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryHeapTest {

    @Test
    public void testInsertAndExtractMin() {
        Heap<Integer> heap = new BinaryHeap<>();

        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);

        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(8, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testPeekMin() {
        Heap<Integer> heap = new BinaryHeap<>();

        heap.insert(10);
        heap.insert(5);

        assertEquals(5, heap.peekMin());
        assertEquals(2, heap.size());
        assertEquals(5, heap.extractMin());
    }

    @Test
    public void testEmptyHeap() {
        Heap<Integer> heap = new BinaryHeap<>();

        assertTrue(heap.isEmpty());
        assertThrows(IllegalStateException.class, () -> heap.extractMin());
        assertThrows(IllegalStateException.class, () -> heap.peekMin());
    }

    @Test
    public void testClear() {
        Heap<String> heap = new BinaryHeap<>();

        heap.insert("C");
        heap.insert("A");
        heap.insert("B");

        heap.clear();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test
    public void testWithCustomObjects() {
        Heap<Task> taskHeap = new BinaryHeap<>();

        Task task1 = new Task("1", "High Priority", 2);
        Task task2 = new Task("2", "Medium Priority", 5);
        Task task3 = new Task("3", "Low Priority", 10);

        taskHeap.insert(task3);
        taskHeap.insert(task1);
        taskHeap.insert(task2);

        assertEquals(task1, taskHeap.extractMin());
        assertEquals(task2, taskHeap.extractMin());
        assertEquals(task3, taskHeap.extractMin());
    }
}