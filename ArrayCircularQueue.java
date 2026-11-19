/**
 * A Circular Queue (FIFO) built using a FIXED-SIZE int array.
 *
 * A normal array-based queue wastes space: once you dequeue from the
 * front, those slots at the start of the array just sit empty forever
 * while rear keeps moving right, until the array fills up even though
 * there's "room" at the start. A CIRCULAR queue fixes this by wrapping
 * the rear pointer back around to index 0 once it reaches the end, as
 * long as there's free space - reusing the slots that were freed up by
 * earlier dequeues.
 *
 * THE TRICK:
 * - We keep a fixed-size array "data", plus "front" (index of the next
 *   number to remove) and "count" (how many numbers are currently
 *   stored).
 * - The rear (next free slot) is calculated as: (front + count) % capacity
 *   The "% capacity" (modulo) is what makes it wrap around.
 * - The queue is full when count == capacity. It's empty when count == 0.
 */
public class ArrayCircularQueue {

    private int[] data;
    private int front;   // index of the front element
    private int count;   // how many numbers are currently stored
    private int capacity;

    public ArrayCircularQueue(int capacity) {
        this.capacity = capacity;
        data = new int[capacity];
        front = 0;
        count = 0;
    }

    // Add a number to the back of the queue.
    public void enqueue(int value) {
        if (isFull()) {
            throw new RuntimeException("Queue is full - cannot enqueue");
        }
        int rear = (front + count) % capacity; // wraps around if needed
        data[rear] = value;
        count++;
    }

    // Remove and return the number at the front of the queue.
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot dequeue");
        }
        int value = data[front];
        front = (front + 1) % capacity; // move front forward, wrapping if needed
        count--;
        return value;
    }

    // Look at the front number without removing it.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot peek");
        }
        return data[front];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == capacity;
    }

    public int size() {
        return count;
    }

    // Shows the numbers currently in the queue, in front-to-back order,
    // even though they might be wrapped around inside the array.
    private void printState() {
        System.out.print("    queue contents (front -> back): [");
        for (int i = 0; i < count; i++) {
            int index = (front + i) % capacity;
            System.out.print(data[index]);
            if (i < count - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]  (front index=" + front + ", size=" + count + "/" + capacity + ")");
    }

    public static void main(String[] args) {
        // A small capacity of 4 so we can actually see the wrap-around happen.
        ArrayCircularQueue queue = new ArrayCircularQueue(4);

        System.out.println("Step 1: enqueue 10");
        queue.enqueue(10);
        queue.printState();

        System.out.println("Step 2: enqueue 20");
        queue.enqueue(20);
        queue.printState();

        System.out.println("Step 3: enqueue 30");
        queue.enqueue(30);
        queue.printState();

        System.out.println("Step 4: enqueue 40 (queue is now full, capacity 4)");
        queue.enqueue(40);
        queue.printState();

        System.out.println("Step 5: dequeue (frees up the first slot, index 0)");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 6: dequeue (frees up index 1)");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 7: enqueue 50 (wraps around and reuses index 0)");
        queue.enqueue(50);
        queue.printState();

        System.out.println("Step 8: enqueue 60 (wraps around and reuses index 1)");
        queue.enqueue(60);
        queue.printState();

        System.out.println("(Notice the queue never grew - it just reused freed-up slots)");

        while (!queue.isEmpty()) {
            System.out.println("Dequeue -> removed " + queue.dequeue());
            queue.printState();
        }
    }
}