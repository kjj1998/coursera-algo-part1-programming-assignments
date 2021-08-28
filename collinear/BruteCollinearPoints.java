/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ****************************************************************************
 * */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {


    private final LineSegment[] segment;

    public BruteCollinearPoints(Point[] points) {

        checkNull(points);
        Point[] copy = points.clone();  // defensive copy for immutability

        Arrays.sort(copy);
        checkDuplicate(copy);
        List<LineSegment> maxLineSegments
                = new LinkedList<>(); // LinkedList to hold the maximal line segments

        /*
        for (Point p : points) {
            StdOut.print(p + " ");
        }
        StdOut.println();
         */

        for (int i = 0; i < copy.length; i++)
            for (int j = i + 1; j < copy.length; j++)
                for (int k = j + 1; k < copy.length; k++)
                    for (int n = k + 1; n < copy.length; n++) {

                        double ij = copy[i].slopeTo(copy[j]);
                        double ik = copy[i].slopeTo(copy[k]);
                        double in = copy[i].slopeTo(copy[n]);

                        
                        if (ij == ik && ik == in) {
                            maxLineSegments.add(new LineSegment(copy[i], copy[n]));
                            // StdOut.print("point i: " 
                            // + points[i] + " , point l: " + points[l]);
                            // StdOut.println();
                        }
                    }
        segment = maxLineSegments.toArray(new LineSegment[0]);
    }

    private void checkDuplicate(Point[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1].compareTo(arr[i]) == 0)
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
        return segment.length;
    }

    public LineSegment[] segments() {
        return segment.clone();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
