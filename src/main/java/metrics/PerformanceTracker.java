package metrics;

public class PerformanceTracker {
    public long comparisons;
    public long swaps;
    public long arrayAccesses;
    public long allocations;

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = 0L;
    }

    public String toCsvHead() {
        return "algo,n,dist,trial,time_ms,comparisons,swaps,array_accesses,allocations";
    }

    public String toCsvRow(String algo, int n, String dist, int trial, long timeMs) {
        return String.format("%s,%d,%s,%d,%d,%d,%d,%d",
                algo, n, dist, trial, timeMs, comparisons, swaps, arrayAccesses, allocations);
    }
}
