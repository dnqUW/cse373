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
        size = 0; // is this size of chains horizontal? Or size of LL vert.
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
        int hashedKey = chainHashedKey(key);
        return chains[hashedKey].get(key);
    }

    @Override
    public V put(K key, V value) {
        int hashedKey = chainHashedKey(key);
        V oldVal = chains[hashedKey].get(key);
        chains[hashedKey].put(key, value);
        size++;
        if ((double) chains[hashedKey].size() / (double) chains.length > DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD) {
            chains = resizeAndRehash();
        }
        return oldVal;
    }

    // size inserting same element and replacing it
    @Override
    public V remove(Object key) {
        int hashedKey = chainHashedKey(key);
        if (containsKey(hashedKey)) {
            size--;
        }
        return chains[hashedKey].remove(key);
    }

    @Override
    public void clear() {
        Arrays.fill(chains, null);
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hashedKey = chainHashedKey(key);
        if (chains[hashedKey] != null) {
            return chains[hashedKey].containsKey(key);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private int chainHashedKey(Object key) {
        if (key != null) {
            return Math.abs(key.hashCode()) % chains.length;
        } else {
            return 0;
        }
    }

    private AbstractIterableMap<K, V>[] resizeAndRehash() {
        AbstractIterableMap<K, V>[] newChain = createArrayOfChains(2 * chains.length);
        for (Map.Entry<K, V> entry : this) {
            int newHashedKey = entry.getKey().hashCode();
            newChain[newHashedKey % newChain.length].put(entry.getKey(), entry.getValue());
        }
        return newChain;
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
        private Iterator<Map.Entry<K, V>> arrayMapIterator;
        // You may add more fields and constructor parameters

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            currIndex = 0;
            if (chains[currIndex] != null) {
                arrayMapIterator = chains[currIndex].iterator();
            }

            // for (int i = 0; i < chains.length; i++) {
            //     if (chains[i] != null || chains[i].size() != 0) {
            //         arrayMapIter = chains[i].iterator();
            //     }
            // }
        }
        @Override
        public boolean hasNext() {
            for (int i = currIndex; i < chains.length; i++) {
                if (arrayMapIterator != null && arrayMapIterator.hasNext()) {
                    return true;
                } else if (currIndex == chains.length - 1) {
                    return false;
                }
                currIndex++;
                if (chains[currIndex] != null) {
                    arrayMapIterator = chains[currIndex].iterator();
                }
            }
            return false;
        }

        @Override
        public Entry<K, V> next() {
            if (arrayMapIterator.hasNext()) {
                return arrayMapIterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
