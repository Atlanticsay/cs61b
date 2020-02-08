/** Create a double linked list inputting generic types and 
 * using circular sentinel topology.
 * @author Atlantic.
 */

 public class LinkedListDeque<T> {
     
    /** A basic nested class for the list. */
    public class Node {
        public Node prev;
        public Node next; 
        public T item;

        public Node(T i, Node p, Node n) {
            prev = p;
            next = n;
            item = i;
        }
    }

    private int size;
    private Node sentinel;

    /** Create an empty double-linked list. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null);
    }
    
    /** Create a list that only has one Node (except for the sentinel).*/
    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(item, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

    /** Return of deepcopy of a new list. */
    public LinkedListDeque(LinkedListDeque other) {  
        size = other.size();      

    }


    /** Returns the number of items in the deque.*/
    public int size() {
        return this.size;
    }


    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }


    /** Prints the items in the deque from first to last, separated by a space. 
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        Node p = sentinel.next;
        if (p == null) {
            System.out.println();
            return;
        }
        while (p.next != sentinel) {
            System.out.print(p.item.toString() + ' ');
            p = p.next;
        }
        System.out.print(p.item.toString() + ' ');
        System.out.println();
    }


    /** Add a node to the front of the list. */
    public void addFirst(T item) {
        Node pFirst  = sentinel.next;
        sentinel.next = new Node(item, sentinel, pFirst);
        size += 1;
    }

    /** Add a node to the end of the list. */
    public void addLast(T item) {
        Node pLast = sentinel.prev;
        sentinel.prev = new Node(item, pLast, sentinel);
        size += 1;
    }


    /** Removes and returns the item at the front of the deque. 
     * If no such item exists, returns null.  */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node pFirst = sentinel.next;
        Node pSecond = pFirst.next;
        sentinel.next = pFirst.next;
        pSecond.prev = sentinel;
        size -= 1;
        return pFirst.item;
    }


    /** Removes and returns the item at the back of the deque. 
     * If no such item exists, returns null.  */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node pLast = sentinel.next;
        Node pScdLast = pLast.prev; // pointer: the second last node
        sentinel.next = pScdLast;
        pScdLast.next = sentinel;
        size -= 1;
        return pLast.item;
    }    


    /** Gets the item at the given index, 
     * where 0 is the front, 1 is the next item, and so forth. 
     * If no such item exists, returns null. Must not alter the deque!
    */
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        Node p = sentinel.next;
        int i = 0;
        while (i < index) {
            p = p.next;
            i++;
        }
        return p.item;
    }

    
    /** Return the ith element of the list recursively.
     * @param index: ith, started from
     */
    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        } 
//!!!
        Node pst = sentinel.next;
        Node p = pst;      
        if (index == 0) {
            return p.item;
        }
        p = p.next;
        return(index-1);
    }







 }