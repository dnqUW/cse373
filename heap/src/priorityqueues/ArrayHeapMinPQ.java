package priorityqueues;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    Map<T, Integer> itemsIndex;
    List<PriorityNode<T>> items;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>(DEFAULT_LENGTH);
        size = items.size();
        itemsIndex = new HashMap<>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        if (size > 0) {
            PriorityNode<T> temp = items.get(a);
            items.set(a, items.get(b));
            items.set(b, temp);
            itemsIndex.put(temp.getItem(), b);
            itemsIndex.put(items.get(b).getItem(), a);
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item already exists.");
        }
        items.add(new PriorityNode<T>(item, priority));
        itemsIndex.put(item, size);
        size++;
        percolateUp(size - 1);
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
        return items.get(START_INDEX).getItem();
    }

    @Override // Wrong invariant, index out of bounds, wrong values
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        swap(START_INDEX, size - 1);
        T val = items.remove(size - 1).getItem();
        size--;
        percolateDown(START_INDEX);
        return val;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
                throw new NoSuchElementException();
        }
        if (size > 0) {
            int index = indexOf(item);
            items.get(index).setPriority(priority);
            if (items.get(index).getPriority()
                < (items.get((index - 1) / 2)).getPriority()) {
                percolateDown(index);
            } else {
                percolateUp(size);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }


    private int indexOf(T item) {
        if (!itemsIndex.isEmpty() && itemsIndex.containsKey(item)) { // my guess has to do that we're calling .get(null)
            return itemsIndex.get(item);
        }
        return -1;
    }

    private void percolateUp(int currIndex) {
        int percolatedIndex = (currIndex - 1) / 2;
        if (size > 1) {
            if (items.get(currIndex) != null && items.get(currIndex).getPriority()
                < items.get(percolatedIndex).getPriority()) {
                swap(percolatedIndex, currIndex);
                percolateUp(percolatedIndex);
            }
        }
    }

    private void percolateDown(int currIndex) {
        int percolatedIndex = 2 * (currIndex + 1);
        if (percolatedIndex <= size) {
            if (items.get(currIndex).getPriority()
                < items.get(currIndex).getPriority()) {
                if (items.get(percolatedIndex - 1).getPriority()
                    < items.get(percolatedIndex).getPriority()) {
                    percolatedIndex++;
                }
            swap(currIndex, percolatedIndex);
            // currIndex = percolatedIndex; // wanted currIndex to be passed as the newly calculated index
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

        // private void resize() {
        //     PriorityNode<T>[] newArray = new PriorityNode[2 * items.size() + 1]; // nodes expected
        //     // Gives us i: if tree isnt full, if size < newArray.length { log2((newArray.length + 1) / 2)
        //     // Start iterating at i / 2
        //     // if (size != 0) {
        //     for (int i = 0; i < newArray.length; i++) {
        //         newArray[i] = itemsSimplified[i];
        //     }
        //     // }
        //     itemsSimplified = newArray;
        // }
    }

