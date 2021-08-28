/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 9th August 2021
 *  Description: Implementation of Solver
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private int minMoves = -1;
    private Stack<Board> result;
    private boolean solvableStatus;

    // helper Node class
    private class Node {
        Board b;
        int moves;
        Node previous;
        int priority;
        int manhattan;

        Node(Board b) {
            this.b = b;
            this.moves = 0;
            this.manhattan = b.manhattan();
            this.priority = this.manhattan + this.moves;
            this.previous = null;
        }

        Node(Board b, Node previous, int moves) {
            this.b = b;
            this.moves = moves;
            this.manhattan = b.manhattan();
            this.priority = this.manhattan + this.moves;
            this.previous = previous;
        }
    }

    // helper comparator class
    private static class priorityOrder implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return Integer.compare(a.priority, b.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        // check for null argument
        if (initial == null) {
            throw new java.lang.IllegalArgumentException("Argument to constructor cannot be null.");
        }

        /* Two nodes, one for the board and one for its twin */
        Node dequeued;
        Node dequeuedTwin;

        Comparator<Node> comparebyPriority = new priorityOrder();   // comparator
        MinPQ<Node> pq = new MinPQ<>(comparebyPriority);    // Minimum Priority Queue for board
        MinPQ<Node> pqTwin = new MinPQ<>(
                comparebyPriority);    // Minimum Priority Queue for board twin

        pq.insert(new Node(initial));   // insert initial Node
        pqTwin.insert(new Node(initial.twin()));    // insert initial Node twin

        while (true) {
            dequeued = pq.delMin();
            dequeuedTwin = pqTwin.delMin();

            /* Check if board is at Goal stage or if twin board is at goal stage*/
            if (dequeued.b.isGoal()) {
                solvableStatus = true;
                this.minMoves = dequeued.moves;
                Stack<Board> a = new Stack<>();

                while (dequeued != null) {
                    a.push(dequeued.b);
                    dequeued = dequeued.previous;
                }
                this.result = a;
                break;
            }
            else if (dequeuedTwin.b.isGoal()) {
                solvableStatus = false;
                break;
            }

            /* Iterables for neighbors of board and twin board */
            Iterable<Board> neighbors = dequeued.b.neighbors();
            Iterable<Board> neighborsTwin = dequeuedTwin.b.neighbors();

            /* Iterate through the iterables of neighbouring boards and only add the unique ones */
            for (Board c : neighbors) {
                if (dequeued.previous == null || !dequeued.previous.b.equals(c)) {
                    pq.insert(new Node(c, dequeued, dequeued.moves + 1));
                }
            }

            for (Board c : neighborsTwin) {
                if (dequeuedTwin.previous == null || !dequeuedTwin.previous.b.equals(c)) {
                    pqTwin.insert(new Node(c, dequeuedTwin, dequeuedTwin.moves + 1));
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvableStatus;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!solvableStatus) {
            return -1;
        }
        else {
            return minMoves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvableStatus) {
            return null;
        }
        else {
            return this.result;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
