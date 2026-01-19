package benchmark;

import java.util.Arrays;
import java.util.Random;

public class InputGenerator {

    private static final Random random = new Random();

    // Unified generator based on pattern
    public static int[] generateArray(int size, String pattern) {
        switch (pattern) {
            case "Random":
                return generateRandomArray(size);
            case "Sorted":
                return generateSortedArray(size);
            case "Reverse":
                return generateReverseSortedArray(size);
            default:
                throw new IllegalArgumentException("Unknown pattern: " + pattern);
        }
    }

    // Random Array
    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10);
        }
        return array;
    }

    // Reversed Array
    public static int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }

    // Sorted Array
    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    // Utility to copy arrays for fair comparisons
    public static int[] copyArray(int[] array) {
        return Arrays.copyOf(array, array.length);
    }
}