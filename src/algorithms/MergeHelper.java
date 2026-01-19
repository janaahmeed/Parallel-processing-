package algorithms;

/**
 * A dedicated helper class containing the static merge function.
 * CRITICAL OUTPUT FOR PERSON 3 (MergeSortTask) AND PERSON 4.
 */
public class MergeHelper {

/**
     * Merges two sorted segments of the array into a single sorted segment.
 * @param array The array containing the segments to be merged.
     * @param start The starting index of the left segment.
     * @param mid The ending index of the left segment (and split point).
     * @param end The ending index of the right segment.
     */
    public static void merge(int[] array, int start, int mid, int end) {
        // 1. Determine sizes of the two halves
        int leftSize = mid - start + 1;
        int rightSize = end - mid;

        // 2. Create temporary auxiliary arrays
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        // 3. Copy data to temp arrays
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[start + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // 4. Merge the temp arrays back into the original array
        int i = 0, j = 0;
        int k = start; // Index to start writing in the original array

        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // 5. Copy remaining elements (if any)
        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}