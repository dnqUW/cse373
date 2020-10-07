package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;


/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode curr = list.front.next.next;
        curr.next = list.front.next;
        curr.next.next = list.front.next;
        curr.next.next.next = list.front;
        curr.next.next.next = null;
        list.front = curr;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null) {
            ListNode curr = list.front;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = list.front;
            list.front = list.front.next;
            curr.next.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        LinkedIntList combined = new LinkedIntList();
        if (b.front == null) {
            combined = a;
        } else if (a.front == null) {
            combined = b;
        } else {
            ListNode curr = a.front;
            combined.front = new ListNode(curr.data);
            ListNode curr2 = combined.front;
            while (curr.next != null) {
                curr = curr.next;
                curr2.next = new ListNode(curr.data);
                curr2 = curr2.next;
            }
            curr2.next = b.front;
        }
        return combined;
    }
}
