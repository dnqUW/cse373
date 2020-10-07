package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : list) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
                if (map.get(word) >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output if the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> common = new HashMap<>();
        for (String name : m1.keySet()) {
            if (m2.containsKey(name) && m1.get(name).equals(m2.get(name))) {
               common.put(name, m1.get(name));
            }
        }
        return common;
    }
}
