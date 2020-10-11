package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    Node<T> front;
    Node<T> back;


    // Constructs the deque
    public LinkedDeque() {
        size = 0; // size of deque
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back; // Points to the front of the deque // two nodes, front and back. sentFront represents the next field of front, sentBack is the last item in list
        back.prev = front; // Points to the back of the deque // front next to point to back // these ensures these two are linked, and are circular
    }


    // adds the given item to the front of the deque
    public void addFirst(T item) {
        size += 1;
        Node<T> temp = new Node<>(item);
        temp.next = front;
        front = temp;
        // Old front doesnt have a prev link, when 2nd node added,
        // it gets deleted

    }

    // adds the given item to the back of the deque
    public void addLast(T item) {
        size += 1;
        Node<T> temp = new Node<>(item);
        temp.prev = back;
        back = temp;
    }

    // Returns the value of the first item in the list, and removes it.
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T result = front.value;
        front = front.next;
        return result; // return front.value;
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
        T result = back.value;
        back = back.prev;
        return result;
        // return back.value;
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
