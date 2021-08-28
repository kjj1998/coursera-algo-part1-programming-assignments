/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 19/8/2021
 *  Description: Implementation of a mutable data type PointSET.java that
 *               represents a set of points in the unit square.
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {

    private SET<Point2D> s; // private SET<Point2D> instance variable

    /**
     * Constructs an empty set of points
     */
    public PointSET() {
        this.s = new SET<>();
    }

    /**
     * Check if the set is empty
     *
     * @return the boolean value
     */
    public boolean isEmpty() {
        return s.isEmpty();
    }

    /**
     * Return the size of the set
     *
     * @return the number of points in the set
     */
    public int size() {
        return s.size();
    }


    /**
     * Insert the point into the set (if it is not already in the set)
     *
     * @param p the point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");

        if (!s.contains(p)) {
            s.add(p);
        }
    }

    /**
     * Check if the set contains the point
     *
     * @param p the point to be checked
     * @return boolean value
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");

        return s.contains(p);
    }

    /**
     * Draw all points in the set to standard draw
     */
    public void draw() {
        for (Point2D a : s) {
            a.draw();
        }
    }

    /**
     * Find all the points that are inside the rectangle (or on the boundary)
     *
     * @param rect the rectangle
     * @return an iterable containing all the points in the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");

        Queue<Point2D> iter = new Queue<>();

        for (Point2D a : s) {
            if (rect.contains(a)) {
                iter.enqueue(a);
            }
        }

        return iter;
    }

    /**
     * Find a nearest neighbour in the set to point p, null if the set is empty
     *
     * @param p the point to find its nearest neighbour
     * @return point which is the nearest neighbour
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");

        Point2D nearestPoint = new Point2D(0.0, 0.0);
        double nearestDist = Double.POSITIVE_INFINITY;

        if (isEmpty())
            return null;

        for (Point2D a : s) {
            if (p.distanceTo(a) < nearestDist) {
                nearestDist = p.distanceTo(a);
                nearestPoint = a;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        /* Code for testing purposes */
        PointSET s = new PointSET();
        StdOut.println("Is the set empty?: " + s.isEmpty());

        Point2D p = new Point2D(0.4, 0.6);
        s.insert(p);
        s.insert(new Point2D(0.35, 0.89));
        s.insert(new Point2D(0.15, 0.78));
        s.insert(new Point2D(0.57, 0.39));
        s.insert(new Point2D(0.67, 0.73));
        s.insert(new Point2D(0.31, 0.61));
        StdOut.println("Does the set contains " + p + " ?: " + s.contains(p));
        StdOut.println("Number of points in the set: " + s.size());

        StdDraw.setPenRadius(0.007);
        s.draw();

        Point2D searchPoint = new Point2D(0.24, 0.83);
        Point2D nearestPoint = s.nearest(searchPoint);
        StdOut.println("Nearest point to " + searchPoint + " is " + nearestPoint);

        RectHV rect = new RectHV(0.25, 0.35, 0.75, 0.75);
        Iterable<Point2D> iter = s.range(rect);
        StdOut.print("All the points inside rectangle " + rect + " : ");
        for (Point2D k : iter) {
            System.out.print(k + " ");
        }
        StdOut.println();
    }
}
