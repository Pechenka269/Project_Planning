package data_structures.interfaces;

public interface List<T> {
    void add(T item);
    T get(int index);
    void set(int index, T item);
    void remove(int index);
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(T item);
}
