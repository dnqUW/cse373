package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers; // related, still have pointers
    private Map<T, Integer> ids;
    private int size;
    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
        ids = new HashMap<>();
        size = 0;
    }

    @Override
    public void makeSet(T item) {
        pointers.add(-1);
        ids.put(item, this.size);
        size++;
    }

    @Override
    public int findSet(T item) {
        Integer index = this.ids.get(item);
        if (index == null) {
            throw new IllegalArgumentException(item + " is not in any set.");
        }
        // Returns the index of the overall parent
        // record where we have visited
        Set<Integer> set = new HashSet<>();
        while (pointers.get(index) >= 0) {
            set.add(index);
            index = pointers.get(index); // index
        }
        // int overallRoot = index;
        // while (pointers.get(index) >= 0) {
        //     int parent = pointers.get(index);
        //     pointers.set(index, overallRoot);
        //     index = parent;
        // }
        // for (Integer i : set) {
        //     if (pointers.get(index) >= 0) { // check if its overallRoot
        //         pointers.set(i, index);
        //     }
        // }
        return index;
    }

    @Override
    public boolean union(T item1, T item2) {
        int id1 = findSet(item1);
        int id2 = findSet(item2);
        if (id1 == id2) {
            return false;
        }
        this.ids.replaceAll((item, rep) -> rep == id1 ? id2 : rep);
        // int size1 = pointers.get(id1); // overallRoot1
        // int size2 = pointers.get(id2); // overallRoot2
        // int sizeSum = size1 + size2;
        // if (size1 >= size2) {
        //     pointers.set(id2, id1);
        //     pointers.set(id2, -sizeSum);
        // } else {
        //     pointers.set(id1, id2);
        //     pointers.set(id1, -sizeSum);
        // }
        return true;
    }
}
