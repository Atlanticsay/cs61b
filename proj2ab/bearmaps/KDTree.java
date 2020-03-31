package bearmaps;

import java.util.List;
import java.util.Collections;

/** @author Atlantic (Y)
 */

public class KDTree<V> implements PointSet{

    private class Node {
        private Point point;
        private V val; // associate data at current point
        private Node left, right;
        private boolean spaceInd; // true if left/right, false if up/sdown

        public Node(Point p, V value, boolean spaceIndex) {
            point = p;
            val = value;
            spaceInd = spaceIndex;
        }

        /** Compare the current node to a given point.
         *  @param   p the point to be compared.
         *  @return  a negative integer, zero, or a positive integer as this node
         *          is less than, equal to, or greater than the specified node.
         */
        public double compareTo(Point p) {
            if (p == null) {
                throw new NullPointerException("The point to be compared is null.");
            }
            if (spaceInd) { // left/right, compare x
                return this.point.getX() - p.getX();
            }
            return this.point.getY() - p.getY(); // up/down, compare y
        }

        /** Compute the distance between the current node and a given point. */
        public double distance(Point p) {
            return Math.sqrt(Point.distance(this.point, p));
        }

        /** Compute the x/y distance between the current node and a given point. */
        public double oneDDistance(Point p) {
            if (spaceInd) {
                return Math.abs(point.getX() - p.getX());
            }
            return Math.abs(point.getY() - p.getY());
        }

    }

    private int size;
    private Node root;

    /** Create an empty KDTree. */
    public KDTree() {
        size = 0;
    }

    /** Create a KDTree using a list of points. */
    public KDTree(List<Point> pointList) {
        size = 0;
        Collections.shuffle(pointList);
        for (Point p : pointList) {
            insert(p, null);
        }
    }

    /** Get the number of nodes in the KDTree. */
    public int size() {
        return size;
    }

    /** Insert point in the KDTree.
     * @param inPoint the point to be inserted
     */
    public void insert(Point inPoint, V value) {
        if (inPoint == null) {
            throw new IllegalArgumentException("The given point is null.");
        }
        if (size == 0) {
            root = new Node(inPoint, value, true);
            size += 1;
            return;
        }
        insertHelper(inPoint, value, root, root.spaceInd);
    }

    private Node insertHelper(Point inPoint, V value, Node curNode, boolean spaceIndicator) {
        if (curNode == null) {
            size++;
            return new Node(inPoint, value, spaceIndicator);
        }
        double cmp = curNode.compareTo(inPoint);
        if (cmp > 0) {
            curNode.left = insertHelper(inPoint, value, curNode.left, !curNode.spaceInd);
        } else if (cmp < 0) {
            curNode.right = insertHelper(inPoint, value, curNode.right, !curNode.spaceInd);
        } else {
            curNode.val = value;
        }
        return curNode;
    }

    /** Returns the closest point to the inputted coordinates.
     *  This takes θ(logN) time where N is the number of points.
     * @param x coordinate
     * @param y coordinate
     * @return the nearest point
     */
    @Override
    public Point nearest(double x, double y) {
        Node best = nearestHelper(root, new Point(x, y), root);
        return best.point;
    }

    private Node nearestHelper(Node nodeCur, Point goalPoint, Node nodeBest) {
        if (nodeCur == null) {
            return nodeBest;
        }
        if (nodeCur.distance(goalPoint) < nodeBest.distance(goalPoint)) {
            nodeBest = nodeCur;
        }
        // always search for the good side first
        double cmp = nodeCur.compareTo(goalPoint);
        Node goodSide, badSide;
        if (cmp > 0) {
            goodSide = nodeCur.left;
            badSide = nodeCur.right;
        } else {
            goodSide = nodeCur.right;
            badSide = nodeCur.left;
        }
        nodeBest = nearestHelper(goodSide, goalPoint, nodeBest);
        // pruning process
        if (nodeCur.oneDDistance(goalPoint) <= nodeBest.distance(goalPoint)) {
            nodeBest = nearestHelper(badSide, goalPoint, nodeBest);
        }
        return nodeBest;
    }

}
