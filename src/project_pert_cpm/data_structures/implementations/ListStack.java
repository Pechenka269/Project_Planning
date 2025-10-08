package data_structures.implementations;

import data_structures.interfaces.Stack;
import data_structures.interfaces.List;


public class ListStack<T> implements Stack<T> {
    private final List<T> list;

    public ListStack() {
        this.list = new ArrayList<>();
    }

    public ListStack(List<T> backingList) {
        this.list = backingList;
    }

    @Override
    public void push(T item) {
        list.add(item);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }

        T item = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }

        return list.get(list.size() - 1);
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
        return "Stack: " + list.toString();
    }
}