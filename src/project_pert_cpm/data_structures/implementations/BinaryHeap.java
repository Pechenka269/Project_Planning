package data_structures.implementations;

import data_structures.interfaces.Heap;
import data_structures.interfaces.List;


public class BinaryHeap<T extends Comparable<T>> implements Heap<T> {
    private final List<T> heap;

    public BinaryHeap() {
        this.heap = new ArrayList<>();
    }

    public BinaryHeap(int initialCapacity) {
        this.heap = new ArrayList<>(initialCapacity);
    }

    @Override
    public void insert(T item) {
        heap.add(item);
        heapifyUp(heap.size() - 1);
    }

    @Override
    public T extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Куча пуста");
        }
        T min = heap.get(0);
        T last = heap.get(heap.size() - 1);
        heap.set(0, last);
        heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heapifyDown(0);
        }

        return min;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Куча пуста");
        }
        return heap.get(0);
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public void clear() {
        heap.clear();
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = getParentIndex(index);

            if (heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();

        while (hasLeftChild(index)) {
            int smallestChildIndex = getLeftChildIndex(index);

            if (hasRightChild(index) &&
                    heap.get(getRightChildIndex(index)).compareTo(heap.get(smallestChildIndex)) < 0) {
                smallestChildIndex = getRightChildIndex(index);
            }

            if (heap.get(index).compareTo(heap.get(smallestChildIndex)) > 0) {
                swap(index, smallestChildIndex);
                index = smallestChildIndex;
            } else {
                break;
            }
        }
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < heap.size();
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < heap.size();
    }
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    @Override
    public String toString() {
        return "BinaryHeap: " + heap.toString();
    }
}