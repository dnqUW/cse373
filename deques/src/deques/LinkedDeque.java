package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    Node<T> sentFront;
    Node<T> sentBack;
    Node<T> front;
    Node<T> back;


    // Constructs the deque
    public LinkedDeque() {
        size = 0; // size of deque
        sentFront = new Node<>((T) "dummy"); // Points to the front of the deque
        sentBack = new Node<>((T) "dummy"); // Points to the back of the deque
        front = sentFront;
        back = sentBack;
    }


    // adds the given item to the front of the deque
    public void addFirst(T item) {
        size += 1;
        // if (front == null) {
        //      back = new Node<>(item);
        //  }
        sentFront.prev = new Node<>(item, null, sentFront); // dummy node is moving along with node
        front = sentFront;

    }

    // adds the given item to the back of the deque
    public void addLast(T item) {
        size += 1;
        // if (back == null) {
        //     front = new Node<>(item);
        // }
        sentBack.next = new Node<>(item, sentBack.prev, null); // dummy node is moving along with node
        back = sentBack;
    }

    // Returns the value of the first item in the list, and removes it.
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        sentFront = sentFront.next;
        return front.value; // return front.value;
    }

    // Returns the value of the last item in the list, and removes it.
    public T removeLast() {
        if (size == 0) {
            return null;

        }
        // back = new Node<>();
        // back.prev = null;
        // back = back.prev;
        // back.prev = null;
        size -= 1;
        T result = sentBack.value;
        sentBack = sentBack.prev;
        return result; // return back.value;
        // Or a holder for the value and then remove If we look at testing, we know that
        // assert.removeFirst/Last returns an actual number. Maybe we check value
    }

    // Returns the item at a given index.
    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> curr = front;
        for (int i = 0; i < index; i++) { // would 0 work
            curr = curr.next;
        }
        return curr.value;
    }

    // Returns the amount of items in the deque.
    public int size() {
        return size;
    }
}
