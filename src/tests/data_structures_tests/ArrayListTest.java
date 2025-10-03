package data_structures_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data_structures.implementations.ArrayList;
import data_structures.interfaces.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {

    private List<Integer> list;
    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
    }
    @Test
    void newList_ShouldBeEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
    @Test
    void add_ShouldIncreaseSize() {
        list.add(10);

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
    }
    @Test
    void get_ShouldReturnCorrectElement() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    @Test
    void get_WithInvalidIndex_ShouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
    }
    @Test
    void set_ShouldReplaceElement() {
        list.add(10);
        list.add(20);

        list.set(1, 25);

        assertEquals(25, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void set_WithInvalidIndex_ShouldThrowException() {
        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(1, 20);
        });
    }
    @Test
    void remove_ShouldDecreaseSizeAndShiftElements() {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(1); // Удаляем 20

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void remove_FromEmptyList_ShouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(0);
        });
    }
    @Test
    void add_ShouldResizeWhenCapacityExceeded() {
        List<Integer> smallList = new ArrayList<>(2);

        smallList.add(1);
        smallList.add(2);
        smallList.add(3);

        assertEquals(3, smallList.size());
        assertEquals(Integer.valueOf(1), smallList.get(0));
        assertEquals(Integer.valueOf(2), smallList.get(1));
        assertEquals(Integer.valueOf(3), smallList.get(2));
    }
    @Test
    void contains_ShouldFindElements() {
        list.add(10);
        list.add(20);

        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
        assertFalse(list.contains(30));
        assertFalse(list.contains(null));
    }


    @Test
    void contains_WithNull() {
        list.add(null);
        list.add(10);

        assertTrue(list.contains(null));
        assertTrue(list.contains(10));
    }
    @Test
    void clear_ShouldMakeListEmpty() {

        list.add(10);
        list.add(20);

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add(30);
        assertEquals(1, list.size());
    }
    @Test
    void complexScenario_ShouldWorkCorrectly() {
        assertTrue(list.isEmpty());

        list.add(100);
        list.add(200);
        assertEquals(2, list.size());

        list.set(0, 150);
        assertEquals(150, list.get(0));

        list.remove(0);
        assertEquals(1, list.size());
        assertEquals(200, list.get(0));


        list.clear();
        assertTrue(list.isEmpty());

        list.add(300);
        assertEquals(1, list.size());
    }



}

