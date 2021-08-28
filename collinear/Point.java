/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if ((this.x == that.x) && (this.y == that.y))   // (x0, y0) and (x1, y1) are equal
            return Double.NEGATIVE_INFINITY;
        else if (this.y == that.y)  // line segment connecting the two points is horizontal
            return 0.0;
        else if (this.x == that.x)  // line segment is vertical
            return Double.POSITIVE_INFINITY;
        else
            return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if ((this.x == that.x) && (this.y == that.y))
            return 0;
        else if (this.y < that.y)
            return -1;
        else if ((this.y == that.y) && (this.x < that.x))
            return -1;
        else
            return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double aToCurrentSlope = slopeTo(a);
            double bToCurrentSlope = slopeTo(b);

            if (aToCurrentSlope == bToCurrentSlope)
                return 0;
            else if (aToCurrentSlope < bToCurrentSlope)
                return -1;
            else
                return 1;
        }
    }


    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        StdDraw.setPenRadius(0.010);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setScale(0.0, 10.0);

        Point p1 = new Point(5, 2);
        Point p2 = new Point(3, 5);
        Point p3 = new Point(5, 2);
        Point p4 = new Point(6, 2);
        Point p5 = new Point(5, 1);
        Point p6 = new Point(6, 1);
        Point p7 = new Point(4, 1);
        Point p8 = new Point(6, 4);

        /* Testing drawing methods */
        p1.draw(); // draw p1
        p1.drawTo(p2); // draw the line from p1 to p2

        /* Testing compareTo() method */
        assert p1.compareTo(p3) == 0;   // testing same point
        assert p1.compareTo(p2) < 0;    // testing p1 smaller than p2 by y-coordinates
        assert p1.compareTo(p4)
                < 0;    // testing p1 smaller than p4 by tie-breaking using x-coordinates
        assert p1.compareTo(p5) > 0; // testing p1 greater than p5 using y-coordinates
        assert p1.compareTo(p6) > 0;
        assert p1.compareTo(p7) > 0;

        /* Testing slopeTo() method */
        assert p1.slopeTo(p3) == Double.NEGATIVE_INFINITY;  // testing same point
        assert p1.slopeTo(p4) == +0.0;  // testing same horizontal line segment
        assert p1.slopeTo(p5) == Double.POSITIVE_INFINITY; // testing vertical line segment
        assert p3.slopeTo(p6) < 0;  // test downwards slope
        assert p3.slopeTo(p8) > 0;  // test upwards slope

        /* Testing slopeOrder() method */
        Point[] pointsArray = new Point[5];
        pointsArray[0] = p6;
        pointsArray[1] = p2;
        pointsArray[2] = p3;
        pointsArray[3] = p4;
        pointsArray[4] = p5;

        Arrays.sort(pointsArray, p1.slopeOrder());

        for (Point p : pointsArray) {
            StdOut.println(p);
        }


    }
}
