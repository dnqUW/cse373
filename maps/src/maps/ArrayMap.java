package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10; // Thinking 10 because eventually implement mod operator
    private int size;
    // private Object key
    // private Object value
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    // You may add extra fields or helper methods though!

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        size = 0;
        // Is it a map yet??? When I call the methods am I directly changing the map?
        // Or do I somehow need to link said array to the map.
        // Do i implement a resize method?
        // Hmm.. Iterator? Iterator<Entry<K, V>> iter = entries.iterator()
        // key = null;
        // value = null;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == key) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size == entries.length) {
            entries = resize();
        }
        // if (size == 0) { // inserting (key, value) at the first index since map is size of 0
        //     entries[0] = new SimpleEntry<>(key, value);
        // } else {
            for (int i = 0; i < size; i++) { // if Key exists
                if (key != null && entries[i].getKey() == key) {
                    V oldVal = entries[i].getValue();
                    entries[i].setValue(value);
                    return oldVal;
                }
            }
            entries[size] = new SimpleEntry<>(key, value);
        //}
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < size; i++) {
            if (key != null && entries[i].getKey() == key) {
                V removedV = entries[i].getValue();
                entries[i] = entries[size - 1];
                entries[size - 1] = null;
                size--;
                return removedV;
            }
        }
        return null;
    }


    @Override
    public void clear() {
        Arrays.fill(entries, null);
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private SimpleEntry<K, V>[] resize() {
        SimpleEntry<K, V>[] newArrayMap = createArrayOfEntries(2 * size);
        for (int i = 0; i < size; i++) {
            newArrayMap[i] = entries[i];
        }
        return newArrayMap;
    }

    // You may NOT create any new temporary data structures inside of your iterators.
    // We want our iterators to be efficient, and having to copy the contents of our
    // map to some other data structure at any point is suboptimal.
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ArrayMapIterator<>(this.entries);
    }

    // // Doing so will give you a better string representation for assertion errors the debugger.
    // @Override
    // public String toString() {
    //     return super.toString();
    // }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int currIndex;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            currIndex = 0;
        }

        @Override
        public boolean hasNext() {
            //return (entries[currIndex + 1] != null);
            return ((entries.length - 1 > currIndex) && (entries[currIndex] != null));
        }

        @Override
        public Map.Entry<K, V> next() {
            if (this.hasNext()) {
                Map.Entry<K, V> entry = entries[currIndex]; // + 1?
                currIndex++;
                return entry;
            } else {
                throw new NoSuchElementException();
            }

        }
    }
}
