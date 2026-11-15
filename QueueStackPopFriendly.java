import java.util.Stack;

/**
 * A Queue (FIFO) built using two Stacks, but this version is "pop friendly":
 * removing a number (dequeue) is ALWAYS fast. Adding a number (enqueue) is
 * the one that has to do extra work every time.
 *
 * THE TRICK (opposite of the push-friendly version):
 * - We keep outStack always arranged so the front of the queue is on top,
 *   ready to pop straight away.
 * - When a new number comes in, we temporarily empty outStack into
 *   tempStack, put the new number in first (so it ends up at the very
 *   bottom = back of the queue), then pour everything back on top of it.
 */
public class QueueStackPopFriendly {

    private Stack<Integer> outStack;
    private Stack<Integer> tempStack;

    public QueueStackPopFriendly() {
        outStack = new Stack<Integer>();
        tempStack = new Stack<Integer>();
    }

    // Add a number to the back of the queue. Does extra work every time.
    public void enqueue(int value) {
        // Step 1: empty outStack into tempStack
        while (!outStack.isEmpty()) {
            tempStack.push(outStack.pop());
        }

        // Step 2: put the new value in first, so it sits at the back
        outStack.push(value);

        // Step 3: pour everything back on top, restoring order
        while (!tempStack.isEmpty()) {
            outStack.push(tempStack.pop());
        }
    }

    // Remove and return the front number. Always fast.
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot dequeue");
        }
        return outStack.pop();
    }

    // Look at the front number without removing it. Always fast.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot peek");
        }
        return outStack.peek();
    }

    public boolean isEmpty() {
        return outStack.isEmpty();
    }

    public int size() {
        return outStack.size();
    }

    // Shows what's inside outStack right now (front of queue is on top).
    private void printState() {
        System.out.println("    outStack (front is on top) : " + outStack);
    }

    public static void main(String[] args) {
        QueueStackPopFriendly queue = new QueueStackPopFriendly();

        System.out.println("Step 1: enqueue 10 (does the empty/refill shuffle, ends with just 10)");
        queue.enqueue(10);
        queue.printState();

        System.out.println("Step 2: enqueue 20 (shuffles 10 out, puts 20 at the bottom, puts 10 back on top)");
        queue.enqueue(20);
        queue.printState();

        System.out.println("Step 3: enqueue 30 (same shuffle again, 30 goes to the very bottom)");
        queue.enqueue(30);
        queue.printState();

        System.out.println("Step 4: peek - no shuffle needed, front is already on top");
        System.out.println("  Front is: " + queue.peek());
        queue.printState();

        System.out.println("Step 5: dequeue - instant, just pop the top");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 6: enqueue 40 (shuffles out 20 and 30, puts 40 at the bottom, restores order)");
        queue.enqueue(40);
        queue.printState();

        System.out.println("Step 7: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 8: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 9: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Final check - is empty now? " + queue.isEmpty());
    }
}