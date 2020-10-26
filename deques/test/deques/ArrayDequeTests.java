package deques;

import org.junit.jupiter.api.Test;

public class ArrayDequeTests extends BaseDequeTests {
    @Override
    protected <T> Deque<T> createDeque() {
        return new ArrayDeque<>();
    }

    @Override
    protected <T> void checkInvariants(Deque<T> deque) {
        // do nothing
    }

    // You can write additional tests here if you only want them to run for ArrayDequeTests and not LinkedDequeTests
    @Test
    void addManyRemoveAllSameSideAD() {
        Deque<String> deque = createDeque();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");
        deque.addFirst("7");
        deque.addFirst("8");
        deque.addFirst("9");
        deque.addFirst("10");
        deque.addFirst("11");
        deque.addFirst("12");
        deque.addFirst("13");
        deque.addFirst("14");
        deque.addFirst("15");
        deque.addFirst("16");
        deque.addFirst("17");
        deque.addFirst("18");

        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();

        checkInvariants(deque);
    }

    @Test
    void addManyRemoveAllOppositeSideAD() {
        Deque<String> deque = createDeque();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");
        deque.addFirst("7");
        deque.addFirst("8");
        deque.addFirst("9");
        deque.addFirst("10");
        deque.addFirst("11");
        deque.addFirst("12");
        deque.addFirst("13");
        deque.addFirst("14");
        deque.addFirst("15");
        deque.addFirst("16");
        deque.addFirst("17");
        deque.addFirst("18");

        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();

        checkInvariants(deque);
    }
}
