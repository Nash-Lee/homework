import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] attempts;
    private double temp = 1.96;

    //
    public PercolationStats(int n, int trails) {
        if (n <= 0 || trails <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        attempts = new double[trails];
        for (int i = 0; i < trails; i++) {
            Percolation test = new Percolation(n);
            int step = 0;
            while (!test.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (test.isOpen(row, col))
                    continue;
                test.open(row, col);
                step++;

            }
            attempts[i] = (double) step / (n * n);
        }
    }

    //
    public double mean() {
        return StdStats.mean(attempts);
    }

    // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(attempts);
    }

    // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - ((temp * stddev())) / Math.sqrt(attempts.length);
    }

    //
    public double confidenceHi() {
        return mean() - ((temp * stddev())) / Math.sqrt(attempts.length);
    }


    public static void main(String[] args) {
        StdOut.println("请输入");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.printf("%-25s=%.7f\n", "mean", ps.mean());
        StdOut.printf("%-25s=%.17f\n", "stddev", ps.stddev());
        StdOut.printf("%-25s=[%.15f, %.15f]\n", "%95 confidence interval", ps.confidenceLo(),
                      ps.confidenceHi());

    }

}







