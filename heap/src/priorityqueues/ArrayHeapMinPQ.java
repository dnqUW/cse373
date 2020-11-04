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
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    int size;
    T min;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        size = items.size();
        if (!items.isEmpty()) {
            min = items.get(0).getItem();
        } else { min = null; }
        // PriorityNode[] itemsSimplified = new PriorityNode[items.size()]; // needs to be longer? 2 * length
        //
        // for (int i = START_INDEX; i <= itemsSimplified.length; i++) {
        //     itemsSimplified[i] = items.get(i);
        //
        // }
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    // Let's break down ties using the name. Compare them based on names?
    // What if no name? higher value > lower value, and if string, take first char, and > check
    private void swap(int a, int b) {
        // PriorityNode temp = itemsSimplified[a]
        // itemsSimplified[a] = itemsSimplified[b];
        // itemsSimplified[b] = temp;
        // do we need to reorganize?
        // items.get(a) = items.get(b)
        // items.get(b) = temp;
    }

    @Override
    public void add(T item, double priority) {
        // add at an index algorithm =
        // itemsSimplified[items.size + START_INDEX] = new PriorityNode(item, priority); // 
    }

    @Override
    public boolean contains(T item) {
        return items.contains(item); // items.something.getItem().equals(item);
    }

    @Override
    public T peekMin() {
        int minIndex = findIndexOfMin();
        return items.get(minIndex).getItem();
    }

    @Override
    public T removeMin() {
        // need to shift everything in the array now? Or do we do array first and then etc
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        int minIndex = findIndexOfMin();
        return items.remove(minIndex).getItem();
    }

    @Override
    public void changePriority(T item, double priority) {
        findNode(item).setPriority(priority);
    }

    @Override
    public int size() {
        return size;
    }

    private PriorityNode<T> findNode(T item) {
        // Can use calculations to cut down finding?
        return null;
    }
    //
    // lets do binary search?
    // private int binarySearchForIndex {
    //     if (items.get(items.size() / 2).getPriority() <
    //        then something else do something else
    // }

    // This one used on NaiveMinPQ, and uses brute force/ Spec says don't do this.
    // private Optional<PriorityNode<T>> findNode(T item) {
    //     return this.items.stream()
    //         .filter(node -> node.getItem().equals(item))
    //         .findFirst();
    // }
    //
    private int findIndexOfMin() {
        // iterate through each index to find the one with the min-priority item
        return IntStream.range(0, this.items.size()).boxed()
            .min(Comparator.comparingDouble(i -> this.items.get(i).getPriority()))
            .orElseThrow();
    }
}
