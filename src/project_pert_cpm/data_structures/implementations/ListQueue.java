package data_structures.implementations;

import data_structures.interfaces.Queue;
import data_structures.interfaces.List;


public class ListQueue<T> implements Queue<T> {
    private final List<T> list;


    public ListQueue() {
        this.list = new ArrayList<>();
    }

    public ListQueue(List<T> backingList) {
        this.list = backingList;
    }

    @Override
    public void enqueue(T item) {
        list.add(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Очередь пуста");
        }

        T item = list.get(0);
        list.remove(0);
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Очередь пуста");
        }

        return list.get(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public String toString() {
        return "Queue: " + list.toString();
    }
}