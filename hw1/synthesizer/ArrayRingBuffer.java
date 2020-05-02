package synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {

    /* First stores the index of the least recently inserted item. */
    private int first;
    /* Last stores the index one beyond the most recently inserted item. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* Non-empty elments in the array.*/
    private int fillCount;
    /* Capacity of the array. */
    int capacity;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /** Return size of the buffer */
    public int capacity() {
        return this.capacity;
    }

    /** return number of items currently in the buffer. */
    public int fillCount() {
        return this.fillCount;
    }

    /** return the pointer next to the given one. */
    public int nextPointerInArrayRing(int pos) {
        if (pos == (capacity - 1)) {
            pos = 0;
            return pos;
        }
        pos += 1;
        return pos;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = nextPointerInArrayRing(last);
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T oldFirst = rb[first];
        rb[first] = null;
        first = nextPointerInArrayRing(first);
        fillCount--;
        return oldFirst;
    }

    /**
     * Return oldest item, but don't remove it.
     * Return (but do not delete) item from the front
     */
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer<T> obj = (ArrayRingBuffer<T>) o;
        int p = first;
        for (T t : obj) {
            t = (T) t;
            if (t != rb[p]) {
                return false;
            }
            p = nextPointerInArrayRing(p);
        }
        return true;
    }

    /** returns an iterator */
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }

    private class ArrayRingIterator implements Iterator<T> {
        private int wizPos;
        public ArrayRingIterator() {
            wizPos = first;
        }

        /*  Returns true if the iteration has more elements. */
        public boolean hasNext() {
            if ((nextPointerInArrayRing(wizPos) == last) || (rb[wizPos] == null)) {
                return false;
            }
            return true;
        }

        /* Mover the pointer wizPos to the next.
         * @returns the next value in the iteration
         * @throws NoSuchElementException if the iteration has no more elements */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T returnItem = rb[wizPos];
            wizPos = nextPointerInArrayRing(wizPos);
            return returnItem;
        }
    }

}
