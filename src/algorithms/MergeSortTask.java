package algorithms;

import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {

    private final int[] array;
    private final int start;
    private final int end;
    private final int threshold;

    public MergeSortTask(int[] array, int start, int end, int threshold) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    // Sequential merge sort for small segments
    private void sequentialSort(int[] arr, int start, int end) {
        if (start >= end) return;

        int mid = start + (end - start) / 2;
        sequentialSort(arr, start, mid);
        sequentialSort(arr, mid + 1, end);
        MergeHelper.merge(arr, start, mid, end);
    }

    @Override
    protected void compute() {
        if ((end - start) <= threshold) {
            sequentialSort(array, start, end);
            return;
        }

        int mid = start + (end - start) / 2;

        MergeSortTask leftTask = new MergeSortTask(array, start, mid, threshold);
        MergeSortTask rightTask = new MergeSortTask(array, mid + 1, end, threshold);

        invokeAll(leftTask, rightTask);

        MergeHelper.merge(array, start, mid, end);
    }
}




