import java.util.Stack;

/**
 * A Queue (FIFO = First In, First Out) built using two Stacks (LIFO = Last In, First Out).
 *
 * This version is "push friendly": adding a number to the queue (enqueue) is
 * always fast. Removing a number (dequeue) is usually fast too, but once in a
 * while it has to do extra work.
 *
 * THE TRICK:
 * - We keep two stacks: inStack and outStack.
 * - Every new number goes straight into inStack. That's why enqueue is fast.
 * - When someone wants to dequeue, we look in outStack first.
 *   - If outStack is empty, we pour everything from inStack into outStack.
 *     Pouring one stack into another REVERSES the order, which happens to
 *     put the numbers back into the correct queue order (oldest on top).
 *   - Then we just pop from outStack like normal.
 */
public class QueueStackPushFriendly {

    private Stack<Integer> inStack;
    private Stack<Integer> outStack;

    public QueueStackPushFriendly() {
        inStack = new Stack<Integer>();
        outStack = new Stack<Integer>();
    }

    // Add a number to the back of the queue. Always fast.
    public void enqueue(int value) {
        inStack.push(value);
    }

    // Remove and return the number at the front of the queue.
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot dequeue");
        }
        moveInStackToOutStackIfNeeded();
        return outStack.pop();
    }

    // Look at the front number without removing it.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot peek");
        }
        moveInStackToOutStackIfNeeded();
        return outStack.peek();
    }

    // Only move numbers across when outStack has nothing left in it.
    private void moveInStackToOutStackIfNeeded() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    public int size() {
        return inStack.size() + outStack.size();
    }

    // Shows what's inside both stacks right now, so we can see each step happen.
    private void printState() {
        System.out.println("    inStack (newest on right) : " + inStack);
        System.out.println("    outStack (front is on top) : " + outStack);
    }

    // Step-by-step demo so you can run this file directly and see it working.
    public static void main(String[] args) {
        QueueStackPushFriendly queue = new QueueStackPushFriendly();

        System.out.println("Step 1: enqueue 10");
        queue.enqueue(10);
        queue.printState();

        System.out.println("Step 2: enqueue 20");
        queue.enqueue(20);
        queue.printState();

        System.out.println("Step 3: enqueue 30");
        queue.enqueue(30);
        queue.printState();

        System.out.println("Step 4: peek (this triggers the pour from inStack to outStack)");
        System.out.println("  Front is: " + queue.peek());
        queue.printState();

        System.out.println("Step 5: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 6: dequeue");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 7: enqueue 40 (goes straight into inStack again)");
        queue.enqueue(40);
        queue.printState();

        System.out.println("Step 8: dequeue (outStack has 30 left, so no pour needed yet)");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Step 9: dequeue (outStack is now empty, so it pours inStack in first)");
        System.out.println("  Removed: " + queue.dequeue());
        queue.printState();

        System.out.println("Final check - is empty now? " + queue.isEmpty());
    }
}