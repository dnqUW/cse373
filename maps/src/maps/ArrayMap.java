package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

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
        // This is read only, can probably change to for each
        if (size != 0) { // added in to check if a test works, it works, but still getting a
            // Cannot invoke "java.util.AbstractMap$SimpleEntry.getKey()" because "entry" is null
            // for every other test
            for (SimpleEntry<K, V> entry : entries) {
                if (entry.getKey() == key) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) { // what is there to return
        if (size == entries.length) {
            resize();
        }
        if (size == 0) { // IF EMPTY, ADD IN entries.containsKey
            entries[0] = new SimpleEntry<>(key, value);
            size++;
            return entries[0].getValue();
        } else {
            for (SimpleEntry<K, V> entry : entries) {
                if (entry.getKey() == key) { // ?? IF KEY EXISTS
                    entry.setValue(value);
                    return entry.getValue();
                } else { // IF KEY DOES NOT EXIST, maybe this whole method should be iter
                    entries[size - 1] = new SimpleEntry<>(key, value);
                    size++;
                    return entries[size - 1].getValue();
                    // while iter.hasNext(), iter = iter.next()? entries[size] = new SimpleEntry<>(key, value)
                    //
                }
            }
            return null;
            // entries[size - 1] = new SimpleEntry<>(key, value);
            // size++;
            // return entries[size - 1].getValue();
        }


        // for (int i = 0; i < entries.length; i++) {
        //     if (entries[i] != null && entries[i].getKey() != key) {
        //         entries[i].getKey() = key;
        //         entries[i].setValue(value);
        //         size++;
        //     } else if (entries[i].getKey() == key) {
        //         entries[i].setValue(value);
        //     }
        // }
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == key) {
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
        for (Object currKey : entries) {
            if (currKey == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    // You may NOT create any new temporary data structures inside of your iterators.
    // We want our iterators to be efficient, and having to copy the contents of our
    // map to some other data structure at any point is suboptimal.
    private SimpleEntry<K, V>[] resize() {
        SimpleEntry<K, V>[] newArrayMap = createArrayOfEntries(2 * size);
        for (int i = 0; i < size; i++) {
            newArrayMap[i] = entries[i];
        }
        return newArrayMap;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ArrayMapIterator<>(this.entries);
    }

    // TODO: after you implement the iterator, remove this toString implementation
    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int currIndex;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
        }

        @Override
        public boolean hasNext() {
            if (currIndex < entries.length && entries[currIndex + 1] != null) { // or size
                return true;
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            Map.Entry<K, V> entry = entries[currIndex + 1]; // + 1
            currIndex++;
            return entry;
        }
    }
}
