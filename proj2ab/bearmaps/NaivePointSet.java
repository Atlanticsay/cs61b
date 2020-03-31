package bearmaps;
import java.util.LinkedList;
import java.util.List;

/** @author Atlantic (Y)
 *  @description A simple PointSet serving as sanity check for the KDTreeSet. */

public class NaivePointSet implements PointSet {

    private int size;
    private List<Point> pointSet;

    public NaivePointSet(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("No given points exist.");
        }
        size = points.size();
        pointSet = points;
    }

    /** Returns the closest point to the inputted coordinates.
     *  This takes θ(N) time where N is the number of points.
     * @param x coordinate
     * @param y coordinate
     * @return the nearest point
     */
    public Point nearest(double x, double y) {
        Point focusP = new Point(x, y);
        double dis = Point.distance(pointSet.get(0), focusP);
        Point nearestP = pointSet.get(0);

        for (Point p : pointSet) {
            double curDis = Point.distance(p, focusP);
            if (dis > curDis) {
                nearestP = p;
                dis = curDis;
            }
        }
        return nearestP;
    }

}
