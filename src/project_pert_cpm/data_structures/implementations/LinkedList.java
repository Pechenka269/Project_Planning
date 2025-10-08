package data_structures.implementations;

import data_structures.interfaces.List;


public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    @SafeVarargs
    public LinkedList(T... elements) {
        this();
        for (T element : elements) {
            add(element);
        }
    }

    @Override
    public void add(T item) {
        Node<T> newNode = new Node<>(item);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        Node<T> current = getNode(index);
        return current.data;
    }


    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void set(int index, T item) {
        checkIndex(index);

        Node<T> node = getNode(index);
        node.data = item;
    }

    @Override
    public void remove(int index) {
        checkIndex(index);

        if (index == 0) {

            removeFirst();
        } else if (index == size - 1) {

            removeLast();
        } else {

            removeMiddle(index);
        }
        size--;
    }


    private void removeFirst() {
        if (head == tail) {

            head = null;
            tail = null;
        } else {
            head = head.next;
        }
    }


    private void removeLast() {
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<T> current = head;
            while (current.next != tail) {
                current = current.next;
            }
            current.next = null;
            tail = current;
        }
    }


    private void removeMiddle(int index) {
        Node<T> previous = getNode(index - 1);
        previous.next = previous.next.next;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(T item) {
        Node<T> current = head;
        while (current != null) {
            if (item == null) {
                if (current.data == null) return true;
            } else {
                if (item.equals(current.data)) return true;
            }
            current = current.next;
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Индекс: " + index + ", Размер: " + size
            );
        }
    }

    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Список пуст");
        }
        return head.data;
    }

    public T getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Список пуст");
        }
        return tail.data;
    }


    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
