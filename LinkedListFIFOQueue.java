/**
 * A Queue (FIFO = First In, First Out) built using a Linked List instead
 * of an array (compare to ArrayCircularQueue from Submission 2, which
 * used a fixed-size array).
 *
 * THE TRICK:
 * - We keep two pointers: "front" (the first node, next to leave) and
 *   "rear" (the last node, where new numbers get added).
 * - ENQUEUE: create a new node, attach it after the current rear, then
 *   make it the new rear. O(1).
 * - DEQUEUE: take the value from front, then move front to front.next. O(1).
 *
 * Because it's a linked list, this queue never has a fixed capacity and
 * never needs to "wrap around" like the array version did - it simply
 * grows one node at a time for as long as you keep enqueuing.
 */
public class LinkedListFIFOQueue {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node front; // the next node to be removed
    private Node rear;  // the last node, where new numbers are added
    private int size;

    public LinkedListFIFOQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Add a number to the back of the queue. Always O(1).
    public void enqueue(int value) {
        Node newNode = new Node(value);
        if (rear == null) {
            // queue was empty - this new node is both front and rear
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Remove and return the number at the front of the queue. Always O(1).
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot dequeue");
        }
        int value = front.value;
        front = front.next;
        if (front == null) {
            rear = null; // the queue just became empty, so reset rear too
        }
        size--;
        return value;
    }

    // Look at the front number without removing it.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot peek");
        }
        return front.value;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    // Shows the queue from front to back, e.g. "[10, 20, 30]" means 10 leaves next.
    public String printState() {
        StringBuilder sb = new StringBuilder("[");
        Node current = front;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedListFIFOQueue queue = new LinkedListFIFOQueue();

        System.out.println("Step 1: enqueue 10");
        queue.enqueue(10);
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 2: enqueue 20");
        queue.enqueue(20);
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 3: enqueue 30");
        queue.enqueue(30);
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 4: peek");
        System.out.println("  Front is: " + queue.peek());

        System.out.println("Step 5: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 6: enqueue 40");
        queue.enqueue(40);
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 7: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 8: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Step 9: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        System.out.println("  Queue (front -> back): " + queue.printState());

        System.out.println("Is empty now? " + queue.isEmpty());
    }
}
