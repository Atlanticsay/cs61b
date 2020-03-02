package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Atlantic
 */

public class TestArrayRingBuffer {

    @Test
    public void basicTests() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertFalse(arb.isFull());
        assertEquals(1, arb.dequeue());
        assertEquals(2, arb.peek());
        arb.enqueue(6);
        arb.enqueue(4);
        assertTrue(arb.isFull());
    }

    @Test
    public void iteratorTests() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);

        Iterator<Integer> arbIterator = arb.iterator();
        int actual = arbIterator.next();
        assertEquals(1, actual);

        arbIterator.next();
        assertTrue(arbIterator.hasNext());
        arbIterator.next();
        assertFalse(arbIterator.hasNext());

        ArrayRingBuffer arb2 = new ArrayRingBuffer(5);
        arb2.enqueue(1);
        for (Object element : arb2) {
            element = (int) element;
            assertEquals(1, element);
        }

    }


    @Test
    public void equalTests() {

        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);

        ArrayRingBuffer arb2 = new ArrayRingBuffer(5);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);
        arb2.enqueue(4);

        ArrayRingBuffer arb3 = new ArrayRingBuffer(5);
        arb3.enqueue(1);
        arb3.enqueue(8);
        arb3.enqueue(3);
        arb3.enqueue(6);

        assertTrue(arb.equals(arb2));
        assertFalse(arb.equals(arb3));


    }

//    /** Calls tests for ArrayRingBuffer. */
//    public static void main(String[] args) {
//        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
//    }

}
