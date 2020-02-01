/** An SLList is a list of integers, which hides the ter
 * the terrible truth of the nakedness within. */
public class SLList {

    /** A nested class, use static only when the class does not
     * use any instance members of the outer class.
     */
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }
    
    private IntNode sentinel; //The first item (if it exixts) is at sentinel.next */
    private IntNode last;
    private int size;

    public SLList(int x) {
        sentinel = new IntNode(9999, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }
    public SLList() {
        sentinel = new IntNode(9999, null);
        size = 0;
    }

    /** Adds x to the front of the list.  */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }
    /** Returns the first item in the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    // /** Add x to the last of the list. Recursive */
    // private IntNode addLastRecursive(IntNode head, int x) {
         
    //      if(head.next == null)   {
    //          IntNode n = new IntNode(x, null);
    //          head.next = n;
    //          return n;
    //      }
    //      return addLastRecursive(head.next, x);
    // }
    // public addLastRecursive(int x) {
    //     size += 1;
    //     return addLastRecursive(this.first, x);
    // }
    
    /** Add x to the last of the list. For Loop */
    public void addLast(int x) {
        last.next = new IntNode(x, null);
        last = last.next;
        size += 1;
        // IntNode p = sentinel;       
        // while(p.next != null) {
        //     p = p.next; // Advance p to the end of the list.
        // }
        // p.next = new IntNode(x, null);        
        // size += 1;
    }
    
    // /** Return the size of the list. OVERLOAD */
    // private static int size(IntNode p) {
    //     if(p.next == null) {
    //         return 1;
    //     }
    //     return 1+size(p.next);        
    // }
    // public int size() {
    //     return size(first);
    // }

    /** Return the size of the list. Caching */
    public int size() {
        return size;
    }




    public static void main(String[] args) {
        /* Creates a list of one integer, namely 10 */
        SLList L = new SLList(10);
        L.addFirst(10);
        L.addFirst(5);
        L.addLast(13);
        L.addFirst(5);

    }
}