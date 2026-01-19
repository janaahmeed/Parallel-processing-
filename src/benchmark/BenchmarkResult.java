package benchmark;

import extras.ComparisonCountingMergeSort;

public class BenchmarkResult {

    private final String algorithmName;
    private final double averageTimeMs;
    private final ComparisonCountingMergeSort.Metrics metrics; // can be null if not counting

    public BenchmarkResult(String algorithmName, double averageTimeMs, ComparisonCountingMergeSort.Metrics metrics) {
        this.algorithmName = algorithmName;
        this.averageTimeMs = averageTimeMs;
        this.metrics = metrics;
    }

    public void print() {
        System.out.println("Algorithm: " + algorithmName);
        System.out.printf("Average Time: %.3f ms\n", averageTimeMs);
        if (metrics != null) {
            System.out.println(metrics);
        }
        System.out.println("---------------------------------------------------");
    }
}