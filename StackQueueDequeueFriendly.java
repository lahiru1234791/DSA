import java.util.LinkedList;
import java.util.Queue;

/**
 * A Stack (LIFO) built using two Queues (FIFO), but this version is
 * "dequeue friendly": removing a number (pop) is ALWAYS fast. Adding a
 * number (push) is the one that has to do extra work every time.
 *
 * THIS IS THE OPPOSITE TRADE-OFF of StackQueueEnqueueFriendly from
 * Submission 1.
 *
 * THE TRICK:
 * - We keep mainQueue arranged so the most recently pushed number is
 *   always at the very FRONT of the queue (ready to come out instantly).
 * - PUSH: put the new number into an empty helperQueue first, then pour
 *   every number from mainQueue in behind it, then swap the two queues
 *   around. This puts the new number at the front, pushing everyone
 *   else one step back - which is exactly where they need to be.
 * - POP: just take from the front of mainQueue. Always fast.
 */
public class StackQueueDequeueFriendly {

    private Queue<Integer> mainQueue;
    private Queue<Integer> helperQueue;

    public StackQueueDequeueFriendly() {
        mainQueue = new LinkedList<Integer>();
        helperQueue = new LinkedList<Integer>();
    }

    // Add a number to the top of the stack. Does extra work every time.
    public void push(int value) {
        // Step 1: the new value goes into the empty helper queue first
        helperQueue.offer(value);

        // Step 2: everyone already in mainQueue goes in behind it
        while (!mainQueue.isEmpty()) {
            helperQueue.offer(mainQueue.poll());
        }

        // Step 3: swap the queues, so mainQueue is the one with the
        // correct order from now on
        Queue<Integer> swap = mainQueue;
        mainQueue = helperQueue;
        helperQueue = swap;
    }

    // Remove and return the top number of the stack. Always fast.
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot pop");
        }
        return mainQueue.poll();
    }

    // Look at the top number without removing it. Always fast.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot peek");
        }
        return mainQueue.peek();
    }

    public boolean isEmpty() {
        return mainQueue.isEmpty();
    }

    public int size() {
        return mainQueue.size();
    }

    // Shows what's inside mainQueue right now (front = top of stack).
    private void printState() {
        System.out.println("    mainQueue (front = top of stack) : " + mainQueue);
    }

    public static void main(String[] args) {
        StackQueueDequeueFriendly stack = new StackQueueDequeueFriendly();

        System.out.println("Step 1: push 10 (goes into helper, swapped in)");
        stack.push(10);
        stack.printState();

        System.out.println("Step 2: push 20 (20 goes in front, 10 follows behind it)");
        stack.push(20);
        stack.printState();

        System.out.println("Step 3: push 30 (30 goes in front, 20 and 10 follow)");
        stack.push(30);
        stack.printState();

        System.out.println("Step 4: peek - instant, just look at the front");
        System.out.println("  Top is: " + stack.peek());
        stack.printState();

        System.out.println("Step 5: pop - instant, just take the front");
        System.out.println("  Removed: " + stack.pop());
        stack.printState();

        System.out.println("Step 6: push 40 (does the full shuffle again)");
        stack.push(40);
        stack.printState();

        System.out.println("Step 7: pop");
        System.out.println("  Removed: " + stack.pop());
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