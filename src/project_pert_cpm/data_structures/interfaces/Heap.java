package data_structures.interfaces;

public interface Heap<T extends Comparable<T>> {
    void insert(T item);
    T extractMin();
    T peekMin();
    boolean isEmpty();
    int size();
    void clear();
}