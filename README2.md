# Assignment 2 — Heap Sort

Group 4: Zarina & Nursultan

Group: SE-2429

This short guide shows **exactly** how to reproduce, verify, and assess the submission.  
All paths below assume the project root is `C:\assignment-java`. Replace with your path if needed.

---

## 1) Environment

**Required**
- Java **17** (Temurin/Eclipse Adoptium or OpenJDK)
- Maven **3.9+**

**Verify**
```powershell
java -version
mvn -version
```

---

## 2) Build & Test (correctness)

From the project root:
```powershell
cd "C:\assignment-java"
mvn clean test
```

**Expected:** `BUILD SUCCESS` with all tests passing.

Artifacts to check:
- Source: `src/main/java/algorithms/HeapSort.java`
- Tests:  `src/test/java/algorithms/HeapSortTest.java`

---

## 3) Benchmark (data for plots)

Run the CLI benchmark to produce a CSV with averaged trials across four input distributions:
```powershell
mvn exec:java "-Dexec.mainClass=cli.BenchmarkRunner" `
              "-Dexec.args=--algo heap --n 100,1000,10000,100000 --trials 5 --dist random,sorted,reverse,nearly-sorted"
```

**Expected console line:**
```
Wrote CSV: ...\docs\performance-plots\heap_bench.csv
```

**Output file:**  
`docs/performance-plots/heap_bench.csv`

**CSV columns:**  
`algo,n,dist,trial,time_ms,comparisons,swaps,array_accesses,allocations`

---

## 4) Plotting — quick verification

Open the CSV in Google Sheets / Excel and create a **pivot table**:

- **Rows:** `n`
- **Columns:** `dist`  
- **Values:** **Average of `time_ms`**

Insert a **Line chart**:
- X axis = `n`
- Series = `random, sorted, reverse, nearly-sorted`
- Title suggestion: *Heap Sort — time (ms) vs n (avg of 5 trials)*

**Optional second plot:** repeat for **Average of `array_accesses`**.

**Expected shape:** curves consistent with **Θ(n log n)**; small differences between distributions (heap height dominates per extraction).

---

## 5) Report

Report as docx and all screenshots are located in the `results` directory, including the diagrams and the overall results.

---

## 6) Quick rubric alignment (suggested)

- **Implementation quality (Correctness/Tests):** Section 2 + test suite.  
- **Empirical validation:** Section 3 (CSV) + Section 4 (plots).  
- **Complexity & discussion:** Section 5 (theory + interpretation).  
- **Reproducibility / Documentation:** README instructions + consistent paths; CSV + plots present.

---

## 7) Troubleshooting

- **`Unknown lifecycle phase ".mainClass=..."`**  
  Cause: PowerShell parsing. Use quotes exactly as shown:
  ```
  mvn exec:java "-Dexec.mainClass=cli.BenchmarkRunner" "-Dexec.args=--algo heap --n 100,1000,10000,100000 --trials 5 --dist random,sorted,reverse,nearly-sorted"
  ```

- **`BUILD FAILURE: no POM`**  
  Run from the **project root** that contains `pom.xml`.

- **Charts look wrong (flat/empty):**  
  Ensure pivot uses **Average** of the metric, and X axis is `n`; disable grand totals; series are the four `dist` values.

---

## 8) Minimal Unix/macOS equivalents

```bash
cd /path/to/assignment-java
mvn clean test
mvn exec:java -Dexec.mainClass=cli.BenchmarkRunner -Dexec.args="--algo heap --n 100,1000,10000,100000 --trials 5 --dist random,sorted,reverse,nearly-sorted"
```

CSV: `docs/performance-plots/heap_bench.csv` → build plots as above.

---

## 9) Directory map

```
assignment-java/
├─ src/main/java/
│  ├─ algorithms/HeapSort.java
│  ├─ metrics/PerformanceTracker.java
│  └─ cli/BenchmarkRunner.java
├─ src/test/java/
│  └─ algorithms/HeapSortTest.java
├─ docs/
│  ├─ analysis-report.pdf
│  └─ performance-plots/
│     └─ heap_bench.csv
├─ results/
│  ├─ tests-pass.png
│  ├─ bench-run.png
│  └─ plot-preview.png
├─ pom.xml
└─ README.md
```

---

**One-liner for verification:** tests pass, CSV generated, plot shows n log n trend, report contains the plot and theory; structure matches the map above.
