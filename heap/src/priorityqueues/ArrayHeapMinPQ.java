package priorityqueues;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    static final int DEFAULT_LENGTH = 8;

    int size;
    List<PriorityNode<T>> items;
    PriorityNode<T>[] itemsSimplified;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>(DEFAULT_LENGTH);
        size = items.size();
        itemsSimplified = new PriorityNode[DEFAULT_LENGTH];
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    // Let's break down ties using the name. Compare them based on names?
    // What if no name? higher value > lower value, and if string, take first char, and > check
    private void swap(int a, int b) {
        if (size > 0) {
            PriorityNode<T> temp = items.get(a);
            items.set(a, items.get(b));
            items.set(b, temp);
            itemsSimplified[a] = itemsSimplified[b];
            itemsSimplified[b] = temp;
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item already exists.");
        }
        if (size == itemsSimplified.length) {
            resize();
        }
        itemsSimplified[items.size() + START_INDEX] = new PriorityNode<T>(item, priority);
        items.add(new PriorityNode<T>(item, priority));
        size++;
        percolateUp(size);
    }

    @Override
    public boolean contains(T item) {
        return (indexOf(item) != -1);
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return itemsSimplified[START_INDEX].getItem();
    }

    @Override // Wrong invariant, index out of bounds, wrong values
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T val = items.remove(0).getItem();
        swap(0, size - 1);
        size--;
        percolateDown(0);
        if (size > 0) {
            items.set(size + 1, null);
            itemsSimplified[size + 1] = null;
        }
        return val;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
                throw new NoSuchElementException();
        }
        if (size > 0) {
            int index = indexOf(item);
            itemsSimplified[index].setPriority(priority);
            items.get(index).setPriority(priority);
            if (itemsSimplified[index].getPriority()
                < itemsSimplified[(index - 1) / 2].getPriority()) {
                percolateDown(0);
            } else {
                percolateUp(size);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    // Potentially could use subList(int fromIndex, int toIndex)
    // Comparing priorities in the middle level of the heap
    private int indexOf(T item) { //
        // int start = findLevel() / 2;
        // for (int i = start; i < itemsSimplified.length;) {
        //     if (item.equals(itemsSimplified[i].getItem())) {
        //         return i;
        //     } else if (itemsSimplified[i].getPriority() > itemsSimplified[i].getItem()){
        //         // instead of i++, put i++ in the i conditions
        //         i++;
        //     }
        // }
        if (size > 0) { // && itemsSimplified != null
            for (int i = 0; i < itemsSimplified.length; i++) { // O(N)
                if (itemsSimplified[i] != null && item == itemsSimplified[i].getItem()) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void percolateUp(int currIndex) {
        // if item is greater than parents, then it stays
        // if item is less than parents move up if item is less than parents move up
        int percolatedIndex = (currIndex - 1) / 2;
        if (size > 1) {
            if (itemsSimplified[currIndex] != null && itemsSimplified[currIndex].getPriority()
                < itemsSimplified[percolatedIndex].getPriority()) {
                swap(percolatedIndex, currIndex);
                percolateUp(percolatedIndex);
            }
        }
    }

    private void percolateDown(int currIndex) {
        int percolatedIndex = 2 * (currIndex + 1);
        if (percolatedIndex <= size) {
            if (itemsSimplified[percolatedIndex].getPriority()
                < itemsSimplified[currIndex].getPriority()) {
                if (itemsSimplified[percolatedIndex - 1].getPriority()
                    < itemsSimplified[percolatedIndex].getPriority()) {
                    percolatedIndex++;
                }
            swap(currIndex, percolatedIndex);
            currIndex = percolatedIndex;
            }
        }
    }


    // We don't know how to determine left or right
    // check index + 1 vs index + 2
    //
    // private void percolateDown() {
    //     if () { //itemsSimplified[START_INDEX].getPriority
    //         // if its less than children, stay
    //
    //     } else if () { // if its greater than child 1 <= child 2, move to child 1, and swap
    //         // if
    //     } else { // swap with child 2
    //         swap(itemsSimplified[START_INDEX], );
    //     }
    // }
        // private int findLevel() {
        //     items.sublist(Math.pow(2, i);
        //     if (size() < itemsSimplified.length) {
        //         return (int) (Math.log((itemsSimplified.length + 1) / 2) / Math.log(2));
        //     } else {
        //         return (int) (Math.log((items.size() + 1) / 2) / Math.log(2));
        //     }
        // }

        // Take,
        // i = log2(x + 1), i = level, and x = nodes per level
        // log2 ((items.size() + 1) / 2), full tree
        // find out if there are missing children nodesCur vs nodesExpected
        // What if we made it so the array was always the size of a full tree, if there is an empty
        // then its not a full tree.

        // So first, we want to go to the beginning of the middle level, and search
        // the amount of nodes that level has, use a calculation

        // private PriorityNode<T> findNode(T item, int totalItems) {
        //
        //     if(items.equals(itemsSimplified[totalItems].getItem()) {
        //         return
        //     } else {
        //
        //         findNode(item, totalItems / 2);
        //     }
        // }

        private void resize() {
            PriorityNode<T>[] newArray = new PriorityNode[2 * items.size() + 1]; // nodes expected
            // Gives us i: if tree isnt full, if size < newArray.length { log2((newArray.length + 1) / 2)
            // Start iterating at i / 2
            if (size != 0) {
                for (int i = 0; i < newArray.length; i++) {
                    newArray[i] = itemsSimplified[i];
                }
            }
            itemsSimplified = newArray;
        }
    }

