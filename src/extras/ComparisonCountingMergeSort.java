package extras;

import algorithms.SortAlgorithm;

public class ComparisonCountingMergeSort implements SortAlgorithm {

    // ============================
    // METRICS OBJECT
    // ============================
    private final Metrics metrics = new Metrics();

    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;

        mergeSortRecursive(array, 0, array.length - 1);
    }

    // ============================
    // RECURSIVE MERGE SORT
    // ============================
    private void mergeSortRecursive(int[] array, int start, int end) {

        // Count recursive call
        metrics.incrementRecursiveCalls();

        if (start < end) {
            int mid = start + (end - start) / 2;

            mergeSortRecursive(array, start, mid);
            mergeSortRecursive(array, mid + 1, end);

            // Count merge
            metrics.incrementMergeCount();

            // Use custom merge that counts comparisons
            mergeWithCounting(array, start, mid, end);
        }
    }

    // ============================
    // CUSTOM MERGE WITH METRICS
    // ============================
    private void mergeWithCounting(int[] array, int start, int mid, int end) {

        int[] left = new int[mid - start + 1];
        int[] right = new int[end - mid];

        // Copy data
        System.arraycopy(array, start, left, 0, left.length);
        System.arraycopy(array, mid + 1, right, 0, right.length);

        int i = 0, j = 0, k = start;

        // Merge while counting comparisons
        while (i < left.length && j < right.length) {
            metrics.incrementComparisons();

            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Remaining elements
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    // ============================
    // METHOD FOR BENCHMARKS
    // ============================
    public Metrics getMetrics() {
        return metrics;
    }

    // ============================
    // METRICS CLASS
    // ============================
    public static class Metrics {
        private long comparisonCount = 0;
        private long mergeCount = 0;
        private long recursiveCalls = 0;

        public void incrementComparisons() { comparisonCount++; }
        public void incrementMergeCount() { mergeCount++; }
        public void incrementRecursiveCalls() { recursiveCalls++; }

        public long getComparisonCount() { return comparisonCount; }
        public long getMergeCount() { return mergeCount; }
        public long getRecursiveCalls() { return recursiveCalls; }

        @Override
        public String toString() {
            return "\nComparison Counting Merge Sort Metrics:" +
                   "\nComparisons = " + comparisonCount +
                   "\nMerges      = " + mergeCount +
                   "\nRecursive Calls = " + recursiveCalls;
        }
    }
}
