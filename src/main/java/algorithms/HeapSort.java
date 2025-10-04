package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public final class HeapSort {
    private HeapSort(){}

    public static void sort(int[] a, PerformanceTracker m) {
        if (a == null || a.length < 2) return;
        // treat m as optional
        if (m == null) m = new PerformanceTracker();
        int n = a.length;
        // heapify (sift-down from middle)
        for (int i = (n >>> 1) - 1; i >= 0; i--) {
            siftDown(a, n, i, m);
        }
        // extract max one by one
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end, m);
            siftDown(a, end, 0, m);
        }
    }

    private static void siftDown(int[] a, int size, int i, PerformanceTracker m) {
        while (true) {
            int left = (i << 1) + 1;
            if (left >= size) break;
            int right = left + 1;
            int largest = left;
            // compare a[right] and a[left]
            if (right < size) {
                m.comparisons++;
                m.arrayAccesses += 2;
                if (a[right] > a[left]) largest = right;
            }
            // compare a[largest] and a[i]
            m.comparisons++;
            m.arrayAccesses += 2;
            if (a[largest] > a[i]) {
                swap(a, i, largest, m);
                i = largest;
            } else break;
        }
    }

    private static void swap(int[] a, int i, int j, PerformanceTracker m) {
        int t = a[i]; m.arrayAccesses++;
        a[i] = a[j];  m.arrayAccesses++;
        a[j] = t;     m.arrayAccesses++;
        m.swaps++;
    }

    // Helper to validate quickly
    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}
