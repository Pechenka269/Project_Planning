package data_structures_tests;

import data_structures.implementations.LinkedList;
import data_structures.interfaces.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Test
    public void testEmptyList() {
        List<Integer> list = new LinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddAndGet() {
        List<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testSet() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.set(1, 20);
        assertEquals(20, list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    public void testRemoveFromBeginning() {
        List<String> list = new LinkedList<>("A", "B", "C");

        list.remove(0);
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    public void testRemoveFromMiddle() {
        List<String> list = new LinkedList<>("A", "B", "C");

        list.remove(1);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    public void testRemoveFromEnd() {
        List<String> list = new LinkedList<>("A", "B", "C");

        list.remove(2);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    public void testContains() {
        List<Integer> list = new LinkedList<>(1, 2, 3, null, 5);

        assertTrue(list.contains(1));
        assertTrue(list.contains(3));
        assertTrue(list.contains(null));
        assertFalse(list.contains(10));
    }

    @Test
    public void testClear() {
        List<String> list = new LinkedList<>("A", "B", "C");

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testIndexOutOfBounds() {
        List<Integer> list = new LinkedList<>(1, 2, 3);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
    }

    @Test
    public void testAdditionalMethods() {
        LinkedList<String> list = new LinkedList<>();


        list.addFirst("B");
        list.addFirst("A");
        assertEquals("A", list.getFirst());


        list.add("C");
        assertEquals("C", list.getLast());
    }

    @Test
    public void testPerformanceAddManyElements() {
        List<Integer> list = new LinkedList<>();
        int count = 1000;

        for (int i = 0; i < count; i++) {
            list.add(i);
        }

        assertEquals(count, list.size());
        assertEquals(0, list.get(0));
        assertEquals(999, list.get(999));
    }
}
