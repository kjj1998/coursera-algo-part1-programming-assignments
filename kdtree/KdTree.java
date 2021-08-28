/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 21 August 2021
 *  Description: An implementation of the mutable data type KdTree.java that
 *               uses a 2d-tree
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    // private Node helper class
    private static class Node {
        private Point2D p;      // the point contained in the node
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    private Node s;                 // Node instance variable
    private Queue<Point2D> iter;    // Queue instance variable
    private double nearestDist;
    // distance between the query point and the nearest point in the BST
    private Point2D nearest;        // nearest point in the BST to the query point
    private int sizeOfTree = 0;     // size of the BST

    /**
     * Constructs an empty KdTree of points
     */
    public KdTree() {
    }

    /**
     * Check if the KdTree is empty
     *
     * @return the boolean value
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the size of the KdTree
     *
     * @return the number of points in the KdTree
     */
    public int size() {
        if (s == null) return 0;            // check for empty tree
        return sizeOfTree;
    }

    /**
     * Insert the point into the set (if it is not already in the set)
     *
     * @param p the point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");

        s = insert(s, p, true, 0.0, 0.0, 1.0, 1.0);
    }

    /**
     * Private overloaded method to insert the Node at the correct position
     *
     * @param x           the current node in the traversal
     * @param p           the point which will be inserted into the node
     * @param orientation decides whether the x-coordinate or y-coordinate will be used for
     *                    comparison
     * @param xmin        minimum x-value of the axis-aligned rectangle
     * @param ymin        minimum y-value of the axis-aligned rectangle
     * @param xmax        maximum x-value of the axis-aligned rectangle
     * @param ymax        maximum y-value of the axis-aligned rectangle
     * @return the inserted Node
     */

    private Node insert(Node x, Point2D p, boolean orientation, double xmin, double ymin,
                        double xmax, double ymax) {
        if (x == null) {
            if (orientation) {
                sizeOfTree++;
                return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            }
            else {
                sizeOfTree++;
                return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            }
        }

        if (orientation) {
            int cmpX = Double.compare(p.x(), x.p.x());
            int cmpY = Double.compare(p.y(), x.p.y());

            if (cmpX < 0) x.lb = insert(x.lb, p, false, xmin, ymin, x.p.x(), ymax);
            else if (cmpX >= 0) {
                if (cmpX != 0 || cmpY != 0)
                    x.rt = insert(x.rt, p, false, x.p.x(), ymin, xmax, ymax);
            }
        }
        else {
            int cmpY = Double.compare(p.y(), x.p.y());
            int cmpX = Double.compare(p.x(), x.p.x());

            if (cmpY < 0) x.lb = insert(x.lb, p, true, xmin, ymin, xmax, x.p.y());
            else if (cmpY >= 0) {
                if (cmpX != 0 || cmpY != 0)
                    x.rt = insert(x.rt, p, true, xmin, x.p.y(), xmax, ymax);
            }
        }
        return x;
    }

    /**
     * Check if the KdTree contains the point
     *
     * @param p the point to be checked
     * @return boolean value
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");
        return contains(s, p, true) != null;
    }

    /**
     * private overloaded method to check if the tree contains a Node with a particular Point2D
     *
     * @param x           the Node to be checked
     * @param p           the Point2D that we are looking for
     * @param orientation decides whether the x-coordinate or y-coordinate will be used for
     *                    comparison
     * @return the Node which contains the Point2D (or null if it doesn't contain)
     */

    private Point2D contains(Node x, Point2D p, boolean orientation) {
        if (x == null) return null;

        if (orientation) {
            int cmpX = Double.compare(p.x(), x.p.x());
            int cmpY = Double.compare(p.y(), x.p.y());
            if (cmpX == 0 && cmpY == 0) {
                return x.p;
            }
            else if (cmpX < 0) return contains(x.lb, p, false);
            else return contains(x.rt, p, false);
        }
        else {
            int cmpX = Double.compare(p.x(), x.p.x());
            int cmpY = Double.compare(p.y(), x.p.y());
            if (cmpX == 0 && cmpY == 0) {
                return x.p;
            }
            else if (cmpY < 0) return contains(x.lb, p, true);
            else return contains(x.rt, p, true);
        }
    }

    /**
     * Draw all points in the set to standard draw
     */
    public void draw() {
        draw(s, true);
    }

    /**
     * Private overloaded method that draws the point to StdDraw
     *
     * @param x           the Node whose Point2D will be drawn
     * @param orientation decides how the point should be drawn (vertical for
     *                    x-coordinate and horizontal for y-coordinate)
     */

    private void draw(Node x, boolean orientation) {
        if (x == null) {
            return;
        }
        if (orientation) {
            draw(x.lb, false);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();

            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();
            draw(x.rt, false);
        }
        else {
            draw(x.lb, true);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();

            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();
            draw(x.rt, true);
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

        iter = new Queue<>();
        range(s, rect);
        return iter;
    }

    /**
     * private overloaded method to find points that intersect the queried rectangle
     *
     * @param x    the Node whose Point2D will be checked
     * @param rect the query rect
     */
    private void range(Node x, RectHV rect) {

        if (x == null) return;

        if (rect.contains(x.p)) {
            iter.enqueue(x.p);
        }

        if (x.lb != null && rect.intersects(x.lb.rect)) {
            range(x.lb, rect);
        }
        if (x.rt != null && rect.intersects(x.rt.rect)) {
            range(x.rt, rect);
        }
    }

    /**
     * Find a nearest neighbour in the set to point p, null if the set is empty
     *
     * @param p the point to find its nearest neighbour
     * @return point which is the nearest neighbour
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("argument cannot be null!");

        nearestDist = Double.POSITIVE_INFINITY;
        nearest(s, p, true);

        return nearest;
    }

    /**
     * Private overloaded nearest method. Always search in the subtree in which the query point
     * lies
     * in.
     * Do not need to search a subtree if the shortest distance between the subtree's rectangle and
     * the query point is greater than the current distance between query point and the nearest
     * point
     * in the BST.
     *
     * @param x           the Node to be checked
     * @param p           the query point
     * @param orientation to decide how the rectangles will be formed
     */

    private void nearest(Node x, Point2D p, boolean orientation) {

        if (x == null) return;

        if (x.p.distanceTo(p) < nearestDist) {
            nearestDist = x.p.distanceTo(p);
            nearest = x.p;
        }

        RectHV leftBotRect;
        RectHV rightTopRect;
        if (orientation) {
            leftBotRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            rightTopRect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());

            double distFromLeftRectToQueryPoint = leftBotRect.distanceTo(p);
            double distFromRightRectToQueryPoint = rightTopRect.distanceTo(p);

            if (distFromLeftRectToQueryPoint >= distFromRightRectToQueryPoint) {
                if (nearestDist > distFromRightRectToQueryPoint)
                    nearest(x.rt, p, false);
                if (nearestDist > distFromLeftRectToQueryPoint)
                    nearest(x.lb, p, false);
            }
            else {
                if (nearestDist > distFromLeftRectToQueryPoint) {
                    nearest(x.lb, p, false);
                }
                if (nearestDist > distFromRightRectToQueryPoint)
                    nearest(x.rt, p, false);
            }

        }
        else {
            leftBotRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            rightTopRect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());

            double distFromLeftRectToQueryPoint = leftBotRect.distanceTo(p);
            double distFromRightRectToQueryPoint = rightTopRect.distanceTo(p);

            if (distFromLeftRectToQueryPoint >= distFromRightRectToQueryPoint) {
                if (nearestDist > distFromRightRectToQueryPoint)
                    nearest(x.rt, p, true);
                if (nearestDist > distFromLeftRectToQueryPoint)
                    nearest(x.lb, p, true);
            }
            else {
                if (nearestDist > distFromLeftRectToQueryPoint) {
                    nearest(x.lb, p, true);
                }
                if (nearestDist > distFromRightRectToQueryPoint)
                    nearest(x.rt, p, true);
            }

        }
    }

    public static void main(String[] args) {
        /* Code for testing purposes */
        KdTree s = new KdTree();
        StdOut.println("Is the KdTree empty?: " + s.isEmpty());

        In in = new In("test.txt");
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = Double.parseDouble(in.readString());
            s.insert(new Point2D(x, y));
        }

        s.draw();
        Point2D queryPoint = new Point2D(0.74, 0.04);
        StdDraw.setPenRadius(0.005);
        queryPoint.draw();
        StdOut.println(s.size());
        StdOut.println("nearest point: " + s.nearest(queryPoint));
    }
}
