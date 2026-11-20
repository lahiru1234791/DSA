/**
 * A Stack (LIFO) built using a Linked List instead of an array
 * (compare to UnboundedArrayStack from Submission 1, which used an array).
 *
 * THE TRICK:
 * - The "top" of the stack is simply the HEAD of the linked list.
 * - PUSH: create a new node, point it at the current head, then make it
 *   the new head. O(1) - no shifting, no resizing, ever.
 * - POP: take the value from the head, then move head to head.next. O(1).
 *
 * Because we always work at the head, a linked-list stack never needs
 * to grow or shrink an array - it's naturally "unbounded" since each
 * push just creates one more node wherever there's free memory.
 */
public class LinkedListStack {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node top; // the node currently at the top of the stack
    private int size;

    public LinkedListStack() {
        top = null;
        size = 0;
    }

    // Add a number to the top of the stack. Always O(1).
    public void push(int value) {
        Node newNode = new Node(value);
        newNode.next = top; // new node points down at the old top
        top = newNode;       // new node becomes the top
        size++;
    }

    // Remove and return the top number. Always O(1).
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot pop");
        }
        int value = top.value;
        top = top.next; // move top down to the next node
        size--;
        return value;
    }

    // Look at the top number without removing it.
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot peek");
        }
        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    // Shows the stack from top to bottom, e.g. "[30, 20, 10]" means 30 is on top.
    public String printState() {
        StringBuilder sb = new StringBuilder("[");
        Node current = top;
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
        LinkedListStack stack = new LinkedListStack();

        System.out.println("Step 1: push 10");
        stack.push(10);
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 2: push 20");
        stack.push(20);
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 3: push 30");
        stack.push(30);
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 4: peek");
        System.out.println("  Top is: " + stack.peek());

        System.out.println("Step 5: pop");
        System.out.println("  Removed: " + stack.pop());
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 6: push 40");
        stack.push(40);
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 7: pop");
        System.out.println("  Removed: " + stack.pop());
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 8: pop");
        System.out.println("  Removed: " + stack.pop());
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Step 9: pop");
        System.out.println("  Removed: " + stack.pop());
        System.out.println("  Stack (top -> bottom): " + stack.printState());

        System.out.println("Is empty now? " + stack.isEmpty());
    }
}