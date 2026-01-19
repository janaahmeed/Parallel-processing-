package algorithms;

/**
 * The core interface that all sorting algorithms in the project must implement.
 */
public interface SortAlgorithm {

    /**
     * Sorts the input array of integers in place.
     * @param array The integer array to be sorted.
     */
    void sort(int[] array);
}