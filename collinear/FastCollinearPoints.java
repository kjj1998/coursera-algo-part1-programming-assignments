/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ****************************************************************************
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {

        checkNull(points);  // check if the array is null

        Point[] copy = points.clone();  // defensive copy for immutability

        Arrays.sort(copy); // sort the array in natural order

        checkDuplicate(copy); // check the array for duplicates

        List<LineSegment> maxLineSegments
                = new LinkedList<>(); // LinkedList to hold the maximal line segments

        for (int i = 0; i < copy.length; i++) {

            Point p = copy[i];

            Point[] pointsBySlope = copy.clone(); // clone a copy of the points array
            Arrays.sort(pointsBySlope,
                        p.slopeOrder()); // sort the copied array by slope order relative to the currently selected point

            int x = 1;
            while (x < copy.length) {

                LinkedList<Point> candidates
                        = new LinkedList<>();  // LinkedList to add points that have the same slopes with the current point
                final double SLOPE_REF = p.slopeTo(
                        pointsBySlope[x]); // get the slope from current point to the next point in the sorted array which will be used as the reference for comparison
                do {
                    candidates.add(pointsBySlope[x++]);
                } while (x < copy.length && p.slopeTo(pointsBySlope[x]) == SLOPE_REF);

                // only select candidates LinkedList with more than three points and that the current point must be less than the first point in the list
                if (candidates.size() >= 3 && p.compareTo(candidates.peek()) < 0) {
                    Point min = p;
                    Point max = candidates.removeLast();
                    maxLineSegments.add(new LineSegment(min, max));
                }
            }
        }
        lineSegments = maxLineSegments.toArray(
                new LineSegment[0]); // convert the LinkedList to an array, as stipulated in the assignment
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].compareTo(points[i]) == 0)
                throw new IllegalArgumentException("There cannot be duplicate points!");
        }
    }

    private void checkNull(Point[] arr) {
        if (arr == null)
            throw new java.lang.IllegalArgumentException("Array of points cannot be null!");
        for (Point p : arr) {
            if (p == null)
                throw new java.lang.IllegalArgumentException("Point cannot be null!");
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.010);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
