/**
 * A Circular Doubly Linked List of int values.
 *
 * This builds on DoublyLinkedList from Submission 2. The difference is in
 * the name: "Circular" means the chain never ends in null - instead, the
 * last node connects back to the first node, and the first node connects
 * back to the last node, forming a loop.
 *
 *        +-------------------------------------+
 *        v                                      |
 * head->[10|prev|next]<->[20|prev|next]<->[30|prev|next]
 *        ^                                      |
 *        +--------------------------------------+
 *        (30's "next" points back to 10, and 10's "prev" points to 30)
 *
 * This is useful when you want to go "around and around" a list
 * endlessly (like a round-robin turn order in a game), since there's
 * no null to stop you.
 */
public class CircularDoublyLinkedList {

    private static class Node {
        int value;
        Node prev;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head; // the node we treat as the "start" of the circle
    private int size;

    public CircularDoublyLinkedList() {
        head = null;
        size = 0;
    }

    // Add a number to the end of the circle (just before it loops back to head).
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            // First node in the list: it points to itself in both directions
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            Node last = head.prev; // because head.prev is always "the last node"
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
    }

    // Add a number to the front of the circle (the new node becomes head).
    public void insertAtBeginning(int value) {
        insertAtEnd(value);   // first, just add it at the end like normal
        head = head.prev;     // then shift head back to point at that new node
    }

    // Remove the first node that has this value. Returns true if removed.
    public boolean deleteValue(int value) {
        if (head == null) {
            return false;
        }

        Node current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == value) {
                if (size == 1) {
                    head = null; // list becomes empty
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    if (current == head) {
                        head = current.next; // we deleted the head, move it forward
                    }
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false; // value not found anywhere
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // Walks the circle exactly once (using size as the stop condition,
    // since there's no null to naturally stop at) and prints the values.
    public String printState() {
        if (head == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        for (int i = 0; i < size; i++) {
            sb.append(current.value);
            if (i < size - 1) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircularDoublyLinkedList list = new CircularDoublyLinkedList();

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

        System.out.println("Step 5: deleteValue(20) (a middle node)");
        list.deleteValue(20);
        System.out.println("  List: " + list.printState());

        System.out.println("Step 6: deleteValue(5) (deletes the head)");
        list.deleteValue(5);
        System.out.println("  List: " + list.printState());

        System.out.println("Proof it's really circular - walking 6 steps from head");
        System.out.print("  Path: ");
        // Walk forward more steps than there are nodes, to show it loops around
        Node walker = list.head;
        for (int i = 0; i < 6; i++) {
            System.out.print(walker.value + " ");
            walker = walker.next;
        }
        System.out.println();

        System.out.println("Current size: " + list.size());
        System.out.println("Is empty? " + list.isEmpty());
    }
}
