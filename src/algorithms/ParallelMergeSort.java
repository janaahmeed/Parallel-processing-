package algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort implements SortAlgorithm {

    private int cores;

    // No-argument constructor
    public ParallelMergeSort() {
        cores = Runtime.getRuntime().availableProcessors();
    }

    /**
     * Sorts the input array in parallel using Fork/Join and MergeSortTask.
     */
    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;

        int threshold = 50000; // choose a reasonable threshold
        MergeSortTask task = new MergeSortTask(array, 0, array.length - 1, threshold);

        ForkJoinPool pool = new ForkJoinPool(cores);
        pool.invoke(task);
        pool.shutdown();
    }

    /**
     * Optional helper to print the array before and after sorting
     * Can replace the old parMergeSort() method
     */
    public void sortAndPrint(int[] array) {
        System.out.println("Original array: " + Arrays.toString(array));
        sort(array);
        System.out.println("Sorted array:   " + Arrays.toString(array));
    }
}