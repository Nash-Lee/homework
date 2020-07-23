import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // create n-by-n grid, with all sites initially blocked

    private boolean[] status;
    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Illegal argument");
        size = n;
        status = new boolean[n * n + 2];
        for (int i = 1; i < n * n + 1; i++) {
            status[i] = false;
        }
        status[0] = true; // two virtual open site
        status[n * n + 1] = true;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 1; i <= n; i++) {
            union(0, i, uf);
            union(size * size + 1, size * size + 1 - i, uf);
            union(0, i, ufTop);
        }
    }


    //
    private void checkbound(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException("Illegal argument");
    }


    private void union(int aPos, int bPos, WeightedQuickUnionUF aUF) {
        if (aUF.find(aPos) != aUF.find(bPos))
            aUF.union(aPos, bPos);
    }


    // open the site(row,col) if it is not open already
    public void open(int row, int col) {
        checkbound(row, col);
        int nowPosi = getPosition(row, col);
        status[nowPosi] = true;
        int prevRowPos = getPosition(row - 1, col);
        int nextRowPos = getPosition(row + 1, col);
        int prevColPos = getPosition(row, col - 1);
        int nextColPos = getPosition(row, col + 1);


        if (row == 1) {
            union(0, nowPosi, uf);
            union(0, nowPosi, ufTop);
        }
        else if (isOpen(row - 1, col)) {
            union(prevRowPos, nowPosi, uf);
            union(prevRowPos, nowPosi, ufTop);
        }

        if (row == size) {
            union(size * size + 1, nowPosi, uf);
        }
        else if (isOpen(row + 1, col)) {
            union(nowPosi, nextRowPos, uf);
            union(nowPosi, nextRowPos, ufTop);
        }

        if (col != 1 && isOpen(row, col - 1)) {
            union(nowPosi, prevColPos, uf);
            union(nowPosi, prevColPos, ufTop);
        }
        if (col != size && isOpen(row, col + 1)) {
            union(nowPosi, nextColPos, uf);
            union(nowPosi, nextColPos, ufTop);
        }
    }


    //


    // number of open sites


    // position
    private int getPosition(int row, int col) {
        int pos = (row - 1) * size + col;
        return pos;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkbound(row, col);
        return status[getPosition(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkbound(row, col);
        return isOpen(row, col) && ufTop.find(0) == ufTop.find(getPosition(row, col));

    }

    // return the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i < size * size + 1; i++) {
            if (status[i])
                count += 1;
        }
        return count;
    }

    // does the system percolates?
    public boolean percolates() {
        if (size == 1)
            return isOpen(1, 1);
        return uf.find(0) == uf.find(size * size + 1);
    }


}
