package cli;

import algorithms.HeapSort;
import metrics.PerformanceTracker;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.stream.Collectors;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Map<String,String> flags = parseArgs(args);
        String algo = flags.getOrDefault("algo", "heap");
        String nList = flags.getOrDefault("n", "100,1000,10000,100000");
        String distList = flags.getOrDefault("dist", "random,sorted,reverse,nearly-sorted");
        int trials = Integer.parseInt(flags.getOrDefault("trials", "5"));

        List<Integer> sizes = Arrays.stream(nList.split(","))
                .map(String::trim).filter(s->!s.isEmpty()).map(Integer::parseInt).collect(Collectors.toList());
        List<String> dists = Arrays.stream(distList.split(","))
                .map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList());

        Path outDir = Paths.get("docs","performance-plots");
        Files.createDirectories(outDir);
        Path csv = outDir.resolve(algo + "_bench.csv");

        PerformanceTracker m = new PerformanceTracker();
        List<String> out = new ArrayList<>();
        out.add(m.toCsvHead());

        Random rnd = new Random(42);
        for (int n : sizes) {
            for (String dist : dists) {
                for (int t = 1; t <= trials; t++) {
                    int[] a = makeArray(n, dist, rnd);
                    m.reset();
                    long t0 = System.nanoTime();
                    if ("heap".equalsIgnoreCase(algo)) {
                        HeapSort.sort(a, m);
                    } else {
                        HeapSort.sort(a, m);  // Default to heap; extend here for other algos
                    }
                    long t1 = System.nanoTime();
                    long ms = (t1 - t0) / 1_000_000L;
                    out.add(m.toCsvRow(algo, n, dist, t, ms));
                }
                System.out.println("Done: n=" + n + " dist=" + dist);
            }
        }
        Files.write(csv, out);
        System.out.println("Wrote CSV: " + csv.toAbsolutePath());
    }

    private static int[] makeArray(int n, String dist, Random rnd) {
        int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
        switch (dist.toLowerCase()) {
            case "sorted":
                Arrays.sort(a);
                break;
            case "reverse":
                Arrays.sort(a);
                for (int i = 0; i < n/2; i++) {
                    int t = a[i]; a[i] = a[n-1-i]; a[n-1-i] = t;
                }
                break;
            case "nearly-sorted":
                Arrays.sort(a);
                // swap 1% of elements randomly
                int swaps = Math.max(1, n/100);
                for (int s = 0; s < swaps; s++) {
                    int i = rnd.nextInt(n), j = rnd.nextInt(n);
                    int t = a[i]; a[i] = a[j]; a[j] = t;
                }
                break;
            default:
                // random already
        }
        return a;
    }

    private static Map<String,String> parseArgs(String[] args) {
        Map<String,String> m = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i].substring(2);
                String val = "true";
                if (i+1 < args.length && !args[i+1].startsWith("--")) {
                    val = args[++i];
                }
                m.put(key, val);
            } else if (args[i].startsWith("-Dexec.args=")) {
                // Allow passing entire arg string via -Dexec.args=...
                String v = args[i].substring("-Dexec.args=".length());
                // naive split by space
                for (String tok : v.split(" ")) {
                    if (tok.startsWith("--")) {
                        int eq = tok.indexOf('=');
                        if (eq > 2) {
                            m.put(tok.substring(2, eq), tok.substring(eq+1));
                        }
                    }
                }
            }
        }
        return m;
    }
}
