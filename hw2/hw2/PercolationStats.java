package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    // The faction of open sites in computational experiment t
    private double[] fraction;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if ((N <= 0) || (T <= 0)) {
            throw new IllegalArgumentException();
        }
        fraction = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                openSitesRandom(p, N);
            }
            fraction[i] = ((double) p.numberOfOpenSites()) / (N * N);
        }
    }

    // Randomly open sites in a given grid
    private void openSitesRandom(Percolation p, int N) {
        int idx = StdRandom.uniform(0, N * N -1);
        int[] id2d = idx2XY(idx, N);
        if (p.isOpen(id2d[0], id2d[1])) {
            return;
        }
        p.open(id2d[0], id2d[1]);
    }

    // convert the index to x, y coordinate in the grid
    private int[] idx2XY(int idx, int gridLen) {
        if ((idx < 0) || (idx > gridLen * gridLen - 1)) {
            throw new IllegalArgumentException("Illegal input of index. ");
        }
        int[] xy = new int[2];
        xy[0] = idx / gridLen;
        xy[1] = idx - 5 * xy[0];
        return xy;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(fraction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(fraction.length);
    }
}
