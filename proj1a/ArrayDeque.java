/** ArrayDeque: Double ended list based on the array.
    @author Yun G*/
public class ArrayDeque<T> {

    private int size;
    private int STARTPOS; // the starting position (index) of the first element
    private double FACTOR;
    private T[] items;
    private int nextFirst, nextLast; // the index that next to the first and last item

    /** Create an empty double ended Array List.*/
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = STARTPOS - 1;
        nextLast = nextFirst + 1;
        size = 0;
        FACTOR = 0.25;
    }

    /** Creates a deep copy of ArrayDeque.*/
    public ArrayDeque(ArrayDeque other) {
        T[] a = (T[]) new Object[items.length];
        System.arraycopy(items, nextFirst+1, a, nextFirst+1, size);
    }

    /** Resize the underlying array to the target capacity.
     * After resizing, the nextFirst param always set to
     * the position next to the constant STARTPOS.*/
    private void resize(int capacity) {
        if(capacity < size) {
            capacity = size;
        }

        T[] a = (T[]) new Object[(int) (capacity / FACTOR)];
        System.arraycopy(items, nextFirst + 1, a, STARTPOS, size);
        items = a;
        nextFirst = STARTPOS - 1;
        nextLast = STARTPOS + size;
    }

    /** Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        if ((items.length == size) || (nextFirst == 0)) {
            resize(size + 1);
        }
        items[nextFirst] = item;
        nextFirst = nextFirst - 1;
        size ++;
    }

    /** Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        if ((items.length == size) || (nextLast == (items.length - 1))) {
            resize(size + 1);
        }
        items[nextLast] = item;
        nextLast ++;
        size ++;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        if (size == 0){
            return true;
        }
        return false;
    }

    /**  Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
        Once all the items have been printed, print out a new line.*/
    public void printDeque() {
        if (size != 0) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i].toString() + ' ');
            }
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T firstItem = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst += 1;
        size -= 1;
        return firstItem;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        T lastItem = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast -= 1;
        size -= 1;
        return lastItem;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        if ((index <= nextFirst) || (index >= nextLast)) {
            return null;
        }
        return items[index];
    }


}
