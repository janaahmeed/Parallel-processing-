package benchmark;

import algorithms.*;
import extras.*;
import java.util.Arrays;

public class SortBenchmark {

    private static final String[] PATTERNS = {"Random", "Reverse", "Sorted"};

    public static void testProgram(int[] SIZES, int Runs) {

        for (int size : SIZES) // Loop on each element of SIZES [10,000, 100,000, 1,000,000]
        {
            for (String pattern : PATTERNS) // Loop on each element of PATTERNS ["Random", "Reverse", "Sorted"]
            {

                int[] baseArray = generateArray(pattern, size);

                System.out.println("==============================================");
                System.out.println("Array size: " + size + " | Pattern: " + pattern);
                System.out.println("==============================================");

                // Sequential Merge Sort
                runAndPrintBenchmark("SequentialMergeSort", Runs, new SequentialMergeSort(), baseArray, null);

                // Parallel Merge Sort
                runAndPrintBenchmark("ParallelMergeSort", Runs, new ParallelMergeSort(), baseArray, null);
                
                // Comparison Counting Merge Sort
                ComparisonCountingMergeSort countingSort = new ComparisonCountingMergeSort();
                runAndPrintBenchmark("ComparisonCountingMergeSort", Runs, countingSort, baseArray, countingSort.getMetrics());

                // Built-in sequenctial sort
                runAndPrintBenchmark("Arrays.sort", Runs, (arr) -> Arrays.sort(arr), baseArray, null);

                // Built-in parallel sort
                runAndPrintBenchmark("Arrays.parallelSort", Runs, (arr) -> Arrays.parallelSort(arr), baseArray, null);

                System.out.println("\n\n");
            }
        }
    }

    // Generate array based on pattern
    private static int[] generateArray(String pattern, int size) {
        switch (pattern) {
            case "Random":
                return InputGenerator.generateRandomArray(size);
            case "Reverse":
                return InputGenerator.generateReverseSortedArray(size);
            case "Sorted":
                return InputGenerator.generateSortedArray(size);
            default:
                throw new IllegalArgumentException("Unknown pattern: " + pattern);
        }
    }

    // Run the benchmark and print results
    private static void runAndPrintBenchmark(String name, int RUNS, SortAlgorithm sorter, int[] baseArray, ComparisonCountingMergeSort.Metrics metrics) {

        long totalTime = 0;

        for (int i = 0; i < RUNS; i++) {
            int[] arrCopy = InputGenerator.copyArray(baseArray);

            long start = System.nanoTime();
            sorter.sort(arrCopy);
            long end = System.nanoTime();

            totalTime += (end - start); // Total time of Number of RUNS in nanoseconds
        }

        double averageMs = totalTime / (RUNS * 1_000_000.0); // Calculate average in milliseconds

        BenchmarkResult result = new BenchmarkResult(name , averageMs, metrics);
        result.print();
    }
}