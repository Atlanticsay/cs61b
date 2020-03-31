package bearmaps;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/** @author Atlantic (Y)
 */

public class KDTreeTest {

    private static int SEED = 1000;
    Random randGenerator = new Random(SEED);


    @Test
    public void simpleTestInsert() {
        Point a = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point z = new Point(4, 2);
        Point c = new Point(4, 5);
        Point b = new Point(4, 2);
        Point d = new Point(3, 3);
        Point e = new Point(1, 5);
        Point f = new Point(4, 4);

        KDTree<Character> kdTree = new KDTree<>();
        kdTree.insert(a, 'a');
        kdTree.insert(z, 'z');
        kdTree.insert(b, 'b');
        kdTree.insert(c, 'c');
        kdTree.insert(d, 'd');
        kdTree.insert(e, 'e');
        kdTree.insert(f, 'f');
        assertEquals(5, kdTree.size());
    }

    @Test
    public void randomizedTestNearest() {
        int pointSetNum = 1000;
        int pointSearchNum = 500;
        List<Point> randTargetPoints = createRandomPoints(pointSearchNum);
        List<Point> randPointSet = createRandomPoints(pointSetNum);

        KDTree kdTree = new KDTree(randPointSet);
        NaivePointSet naivePoints = new NaivePointSet(randPointSet);
        for (Point pointCur : randTargetPoints) {
            Point actualP = kdTree.nearest(pointCur.getX(), pointCur.getY());
            Point expectedP = naivePoints.nearest(pointCur.getX(), pointCur.getY());
            assertTrue(expectedP.equals(actualP));
        }
    }

    private List<Point> createRandomPoints(int size) {
        List<Point> pointList = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            pointList.add(randomPoint());
        }
        return pointList;
    }

    private Point randomPoint() {
        double x = randGenerator.nextDouble();
        double y = randGenerator.nextDouble();
        return new Point(x, y);
    }

    /** Compare the run time between the NaivePoints and KDTree. */
    @Test
    public void timeTest() {
        int pointSetNum = 1000000;
        int pointQueryNum = 10000;
        List<Point> queryPoints = createRandomPoints(pointQueryNum);
        List<Point> randPointSet = createRandomPoints(pointSetNum);

        KDTree kdTree = new KDTree(randPointSet);
        NaivePointSet naivePoints = new NaivePointSet(randPointSet);

        long start1 = System.currentTimeMillis();
        for (Point pointCur : queryPoints) {
            kdTree.nearest(pointCur.getX(), pointCur.getY());
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (Point pointCur : queryPoints) {
            naivePoints.nearest(pointCur.getX(), pointCur.getY());
        }
        long end2 = System.currentTimeMillis();

        System.out.println(String.format("KDTree %d queries on %d points: %.2f seconds",
                           pointQueryNum, pointSetNum, (end1 - start1) / 1000.0));
        System.out.println(String.format("Naive %d queries on %d points: %.2f seconds",
                           pointQueryNum, pointSetNum, (end2- start2) / 1000.0));

    }


}
