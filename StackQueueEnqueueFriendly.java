import java.util.LinkedList;
import java.util.Queue;

/**
 * A Stack (LIFO) built using two Queues (FIFO = First In, First Out).
 * This version is "enqueue friendly": adding a number (push) is always
 * fast. Removing a number (pop) has to do extra work.
 *
 * THE TRICK:
 * - We keep one main queue that holds all the numbers in the order they
 *   were pushed.
 * - PUSH: just add the new number to the back of the queue. Fast.
 * - POP: the "top of the stack" is actually the LAST number that was
 *   added, which sits at the very back of the queue. To get it, we move
 *   every number except the last one into a helper queue, take out that
 *   last number (that's our answer), then make the helper queue the new
 *   main queue.
 */
public class StackQueueEnqueueFriendly {

    private Queue<Integer> mainQueue;
    private Queue<Integer> helperQueue;

    public StackQueueEnqueueFriendly() {
        mainQueue = new LinkedList<Integer>();
        helperQueue = new LinkedList<Integer>();
    }

    // Add a number to the top of the stack. Always fast.
    public void push(int value) {
        mainQueue.offer(value);
    }

    // Remove and return the top number of the stack.
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot pop");
        }

        // Move everything except the very last number into helperQueue
        while (mainQueue.size() > 1) {
            helperQueue.offer(mainQueue.poll());
        }

        // The one number left in mainQueue is the top of the stack
        int topValue = mainQueue.poll();

        // Swap the queues around so mainQueue is ready for next time
        Queue<Integer> swap = mainQueue;
        mainQueue = helperQueue;
        helperQueue = swap;

        return topValue;
    }

    // Look at the top number without removing it.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot peek");
        }

        while (mainQueue.size() > 1) {
            helperQueue.offer(mainQueue.poll());
        }
        int topValue = mainQueue.peek();
        helperQueue.offer(mainQueue.poll());

        Queue<Integer> swap = mainQueue;
        mainQueue = helperQueue;
        helperQueue = swap;

        return topValue;
    }

    public boolean isEmpty() {
        return mainQueue.isEmpty();
    }

    public int size() {
        return mainQueue.size();
    }

    // Shows what's inside mainQueue right now (oldest push first, newest = top of stack last).
    private void printState() {
        System.out.println("    mainQueue (front...back, back = top of stack) : " + mainQueue);
    }

    public static void main(String[] args) {
        StackQueueEnqueueFriendly stack = new StackQueueEnqueueFriendly();

        System.out.println("Step 1: push 10");
        stack.push(10);
        stack.printState();

        System.out.println("Step 2: push 20");
        stack.push(20);
        stack.printState();

        System.out.println("Step 3: push 30");
        stack.push(30);
        stack.printState();

        System.out.println("Step 4: peek (shuffles everyone else into helperQueue, grabs 30, swaps back)");
        System.out.println("  Top is: " + stack.peek());
        stack.printState();

        System.out.println("Step 5: pop (same shuffle, removes 30)");
        System.out.println("  Removed: " + stack.pop());
        stack.printState();

        System.out.println("Step 6: pop (shuffles 10 aside, removes 20)");
        System.out.println("  Removed: " + stack.pop());
        stack.printState();

        System.out.println("Step 7: push 40");
        stack.push(40);
        stack.printState();

        System.out.println("Step 8: pop");
        System.out.println("  Removed: " + stack.pop());
        stack.printState();

        System.out.println("Step 9: pop");
        System.out.println("  Removed: " + stack.pop());
        stack.printState();

        System.out.println("Final check - is empty now? " + stack.isEmpty());
    }
}