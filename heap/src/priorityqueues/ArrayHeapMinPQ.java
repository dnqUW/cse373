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
    List<PriorityNode<T>> items;
    PriorityNode<T>[] itemsSimplified;
    int size;
    T min;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>(); // 10 nulls
        size = items.size();
        if (!items.isEmpty()) {
            min = items.get(0).getItem();
        } else { min = null; }
         itemsSimplified = new PriorityNode[items.size()]; // needs to be longer? 2 * length

        // for (int i = START_INDEX; i < itemsSimplified.length; i++) {
        //     itemsSimplified[i] = items.get(i);
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
        PriorityNode<T> temp = itemsSimplified[a]; // a = 2, b = 8, temp = 2.
        itemsSimplified[a] = itemsSimplified[b]; // a = 8
        itemsSimplified[b] = temp; // b = 2
        // Maybe here we can decide to percolateUp / Down
        // items.get(a) = items.get(b);
        //     setPriority(items.get(b).getPriority());
        // items.get(b).setPriority(temp.getPriority());
    }

    @Override
    public void add(T item, double priority) {
        // add at an index algorithm =
        // itemsSimplified[items.size + START_INDEX] = new PriorityNode(item, priority); // 
    }

    @Override // introduce private method
    // call it, and then return items.contains(indexOf(item)), need a dummy val, -1?
    // return (indexOf == -1)
    public boolean contains(T item) {
        // return items.contains(item); // items.something.getItem().equals(item);
        return (indexOf(item) == -1);
    }

    @Override
    public T peekMin() {
        return min;
    } // Either do this, or return itemsSimplified[START_INDEX]

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
        return items.remove(0).getItem();
        // swap(0, items.size()); // we use items.size() because array should be longer
        // Honestly thinking we might want to initialize itemsSimplified with items.size() * 2
    }

    @Override
    public void changePriority(T item, double priority) {
        //items.get(indexOf(item).setPriority(priority);
        itemsSimplified[indexOf(item)].setPriority(priority);
    }

    @Override
    public int size() {
        return size;
    }


    // Comparing priorities in the middle level of the heap
    private int indexOf(T item) {
        for (int i = START_INDEX; i < itemsSimplified.length;) {
            if (item.equals(itemsSimplified[i].getItem())) {
                return i;
            } else {
                // instead of i++, put i++ in the i conditions
                i++;
            }
        }

    }

    // So first, we want to go to the beginning of the middle level, and search
    // the amount of nodes that level has, use a calculation
    // To find level, we could do number of levels,
    // int temp = .size() / 2
    // check indices (temp / 2)  + 1 to .size() / 2
    // private PriorityNode<T> findNode(T item, int totalItems) { // how do we start?
    // would we search each node????? No I think we need to use the calculations
    //
    //     if(items.equals(itemsSimplified[totalItems].getItem()) {
    //         return
    //     } else {
    //
    //         findNode(item, totalItems / 2);
    //     }
    // }

    // private void resize() {
    //     PriorityNode[] newArray = new PriorityNode[2 * items.size()]
    //     for(int i = 0; i < ; i++) {
    //         newArray[i] = itemsSimplified[i];
    //     }
    //     itemsSimlpified = newArray[i];
    // }

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
    // private int findIndexOfMin() {
    //     // iterate through each index to find the one with the min-priority item
    //     return IntStream.range(0, this.items.size()).boxed()
    //         .min(Comparator.comparingDouble(i -> this.items.get(i).getPriority()))
    //         .orElseThrow();
    // }
}
