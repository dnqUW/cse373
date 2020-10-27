package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    // TODO: define reasonable default values for each of the following three fields
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;
    private int size;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        size = DEFAULT_INITIAL_CHAIN_COUNT;
        this.chains = createArrayOfChains(initialChainCount);
        for (int i = 0; i < initialChainCount; i++) {
            chains[i] = createChain(chainInitialCapacity);
        }
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        return chainHashedKey(key).get(key);
    }

    @Override
    public V put(K key, V value) {
        int hashedKey = Math.abs(key.hashCode()) % DEFAULT_INITIAL_CHAIN_COUNT;
        V oldVal = chains[hashedKey].get(key);
        chains[hashedKey].put(key, value);
        return oldVal;
    }

    @Override
    public V remove(Object key) {
        return chainHashedKey(key).remove(key);
    }

    @Override
    public void clear() {
        Arrays.fill(chains, null);
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return chainHashedKey(key).containsKey(key);
    }

    @Override
    public int size() {
        return size;
    }

    private AbstractIterableMap<K, V> chainHashedKey(Object key) {
        int hashedKey = Math.abs(key.hashCode()) % DEFAULT_INITIAL_CHAIN_COUNT;
        return chains[hashedKey];
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    // TODO: after you implement the iterator, remove this toString implementation
    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private int currIndex;
        // You may add more fields and constructor parameters

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            currIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return ((chains.length - 1 > currIndex) && (chains[currIndex] != null));
        }

        @Override
        public Map.Entry<K, V> next() {
            if (this.hasNext()) {
                AbstractIterableMap<K, V> entry = chains[currIndex]; // + 1?
                currIndex++;
                return (Entry<K, V>) entry;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
