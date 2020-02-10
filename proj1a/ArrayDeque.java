/** ArrayDeque: Double ended list based on the array.
    @author Yun G*/
public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst, nextLast; // the index that next to the first and last item
    private static final int STARTPOS = 3; // the starting position (index) of the first element
    private static final double RESZ_FACTOR = 2;
    private static final double MEMO_USE_RATIO = 0.35;


    /** Create an empty double ended Array List.*/
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = STARTPOS - 1;
        nextLast = nextFirst + 1;
    }


    /**Make the index flows in a circular way.
     * Return the next index (+). */
    private int nextIndex(int curIndex) {
        int assumedNext = curIndex + 1;
        if (assumedNext > items.length - 1) {
            return 0;
        }
        return assumedNext;
    }


    /**Make the index flows in a circular way.
     * Return the index before current one (-). */
    private int beforeIndex(int curIndex) {
        int assumedBefore = curIndex - 1;
        if (assumedBefore < 0) {
            return items.length - 1;
        }
        return assumedBefore;
    }


    /** Resize the underlying array to the target capacity.
     * After resizing, the nextFirst param always set to
     * the position next to the constant STARTPOS.*/
    private void resize(int capacity) {
        if (capacity < size) {
            capacity = size + 1;
        }
        T[] a = (T[]) new Object[(int) (capacity * RESZ_FACTOR)];

        // copy the circular array
        int p = nextIndex(nextFirst);
        for (int i = 0; i < size; i++) {
            a[i] = items[p];
            p = nextIndex(p);
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }


    /** Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        if (items.length == size) {
            resize(size + 1);
        }
        items[nextFirst] = item;
        nextFirst = beforeIndex(nextFirst);
        size++;
    }


    /** Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        if (items.length == size) {
            resize(size + 1);
        }
        items[nextLast] = item;
        nextLast = nextIndex(nextLast);
        size++;
    }


    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        if (size == 0) {
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
            int p = nextIndex(nextFirst);
            for (int i = 0; i < size; i++) {
                System.out.print(items[p].toString() + ' ');
                p = nextIndex(p);
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

        int pFirstOld = nextIndex(nextFirst);
        T firstItemOld = items[pFirstOld];
        items[pFirstOld] = null;
        nextFirst = pFirstOld;
        size--;

        // downsizing
        if ((items.length > 16) && (size / items.length) <= MEMO_USE_RATIO) {
            resize(size);
        }
        return firstItemOld;
    }


    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int pLastOld = beforeIndex(nextLast);
        T lastItemOld = items[pLastOld];
        items[pLastOld] = null;
        nextLast = pLastOld;
        size--;

        // downsizing
        if ((items.length > 16) && (size / items.length) <= MEMO_USE_RATIO) {
            resize(size);
        }
        return lastItemOld;
    }


    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        int p = nextIndex(nextFirst);
        for (int i = 0; i < index; i++) {
            p = nextIndex(p);
        }
        return items[p];
    }

    //    /** Creates a deep copy of ArrayDeque.*/
//    public ArrayDeque(ArrayDeque other) {
//        T[] a = (T[]) new Object[items.length];
//        System.arraycopy(items, nextFirst + 1, a, nextFirst + 1, size);
//    }

}


