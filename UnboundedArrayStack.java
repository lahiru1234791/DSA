/**
 * A Stack (LIFO = Last In, First Out) built by hand using a plain int array
 * instead of java.util.Stack. "Unbounded" means it has no fixed maximum
 * size - the array grows when it fills up, and shrinks when it gets mostly
 * empty, so you never have to decide a size in advance.
 *
 * THE TRICK:
 * - We keep an int array called "data" and a counter "count" that tells us
 *   how many numbers are currently stored (and also points to the next
 *   free slot).
 * - GROW: if the array is full and we try to push, we make a NEW, bigger
 *   array (double the size), copy everything across, then continue.
 * - SHRINK: if we pop and the array becomes mostly empty (only using a
 *   quarter of its space), we make a NEW, smaller array (half the size)
 *   and copy everything across, so we're not wasting memory.
 */
public class UnboundedArrayStack {

    private int[] data;
    private int count;              // how many numbers are stored right now
    private static final int MIN_CAPACITY = 4;

    public UnboundedArrayStack() {
        data = new int[MIN_CAPACITY];
        count = 0;
    }

    // Add a number to the top of the stack.
    public void push(int value) {
        if (count == data.length) {
            resize(data.length * 2); // array is full -> make it bigger
        }
        data[count] = value;
        count++;
    }

    // Remove and return the top number.
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot pop");
        }
        count--;
        int value = data[count];

        // If we're only using a quarter of the array, shrink it in half
        if (count > 0 && count == data.length / 4 && data.length / 2 >= MIN_CAPACITY) {
            resize(data.length / 2);
        }
        return value;
    }

    // Look at the top number without removing it.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot peek");
        }
        return data[count - 1];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    // Lets us see the current array size when testing (not required by the
    // assignment, just handy for showing how the resizing works).
    public int capacity() {
        return data.length;
    }

    // Makes a new array of the given size and copies the old values into it.
    private void resize(int newCapacity) {
        int[] newData = new int[newCapacity];
        for (int i = 0; i < count; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    // Shows only the slots that are actually in use right now, bottom to top.
    private void printState() {
        System.out.print("    contents (bottom -> top): [");
        for (int i = 0; i < count; i++) {
            System.out.print(data[i]);
            if (i < count - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]  size=" + count + "  capacity=" + data.length);
    }

    public static void main(String[] args) {
        UnboundedArrayStack stack = new UnboundedArrayStack();

        System.out.println("Start: empty array, capacity " + stack.capacity());
        stack.printState();

        for (int i = 1; i <= 5; i++) {
            int value = i * 10;
            System.out.println("Step " + i + ": push " + value);
            stack.push(value);
            stack.printState();
        }
        System.out.println("(Notice the capacity doubled from 4 to 8 once the 5th value needed a spot)");

        System.out.println("\nTop of stack right now: " + stack.peek());

        int step = 6;
        while (!stack.isEmpty()) {
            int popped = stack.pop();
            System.out.println("Step " + step + ": pop -> removed " + popped);
            stack.printState();
            step++;
        }
        System.out.println("(Notice the capacity shrank back down as the stack emptied out)");
    }
}