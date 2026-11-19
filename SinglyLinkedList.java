/**
 * A Singly Linked List of int values.
 *
 * Unlike an array, a linked list doesn't store its numbers in one block
 * of memory next to each other. Instead, each number lives inside its
 * own little box called a "Node", and each Node points to the next one.
 * "Singly" means each Node only knows about the node AFTER it (one
 * direction only) - it doesn't know what comes before it.
 *
 * head -> [10|next] -> [20|next] -> [30|next] -> null
 *
 * This is useful because adding/removing from the FRONT is instant,
 * you don't need a fixed size in advance, and you don't need to shift
 * elements around like you would with an array.
 */
public class SinglyLinkedList {

    // A Node is one box in the chain: a value, and a pointer to the next box.
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head; // points to the first node in the list (or null if empty)
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    // Add a number to the very front of the list. Fast: O(1).
    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);
        newNode.next = head; // new node points to the old first node
        head = newNode;      // the new node becomes the first node
        size++;
    }

    // Add a number to the very end of the list. O(n) - has to walk to the end.
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode; // list was empty, so this is now the only node
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next; // walk forward until the last node
            }
            current.next = newNode; // attach the new node after the last one
        }
        size++;
    }

    // Remove the first node that has this value. Returns true if removed.
    public boolean deleteValue(int value) {
        if (head == null) {
            return false; // nothing to delete
        }

        // Special case: the value to delete is in the very first node
        if (head.value == value) {
            head = head.next;
            size--;
            return true;
        }

        // Otherwise walk through, always looking one node AHEAD
        Node current = head;
        while (current.next != null) {
            if (current.next.value == value) {
                current.next = current.next.next; // skip over the matching node
                size--;
                return true;
            }
            current = current.next;
        }
        return false; // value was not found anywhere in the list
    }

    // Check whether a value exists in the list.
    public boolean contains(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // Builds a readable String of the whole list, e.g. "[10, 20, 30]"
    public String printState() {
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

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        System.out.println("Step 1: insertAtEnd 10");
        list.insertAtEnd(10);
        System.out.println("  List: " + list.printState());

        System.out.println("Step 2: insertAtEnd 20");
        list.insertAtEnd(20);
        System.out.println("  List: " + list.printState());

        System.out.println("Step 3: insertAtEnd 30");
        list.insertAtEnd(30);
        System.out.println("  List: " + list.printState());

        System.out.println("Step 4: insertAtBeginning 5");
        list.insertAtBeginning(5);
        System.out.println("  List: " + list.printState());

        System.out.println("Step 5: contains(20)? " + list.contains(20));
        System.out.println("Step 6: contains(99)? " + list.contains(99));

        System.out.println("Step 7: deleteValue(20)");
        list.deleteValue(20);
        System.out.println("  List: " + list.printState());

        System.out.println("Step 8: deleteValue(5) (removes the first node)");
        list.deleteValue(5);
        System.out.println("  List: " + list.printState());

        System.out.println("Current size: " + list.size());
        System.out.println("Is empty? " + list.isEmpty());
    }
}
