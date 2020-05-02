package bearmaps.proj2ab;

/** @Author Atlantic (Y)
 *  @Description The class ArrayHeapMinPQ is a priority queue
 *  based on the array heap.
 *  The priority is a given number. */

import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    /** HpNode: The Node of the heap. */
    private class HpNode<T> implements Comparable<HpNode<T>>, Comparator<HpNode<T>> {
        private T item;
        private double priority;

        /** Initialize a hpNode. */
        public HpNode(T itemVal, double prioVal) {
            this.item = itemVal;
            this.priority = prioVal;
        }

        public T getItem() {
            return this.item;
        }

        public double getPriority() {
            return this.priority;
        }

        public void setPriority(double priorityNew) {
            this.priority = priorityNew;
        }

        /** Compare the current HpNode to the nodeIn.
         * @return  a negative integer, zero, or a positive integer as this object
         *          is less than, equal to, or greater than the specified object.*/
        @Override
        public int compareTo(HpNode<T> nodeIn) {
            if (nodeIn == null) {
                throw new NullPointerException("Null input.");
            }
            if (nodeIn.getClass() != this.getClass()) {
                throw new ClassCastException("Cannot compare to nodeIn.");
            }
            return (int) (nodeIn.getPriority() - this.getPriority());
        }

        /** Compare two items nodeIn1 and nodeIn2.
         * * @return a negative integer, zero, or a positive integer as the
         *         first argument is less than, equal to, or greater than the
         *         second.*/
        @Override
        public int compare(HpNode<T> nodeIn1, HpNode<T> nodeIn2) {
            if ((nodeIn1 == null) || (nodeIn2 == null)) {
                throw new NullPointerException("Null input.");
            }
            if (nodeIn1.getClass() != nodeIn2.getClass()) {
                throw new ClassCastException("The inputs do not consist in class type.");
            }
            return (int) (nodeIn1.getPriority() - nodeIn2.getPriority());
        }

        /**
         *  @return  {@code true} only if the specified object is also
         *          a comparator and it imposes the same ordering as this
         *          comparator.*/
        @Override
        public boolean equals(Object obj) {
            if ((obj == null) || (obj.getClass() != this.getClass())) {
                return false;
            }
            return this.getItem().equals(((HpNode<T>) obj).getItem());
        }

    }

    private final static int DEFAULT_SIZE = 10;
    private final static int RESIZE_FACTOR = 2;
    private final static double RESIZE_UP_THRESHOLD = 0.75;
    private final static double RESIZE_DOWN_THRESHOLD = 0.3;

    private int size;
    private HpNode[] arrHeap;
    private HashMap<T, Integer> itemsIdx; // store the index of the item in arrHeap

    /** Initialize a default ArrayHeapMinPQ. */
     public ArrayHeapMinPQ() {
         arrHeap = (HpNode<T>[]) new HpNode[DEFAULT_SIZE];
         itemsIdx = new HashMap<>(DEFAULT_SIZE);
         size = 0;
     }

    /** Initialize an ArrayHeapMinPQ.
     * @param initialSize the initial size of the ArrayHeapMinPQ */
    public ArrayHeapMinPQ(int initialSize) {
        if (initialSize <= 0) {
            throw new IllegalArgumentException("Invalid initial size.");
        } else if (initialSize < DEFAULT_SIZE) {
            initialSize = DEFAULT_SIZE;
        }
        arrHeap = new HpNode[initialSize];
        itemsIdx = new HashMap<>(initialSize);
        size = 0;
    }

     /** Adds an item with the given priority value.
      * The first items begin at index 1.
      * @param item the input key
      * @param priority given priority */
    @Override
    public void add(T item, double priority) {
        if(item == null) {
            throw new IllegalArgumentException("The item is null.");
        }
        if (itemsIdx.containsKey(item)) {
            throw new IllegalArgumentException("The item already exist.");
        }

        // put the new node at the end of queue temporally
        arrHeap[size + 1] = new HpNode(item, priority);
        itemsIdx.put(item, size + 1);
        swim(size + 1); //up, re-organize the queue
        size++;

        if ((size + 1) > arrHeap.length * RESIZE_UP_THRESHOLD) {
            resize((size + 1)* RESIZE_FACTOR);
        }

    }

    /** Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemsIdx.containsKey(item);
    }

    /** Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty Heap.");
        }
        return (T) arrHeap[1].getItem();
    }

    /** Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty Heap.");
        }
        T retKey = this.getSmallest();
        itemsIdx.remove(retKey);

        swap(1, size);
        arrHeap[size] = null;
        size--;
        sink(1);

        if ((size + 1 > DEFAULT_SIZE)
             && (size + 1 < arrHeap.length * RESIZE_DOWN_THRESHOLD)) {
            resize((size + 1) * RESIZE_FACTOR);
        }
        return retKey;
    }

    /** Returns the number of items in the PQ. */
    @Override
    public int size() {
        return this.size;
    }

    /** Return whether the pq is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!itemsIdx.containsKey(item)) {
            throw new NoSuchElementException("No such item.");
        }
        int idx =itemsIdx.get(item);
        arrHeap[idx].setPriority(priority);
        swim(idx);
        sink(idx);
    }

    /** Print out the heap in a simple form.
     * @source Hug CS61B*/
    public void printSimpleHeap() {
        PrintHeapDemo.printSimpleHeapDrawing(getHeapItems());
        System.out.println("-----------------------------");
    }

    //============================= Helper methods ===================================

    /** Bottom-up reheapify: use if the heap order is violated because the node's key
     * becomes smaller than that node's parent.
     * @source https://algs4.cs.princeton.edu/24pq/*/
    private void swim(int index) {
        if (index <= 1) { // root
            return;
        }
        if (less(index, parent(index))) {
            swap(index, parent(index));
            swim(parent(index));
        }
    }

    /** Top-down heapify: use if the heap order is violated because the node's key
     * becomes larger than one or both of its two children.
     * @source https://algs4.cs.princeton.edu/24pq/*/
    private void sink(int index) {
        if (leftChildren(index) > size) { // 1. no children
            return;
        }

        int j = leftChildren(index); // 2. one child
        if ((j < size) && greater(leftChildren(index), rightChildren(index))) {
            j = rightChildren(index); // 3. two children: the smaller child
        }

        if (less(index, j)) {
            return; // value at index is smaller than all the children
        }
        swap(index, j);
        sink(j);
    }

    /** Return true if the priority at idx1 is smaller than the priority at idx2.*/
    private boolean less(int idx1, int idx2) {
        return arrHeap[idx1].compareTo(arrHeap[idx2]) < 0;
    }

    /** Return true if the priority at idx1 is greater than the priority at idx2.*/
    private boolean greater(int idx1, int idx2) {
        return arrHeap[idx1].compareTo(arrHeap[idx2]) > 0;
    }

    /** Exchange values at idx1 and idx2. */
    private void swap(int idx1, int idx2) {
        itemsIdx.replace((T) arrHeap[idx1].getItem(), idx2);
        itemsIdx.replace((T) arrHeap[idx2].getItem(), idx1);
        HpNode tmp = arrHeap[idx1];
        arrHeap[idx1] = arrHeap[idx2];
        arrHeap[idx2] = tmp;
    }

    /** Resize (expand or shrink) the arrHeap. */
    private void resize(int sizeNew) {
        HpNode[] arrHeapNew = new HpNode[sizeNew + 1];
        for (int i = 1; i <= this.size; i++) {
            arrHeapNew[i] = arrHeap[i];
        }
        arrHeap = arrHeapNew;
    }

    /** Return the parent idx of current node idx. */
    private int parent(int idx) {
        return idx / 2;
    }

    /** Return the leftChildren idx of current node idx. */
    private int leftChildren(int idx) {
        return idx * 2;
    }

    /** Return the rightChildren idx of current node idx. */
    private int rightChildren(int idx) {
        return idx * 2 + 1;
    }

    /** Convert arrHeap to an array with items. */
    private T[] getHeapItems() {
        T[] retArr = (T[]) new Object[size() + 1];
        for (int i = 1; i < size + 1; i++) {
            retArr[i] = (T) arrHeap[i].getItem();
        }
        return retArr;
    }


    /** Get index of a given item. Package-level accessible.*/
    int getIndex(T item) {
        return itemsIdx.get(item);
    }

}
