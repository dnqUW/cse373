package deques;

import org.junit.jupiter.api.Test;

public class LinkedDequeTests extends BaseDequeTests {
    public static <T> LinkedDequeAssert<T> assertThat(LinkedDeque<T> deque) {
        return new LinkedDequeAssert<>(deque);
    }

    @Override
    protected <T> Deque<T> createDeque() {
        return new LinkedDeque<>();
    }

    @Override
    protected <T> void checkInvariants(Deque<T> deque) {
        // cast so we can use the LinkedDeque-specific version of assertThat defined above
        assertThat((LinkedDeque<T>) deque).isValid();
    }

    // You can write additional tests here if you only want them to run for LinkedDequeTests and not ArrayDequeTests
    @Test
    void addToFirstMany() {
        Deque<String> deque = createDeque();
        deque.addFirst("0");
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");
        deque.addFirst("7");
        deque.addFirst("8");
        deque.addFirst("9");

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
}
