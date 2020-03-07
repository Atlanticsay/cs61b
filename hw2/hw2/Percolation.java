package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/** @author Atlantic */

public class Percolation {

    // the grid saves the parent no. of each member in different unions
    private WeightedQuickUnionUF gridWQU;
    private WeightedQuickUnionUF gridWQUNoBottom;
    private boolean[][] gridOpen;
    private int gridLen;
    private int numOfOpen;
    private int virtualTop;
    private int virtualBottom;

    /** create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        gridLen = N;
        numOfOpen = 0;
        virtualTop = gridLen * gridLen;
        virtualBottom = gridLen * gridLen + 1;
        gridOpen = new boolean[gridLen][gridLen];
        gridWQU = new WeightedQuickUnionUF(gridLen * gridLen + 2);
        gridWQUNoBottom = new WeightedQuickUnionUF(gridLen * gridLen + 1);

        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                gridOpen[i][j] = false;
            }
        }

    }

    /** open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        if (((row < 0) || (row > gridLen - 1)) ||
             ((col < 0) || (col > gridLen - 1))) {
            throw new IndexOutOfBoundsException();
        }
        numOfOpen++;
        gridOpen[row][col] = true;
        connectToOpenNeighbors(row, col);
    }

    /** connect current location to its open surroundings */
    private void connectToOpenNeighbors(int row, int col) {
        int idx = xyTo1D(row, col);
        if ((col > 0) && isOpen(row, col - 1)) {
            gridWQU.union(idx, xyTo1D(row, col - 1));
            gridWQUNoBottom.union(idx, xyTo1D(row, col - 1));
        }
        if ((col < gridLen - 1) && isOpen(row, col + 1)) {
            gridWQU.union(idx, xyTo1D(row, col + 1));
            gridWQUNoBottom.union(idx, xyTo1D(row, col + 1));
        }
        if ((row > 0) && isOpen(row - 1, col)) {
            gridWQU.union(idx, xyTo1D(row - 1, col));
            gridWQUNoBottom.union(idx, xyTo1D(row - 1, col));
        }
        if ((row < gridLen - 1) && isOpen(row + 1, col)) {
            gridWQU.union(idx, xyTo1D(row + 1, col));
            gridWQUNoBottom.union(idx, xyTo1D(row + 1, col));
        }

        if (row == 0) {
            gridWQU.union( virtualTop, idx);
            gridWQUNoBottom.union(virtualTop, idx);
        }
        if (row == (gridLen - 1)) {
            gridWQU.union(virtualBottom, idx);
        }
    }

    /** whether is the site (row, col) open */
    public boolean isOpen(int row, int col) {
        if (((row < 0) || (row > gridLen - 1)) ||
             ((col < 0) || (col > gridLen - 1))) {
            throw new IndexOutOfBoundsException();
        }
        return gridOpen[row][col];
    }

    private boolean isOpen(int idx) {
        int[] rc = idx2XY(idx);
        if (((rc[0] < 0) || (rc[0] > gridLen - 1)) ||
             ((rc[1] < 0) || (rc[1] > gridLen - 1))) {
            throw new IndexOutOfBoundsException();
        }
        return gridOpen[rc[0]][rc[1]];
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (((row < 0) || (row > gridLen - 1)) ||
             ((col < 0) || (col > gridLen - 1))) {
            throw new IndexOutOfBoundsException();
        }
        int idx = xyTo1D(row, col);
        return isOpen(idx) && gridWQU.connected(virtualTop, idx)
                           && gridWQUNoBottom.connected(virtualTop, idx);
    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return gridWQU.connected(virtualTop, virtualBottom);
    }

    /** convert the x, y coordinate to the single number coordinate */
    private int xyTo1D(int row, int col) {
        return row * gridLen + col;
    }

    /** convert the index to x, y coordinate in the grid */
    private int[] idx2XY(int idx) {
        if ((idx < 0) || (idx > gridLen * gridLen - 1)) {
            throw new IllegalArgumentException("Illegal input of index. ");
        }
        int[] xy = new int[2];
        xy[0] = idx / gridLen;
        xy[1] = idx - gridLen * xy[0];
        return xy;
    }

    /** use for unit testing (not required,
     * but keep this here for the autograder) */
    public static void main(String[] args) {}

}
