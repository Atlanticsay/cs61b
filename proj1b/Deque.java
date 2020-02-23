public interface Deque<T> {

    /** Returns the number of items in the deque.*/
    int size();

    /** Returns true if deque is empty, false otherwise. */
    default boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return false;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque();

    /** Add a node to the front of the list. */
    public void addFirst(T item);

    /** Add a node to the end of the list. */
    public void addLast(T item);

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.  */
    public T removeFirst();

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.  */
    public T removeLast();

    /** Gets the item at the given index,
     * where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index);

    /** Return the ith element of the list recursively.
     * @param index: ith, started from 0
     */
//    public T getRecursive(int index);

}
