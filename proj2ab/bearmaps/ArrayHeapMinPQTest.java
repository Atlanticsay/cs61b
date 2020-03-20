package bearmaps;

/** Author: Atlantic */

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class ArrayHeapMinPQTest<T>{

    @Test
    public void arrHpTestBasicModules() {
        ArrayHeapMinPQ<Integer> arrHp = new ArrayHeapMinPQ<>();
        arrHp.add(5, 3);
        arrHp.add(6,2);
        arrHp.add(7,1);
        assertEquals(3, arrHp.size());
        arrHp.printSimpleHeap();

        arrHp.changePriority(7, 4);
        arrHp.printSimpleHeap();
        assertEquals((Integer) 7, arrHp.getSmallest());
        assertEquals((Integer) 7, arrHp.removeSmallest());
        assertFalse(arrHp.contains(7));
        assertTrue(arrHp.contains(0));
        arrHp.printSimpleHeap();
    }

    @Test
    public void arrHpTestEdgeCases() {
        ArrayHeapMinPQ<Integer> arrHp = new ArrayHeapMinPQ<>();
        arrHp.add(3, 10);
        arrHp.add(4, 8);
        arrHp.add(5, 3);
        arrHp.add(6,2);
        arrHp.add(7,1);
        arrHp.printSimpleHeap();

        arrHp.changePriority(7, 9); // swim up
        arrHp.printSimpleHeap();
        assertEquals(2, arrHp.getIndex(7));
        arrHp.changePriority(7, 11); // swim up
        arrHp.printSimpleHeap();
        assertEquals(1, arrHp.getIndex(7));
        arrHp.changePriority(7, 0); // swim up
        arrHp.printSimpleHeap();
        assertEquals(5, arrHp.getIndex(7)); // sink
        arrHp.printSimpleHeap();
    }

    /** @source TimingTestDemo.java */
    public static void main(String[] args) {

        // compare add() methods between NaiveMinPQ and ArrayHeapMinPQ
        Random rd1 = new Random(10);
        long start0 = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> arrHp = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            arrHp.add(i, rd1.nextInt());
        }
        long end0 = System.currentTimeMillis();
        System.out.println("ArrayHeapMinPQ add() time elapsed: " + (end0 - start0)/1000.0 +  " seconds.");

        Random rd2 = new Random(10);
        long start = System.currentTimeMillis();
        NaiveMinPQ<Integer> nvHp = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            nvHp.add(i, rd2.nextInt());

        }
        long end = System.currentTimeMillis();
        System.out.println("NaiveMinPQ add() time elapsed: " + (end - start)/1000.0 +  " seconds.\n");

        // compare changePriority() methods between NaiveMinPQ and ArrayHeapMinPQ
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            arrHp.changePriority(i, rd1.nextInt());

        }
        System.out.println("ArrayHeapMinPQ changePriority() time elapsed: " + sw.elapsedTime() +  " seconds.");
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            nvHp.changePriority(i, rd2.nextInt());

        }
        System.out.println("NaiveMinPQ changePriority() time elapsed: " + sw2.elapsedTime() +  " seconds.");

    }


}
