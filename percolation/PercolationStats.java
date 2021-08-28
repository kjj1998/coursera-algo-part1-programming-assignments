/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 ****************************************************************************
 * */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] sum;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Either n or trials are less than or equal to 0");
        }
        Percolation p;
        sum = new double[trials];
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            int openSites = p.numberOfOpenSites();
            double fraction = ((double) openSites) / (n * n);

            /*
            StdOut.println(
                    "Trial: " + (i + 1) + ", Number of open sites: " + openSites + ", fraction: "
                            + fraction);
            */
            sum[i] = fraction;

            /*

             */
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(sum);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sum);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (CONFIDENCE_95 * stddev()) / (Math.sqrt(sum.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (CONFIDENCE_95 * stddev()) / (Math.sqrt(sum.length)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, trials);


        StdOut.println("mean                    = " + p.mean());
        StdOut.println("stddev                  = " + p.stddev());
        StdOut.println(
                "95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");

    }
}
