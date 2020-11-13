package priorityqueues;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    static final int DEFAULT_LENGTH = 7;
    List<PriorityNode<T>> items;
    PriorityNode<T>[] itemsSimplified;
    int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        size = items.size();
        itemsSimplified = new PriorityNode[DEFAULT_LENGTH];
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> temp = itemsSimplified[a]; // a = 2, b = 8, temp = 2.
        itemsSimplified[a] = itemsSimplified[b]; // a = 8
        itemsSimplified[b] = temp; // b = 2
        // Maybe here we can decide to percolateUp / Down
        // items.get(a) = items.get(b);
        //     setPriority(items.get(b).getPriority());
        // items.get(b).setPriority(temp.getPriority());
        // AL set(int index, E element)
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
        if (size != 1) {
            percolateUp(size);
        }
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

    @Override
    // Step 2 store first index, replace item in last index, and overwrite the first index
    // Step 3 set last index to null
    // Step 4 percolateDown(), maybe recurse with extra parameter i as level? compare bottom two elements, whichever has less Prio, swap
    // find 8's children, and the child should have more prio than parent
    public T removeMin() {
        // need to shift everything in the array now? Or do we do array first and then etc
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T val = items.remove(0).getItem();
        swap(0, size);
        items.set(size, null); // check if O(n)
        size--;
        percolateDown(0);
        return val;
        // swap(0, items.size()); // we use items.size() because array should be longer
        // Honestly thinking we might want to initialize itemsSimplified with items.size() * 2
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = indexOf(item);
        itemsSimplified[index].setPriority(priority);
        items.get(index).setPriority(priority);
        if (items.get(index).getPriority() > items.get((index - 1) / 2).getPriority()) {
            percolateUp(size);
        } else {
            percolateDown(0); // SHOULD BE findLevel, but 0 as placeholder
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
        if (size != 0) {
            for (int i = 0; i < itemsSimplified.length; i++) { // O(N)
                if (itemsSimplified[i].getItem() == item) { //Objects.equals(itemsSimplified[i].getItem(), item)
                    return i;
                }
            }
        }
        return -1;
    }

    private void percolateUp(int currSize) {
        // if item is greater than parents, and less than children then it stays
        // if item is less than parents move up
        int percolatedIndex = (currSize - 1) / 2;
        if (itemsSimplified[currSize].getPriority() > itemsSimplified[percolatedIndex].getPriority()) {
            swap(currSize, percolatedIndex);
            percolateUp(percolatedIndex);
        }
    }

    private void percolateDown(int currLevel) {
        int percolatedIndex = currLevel * 2;
            // or currLevel * 2 + 1;
        if (itemsSimplified[0].getPriority() > itemsSimplified[percolatedIndex].getPriority()) {
            swap(0, percolatedIndex);
            percolateDown(currLevel++);
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
    //     swap(itemsSimplified[START_INDEX], );
    // }

    // private int findLevel() {

    //     items.sublist(Math.pow(2, i),

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
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = itemsSimplified[i];
        }
        itemsSimplified = newArray;
    }
}
