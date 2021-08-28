/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 ****************************************************************************
 * */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private boolean[] gridStatus;
    private int openSitesCount = 0;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be less than or equal to 0");
        }
        gridSize = n;
        gridStatus = new boolean[n * n + 2];

        for (int i = 0; i < (n * n + 2); i++) {
            gridStatus[i] = false;
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // maps 2D coordinates to 1D coordinates
    private int xyTo1D(int x, int y, int gs) {

        if (x == 0) {
            return y;
        }
        else {
            return ((x - 1) * gs + y);
        }
    }

    // validating indices entered
    private void validate(int x, int y) {
        if ((x <= 0 || x > gridSize) || (y <= 0 || y > gridSize)) {
            throw new IllegalArgumentException(
                    "row/col is out of bounds");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row <= 0 || row > gridSize) || (col <= 0 || col > gridSize)) {
            throw new IllegalArgumentException(
                    "row/col is out of bounds");
        }

        if (!(isOpen(row, col))) {
            int position = xyTo1D(row, col, gridSize);

            gridStatus[position] = true;

            // Connect any of top row sites to the virtual top site
            if (position <= gridSize) {
                uf.union(0, position);
            }

            // Connect any of the bottom row sites to the virtual bottom site
            if (position <= (gridSize * gridSize) && position > (gridSize * gridSize - gridSize)) {
                uf.union(gridSize * gridSize + 1, position);
            }

            // Check the site to the bottom to see if its opened
            if (!(position + gridSize > gridSize * gridSize)) {
                if (gridStatus[position + gridSize]) {
                    uf.union(position, position + gridSize);
                }
            }

            // Check the site to the top to see if its opened
            if (!(position - gridSize <= 0)) {
                if (gridStatus[position - gridSize]) {
                    uf.union(position, position - gridSize);
                }
            }

            // Check the site to the left to see if its opened
            if ((position - 1) % gridSize != 0) {
                if (gridStatus[position - 1]) {
                    uf.union(position, position - 1);
                }
            }

            // Check the site to the right to see if its opened
            if ((position + 1) % gridSize != 1) {
                if (gridStatus[position + 1]) {
                    uf.union(position, position + 1);
                }
            }

            openSitesCount++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int position = xyTo1D(row, col, gridSize);

        return gridStatus[position];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        boolean open = isOpen(row, col);
        int position = xyTo1D(row, col, gridSize);
        return open && connect(position);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolates?
    public boolean percolates() {
        return uf.find(0) == uf.find(gridSize * gridSize + 1);
    }

    private boolean connect(int x) {
        return uf.find(x) == uf.find(0);
    }

    // test client (optional)
    public static void main(String[] args) {

        int n = StdIn.readInt();
        Percolation a = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            StdOut.println("p = " + p);
            int q = StdIn.readInt();
            StdOut.println("q = " + q);
            a.open(p, q);
        }
        StdOut.println(a.percolates());
    }
}
