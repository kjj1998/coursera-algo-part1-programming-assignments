/* *****************************************************************************
 *  Name: Koh Jun Jie
 *  Date: 7th August 2021
 *  Description: Immutable data type Board
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {

    private int n;  // n-by-n array
    private int[][] tiles;
    private int iZero;  // the index of the role which 0 resides on
    private int jRandom1;   // a random column index value, used to generate the twin board
    private int jRandom2;   // a random column index value, used to generate the twin board
    private int iRandom;    // a random row index value, used to generate the twin board
    private Board twin; // the twin board for easy retrieval

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        this.n = tiles.length;
        this.tiles = clone(tiles);  // cloning for immutability

        /* Check for the row that contains the 0 value */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    this.iZero = i;
                    break;
                }
            }
        }

        /* Generate the two random column indexes and the row index that does not contain the 0 value for the twin board */
        while ((jRandom1 == jRandom2) || (iRandom == iZero)) {
            jRandom1 = StdRandom.uniform(0, n);
            jRandom2 = StdRandom.uniform(0, n);
            iRandom = StdRandom.uniform(0, n);
        }
    }

    // private method to clone tiles
    private int[][] clone(int[][] target) {
        int len = target.length;
        int[][] clone = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                clone[i][j] = target[i][j];
            }
        }
        return clone;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int score = 0;
        int rightAns = 1;   // the right answer for that position

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                // Don't need to check the value at tile[n-1][n-1] because it should be zero
                if (i == n - 1 && j == n - 1)
                    break;
                if (tiles[i][j] != rightAns)
                    score++; // increment it the correct value is not at the correct position
                rightAns++;
            }

        return score;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int score = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (tiles[i][j] == 0)   // if value of tile is zero
                    continue;

                if (tiles[i][j] != i * n + j + 1) {
                    int quotient = tiles[i][j] / n;
                    int remainder = tiles[i][j] % n;

                    if (remainder != 0) {
                        int iActual = quotient;
                        int jActual = remainder - 1;
                        int move = Math.abs(iActual - i) + Math.abs(jActual - j);

                        score += move;
                    }
                    else {
                        int iActual = quotient - 1;
                        int jActual = n - 1;
                        int move = Math.abs(iActual - i) + Math.abs(jActual - j);

                        score += move;
                    }
                }
            }
        }
        return score;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0 && manhattan() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        return (this.dimension() == that.dimension()) && (Arrays
                .deepEquals(this.tiles, that.tiles));
    }

    // all neighbouring boards
    public Iterable<Board> neighbors() {
        Stack<Board> iter = new Stack<>();
        int[][] left = clone(tiles);
        int[][] right = clone(tiles);
        int[][] up = clone(tiles);
        int[][] down = clone(tiles);

        /* Generate at least 2 and up to 4 neighbour boards based on the position of the 0 value*/
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    if (j - 1 >= 0) {
                        int temp = left[i][j];
                        left[i][j] = left[i][j - 1];
                        left[i][j - 1] = temp;
                        iter.push(new Board(left));
                    }
                    if (j + 1 < n) {
                        int temp = right[i][j];
                        right[i][j] = right[i][j + 1];
                        right[i][j + 1] = temp;
                        iter.push(new Board(right));
                    }
                    if (i - 1 >= 0) {
                        int temp = up[i][j];
                        up[i][j] = up[i - 1][j];
                        up[i - 1][j] = temp;
                        iter.push(new Board(up));
                    }
                    if (i + 1 < n) {
                        int temp = down[i][j];
                        down[i][j] = down[i + 1][j];
                        down[i + 1][j] = temp;
                        iter.push(new Board(down));
                    }
                }
            }
        }
        return iter;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        /* Generate the twin board if not already done so */
        if (this.twin == null) {
            int[][] arr = clone(tiles);

            int temp = arr[iRandom][jRandom1];
            arr[iRandom][jRandom1] = arr[iRandom][jRandom2];
            arr[iRandom][jRandom2] = temp;
            this.twin = new Board(arr);

            return this.twin;
        }
        else {
            /* return the twin board if already generated */
            return this.twin;
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        /* Some code to the test out the methods of class Board */
        int[][] test = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        int[][] correct = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        int[][] test2 = {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };

        Board b = new Board(test);
        Board c = new Board(correct);
        Board d = new Board(correct);
        Board e = new Board(test2);

        StdOut.println("Hamming = " + b.hamming());
        StdOut.println("Manhattan = " + b.manhattan());

        StdOut.println("Goal?: " + c.isGoal());
        StdOut.println("Same?: " + c.equals(d));

        Iterable<Board> iter = e.neighbors();

        for (Board z : iter) {
            StdOut.println(z);
        }

        StdOut.println("Original: \n" + e + "Twin: \n" + e.twin());
    }
}
