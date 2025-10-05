package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import metrics.PerformanceTracker;

public class HeapSortTest {
    @Test
    void emptyAndSingleton() {
        int[] a = {};
        HeapSort.sort(a, new PerformanceTracker());
        assertArrayEquals(new int[]{}, a);

        int[] b = {42};
        HeapSort.sort(b, new PerformanceTracker());
        assertArrayEquals(new int[]{42}, b);
    }

    @Test
    void duplicatesAndPatterns() {
        int[] a = {5,5,5,5,5};
        HeapSort.sort(a, new PerformanceTracker());
        assertTrue(HeapSort.isSorted(a));

        int[] b = {9,8,7,6,5,4,3,2,1};
        HeapSort.sort(b, new PerformanceTracker());
        assertTrue(HeapSort.isSorted(b));
    }

    @Test
    void randomLarge() {
        Random rnd = new Random(123);
        int[] a = rnd.ints(10000, -100000, 100000).toArray();
        int[] b = a.clone();
        HeapSort.sort(a, new PerformanceTracker());
        Arrays.sort(b);
        assertArrayEquals(b, a);
    }
}
