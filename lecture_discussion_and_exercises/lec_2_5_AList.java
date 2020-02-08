/** Array based list.
 *  @author 1 Josh Hug 2 Yun G
 */

 //         0 1 2  3 4 5 6
 // items: [6 9 -1 2 0 0 0 ...]
 // size: 4

 /* Invariants: 
    getLast: The item we want to return is in position size-1.
    addlast: The next item we want to add, will go into posiztion size.
    size: The number of items in the list should be size. */

public class AList<Item> {
    private Item[] items;
    private int size;

    /** Creates an empty list. */
    public AList() {
//         items = new Item[100];
        // cast: To solve the java problem, i.e., the generic arry is not allowed in java
        items = (Item[]) new Object[100];
        size = 0;
    }

    /** Resize the underlying array to the target capacity. */
    private void resize(int capacity) {
        // Item[] a = new Item[capacity];
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        if (size == items.length) {
            resize(size + 1);
        }  
        items[size] = x;
        size = size + 1;
    }

    /** Returns the items from the back of the list. */
    public Item getLast() {

        return items[size-1];        
    }
    /** Gets the ith items in the list (0 is the front). */
    public Item get(int i) {
        return items[i];        
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;        
    }

    /** Deletes items from back of the list and
      * returns deleted items. */
    public Item removeLast() {
        Item x = getLast();
        items[size-1] = null;
        size = size - 1;
        return x;
    }

} 