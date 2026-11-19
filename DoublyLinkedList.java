/**
 * A Doubly Linked List of int values.
 *
 * Just like SinglyLinkedList, but each Node now points BOTH ways: to the
 * next node AND to the previous node. "Doubly" = two directions.
 *
 * null <- [10|prev|next] <-> [20|prev|next] <-> [30|prev|next] -> null
 *          ^ head                                ^ tail
 *
 * This means we can walk the list backwards as well as forwards, and we
 * can delete a node quickly if we already have a reference to it
 * (no need to walk from the head to find "the node before it", because
 * every node already knows its own previous node).
 */
public class DoublyLinkedList {

    // A Node is one box in the chain: a value, plus pointers to both
    // the previous box and the next box.
    private static class Node {
        int value;
        Node prev;
        Node next;

        Node(int value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head; // first node (or null if empty)
    private Node tail; // last node (or null if empty)
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Add a number to the front of the list. O(1).
    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            // list was empty: this single node is both head and tail
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Add a number to the end of the list. O(1) because we keep a tail pointer
    // (no need to walk the whole list like in the singly linked version).
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);
        if (tail == null) {
            // list was empty
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Remove the first node that has this value. Returns true if removed.
    public boolean deleteValue(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                // Reconnect the neighbours to skip over "current"

                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next; // we were deleting the head
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev; // we were deleting the tail
                }

                size--;
                return true;
            }
            current = current.next;
        }
        return false; // value not found
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // Builds a readable String walking forward from head, e.g. "[10, 20, 30]"
    public String printForward() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
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

    // Builds a readable String walking backward from tail, e.g. "[30, 20, 10]"
    public String printBackward() {
        StringBuilder sb = new StringBuilder("[");
        Node current = tail;
        while (current != null) {
            sb.append(current.value);
            if (current.prev != null) {
                sb.append(", ");
            }
            current = current.prev;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();

        System.out.println("Step 1: insertAtEnd 10");
        list.insertAtEnd(10);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Step 2: insertAtEnd 20");
        list.insertAtEnd(20);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Step 3: insertAtEnd 30");
        list.insertAtEnd(30);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Step 4: insertAtBeginning 5");
        list.insertAtBeginning(5);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Step 5: deleteValue(20) (a middle node)");
        list.deleteValue(20);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Step 6: deleteValue(5) (deletes the head)");
        list.deleteValue(5);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Step 7: deleteValue(30) (deletes the tail)");
        list.deleteValue(30);
        System.out.println("  Forward:  " + list.printForward());
        System.out.println("  Backward: " + list.printBackward());

        System.out.println("Current size: " + list.size());
        System.out.println("Is empty? " + list.isEmpty());
    }
}
