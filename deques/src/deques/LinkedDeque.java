package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size; // Size of deque
    Node<T> front; // Points to the front of the deque
    Node<T> back; // Points to the back of the deque


    // Constructs the deque
    public LinkedDeque() {
        size = 0;
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        back.prev = front;
    }


    // adds the given item to the front of the deque
    public void addFirst(T item) {
        size += 1;
        front.next = new Node<>(item, front, front.next); // right side is evaluated first
        front.next.next.prev = front.next;
    }

    // adds the given item to the back of the deque
    public void addLast(T item) {
        size += 1;
        back.prev = new Node<>(item, back.prev, back); // right side evaluated first
        back.prev.prev.next = back.prev;
    }

    // Returns the value of the first item in the list, and removes it.
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T result = front.next.value;
        front.next.next.prev = front;
        front.next = front.next.next;
        return result;
    }

    // Returns the value of the last item in the list, and removes it.
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T result = back.prev.value;
        back.prev.prev.next = back;
        back.prev = back.prev.prev;
        return result;
    }

    // Returns the item at a given index.
    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> curr;
        int newIndex = size - index;
        if (newIndex < index) {
            curr = back.prev;
            for (int i = size; i > index + 1; i--) {
                curr = curr.prev;
            }
        } else {
            curr = front.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
        }
        return curr.value;
    }

    // Returns the amount of items in the deque.
    public int size() {
        return size;
    }
}
