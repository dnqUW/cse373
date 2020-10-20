package maps;

import java.util.Iterator;
import java.util.Map;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10; // Thinking 10 because eventually implement mod operator
    private int size;
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
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].getKey() == key) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        // if (needsResize()) {
        //     resize();
        // }
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
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].getKey() == key) {
                V removedV = entries[i].getValue();
                entries[i] = entries[entries.length];
                entries[entries.length] = null;
                size--;
                return removedV;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < entries.length; i++) {
            entries[i] = null;
        }
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

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ArrayMapIterator<>(this.entries);
    }

    // // TODO: after you implement the iterator, remove this toString implementation
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
        }

        @Override
        public boolean hasNext() {
            if (currIndex < entries.length) {
                return true;
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            Map.Entry<K, V> entry = entries[currIndex];
            currIndex++;
            return entry;
        }
    }
}
