package algorithms;

// import java.util.Arrays;

/**
 * Implementation of the standard recursive sequential merge sort algorithm.
 * Handles edge cases and utilizes the MergeHelper for the merge step.
 */
public class SequentialMergeSort implements SortAlgorithm {

    @Override
    public void sort(int[] array) {
        // Handle edge cases: null array or size 0/1
        if (array == null || array.length <= 1) {
            return;
        }
        // Start the recursive process
        mergeSortRecursive(array, 0, array.length - 1);
    }

    /**
     * The private recursive helper function for dividing the array.
     */
    private void mergeSortRecursive(int[] array, int start, int end) {
        // Base Case: Stop when the segment has 1 or 0 elements
        if (start < end) {
            int mid = start + (end - start) / 2;

            // 1. Divide: Recursively sort the two halves
            mergeSortRecursive(array, start, mid);
            mergeSortRecursive(array, mid + 1, end);

            // 2. Conquer: Merge the sorted halves using the static helper
            MergeHelper.merge(array, start, mid, end);
        }
    }
}
