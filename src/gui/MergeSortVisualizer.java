package gui;

import benchmark.InputGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ForkJoinPool;

public class MergeSortVisualizer extends JFrame {

    private final VisualArrayPanel arrayPanel;
    private int[] currentArray;

    private final JLabel timeLabel;

    public MergeSortVisualizer() {
        setTitle("Merge Sort Visualizer");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Top Controls =====
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JTextField sizeField = new JTextField("100", 7);

        String[] patterns = {"Random", "Sorted", "Reverse"};
        JComboBox<String> patternBox = new JComboBox<>(patterns);

        String[] algorithms = {"Sequential", "Parallel"};
        JComboBox<String> algorithmBox = new JComboBox<>(algorithms);

        JButton generateBtn = new JButton("Generate Array");
        JButton runBtn = new JButton("Run Sorting");

        timeLabel = new JLabel("Time: N/A");

        controlPanel.add(new JLabel("Size:"));
        controlPanel.add(sizeField);
        controlPanel.add(new JLabel("Pattern:"));
        controlPanel.add(patternBox);
        controlPanel.add(new JLabel("Algorithm:"));
        controlPanel.add(algorithmBox);
        controlPanel.add(generateBtn);
        controlPanel.add(runBtn);
        controlPanel.add(timeLabel);

        add(controlPanel, BorderLayout.NORTH);

        // ===== Array Panel =====
        arrayPanel = new VisualArrayPanel();
        add(arrayPanel, BorderLayout.CENTER);

        // ===== Button Logic =====
        generateBtn.addActionListener((ActionEvent e) -> {
            try {
                int size = Integer.parseInt(sizeField.getText());
                String pattern = (String) patternBox.getSelectedItem();
                currentArray = InputGenerator.generateArray(size, pattern);

                arrayPanel.setArray(currentArray);
                timeLabel.setText("Time: N/A");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid size.");
            }
        });

        runBtn.addActionListener((ActionEvent e) -> {
            if (currentArray == null) {
                JOptionPane.showMessageDialog(this, "Generate an array first.");
                return;
            }

            int[] arrCopy = currentArray.clone();
            String choice = (String) algorithmBox.getSelectedItem();

            new Thread(() -> {
                try {
                    long start = System.nanoTime();
                    if (choice.equals("Sequential")) {
                        animateSequentialMergeSort(arrCopy);
                    } else {
                        animateParallelMergeSort(arrCopy);
                    }
                    long end = System.nanoTime();
                    double ms = (end - start) / 1_000_000.0;
                    timeLabel.setText(String.format("%s Time: %.2f ms", choice, ms));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        setVisible(true);
    }

    // =============================
    // ANIMATION METHODS
    // =============================
    private void animateSequentialMergeSort(int[] array) throws InterruptedException {
        sequentialMergeSort(array, 0, array.length - 1);
        arrayPanel.setArray(array);
    }

    private void sequentialMergeSort(int[] arr, int start, int end) throws InterruptedException {
        if (start >= end) return;
        int mid = start + (end - start) / 2;
        sequentialMergeSort(arr, start, mid);
        sequentialMergeSort(arr, mid + 1, end);
        mergeAndAnimate(arr, start, mid, end);
    }

    private void animateParallelMergeSort(int[] array) throws InterruptedException {
        int threshold = 50; // smaller for visible animation
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ParallelMergeSortTaskAnim(array, 0, array.length - 1, threshold));
        arrayPanel.setArray(array);
    }

    private void mergeAndAnimate(int[] array, int start, int mid, int end) throws InterruptedException {
        int leftSize = mid - start + 1;
        int rightSize = end - mid;
        int[] left = new int[leftSize];
        int[] right = new int[rightSize];

        System.arraycopy(array, start, left, 0, leftSize);
        System.arraycopy(array, mid + 1, right, 0, rightSize);

        int i = 0, j = 0, k = start;

        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                array[k] = left[i++];
            } else {
                array[k] = right[j++];
            }
            k++;
            arrayPanel.setArray(array.clone());
            Thread.sleep(10); // animation speed
        }

        while (i < leftSize) {
            array[k++] = left[i++];
            arrayPanel.setArray(array.clone());
            Thread.sleep(10);
        }

        while (j < rightSize) {
            array[k++] = right[j++];
            arrayPanel.setArray(array.clone());
            Thread.sleep(10);
        }
    }

    // =============================
    // Inner class for parallel animation
    // =============================
    private class ParallelMergeSortTaskAnim extends java.util.concurrent.RecursiveAction {
        private final int[] array;
        private final int start, end, threshold;

        public ParallelMergeSortTaskAnim(int[] array, int start, int end, int threshold) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected void compute() {
            if (end - start <= threshold) {
                try {
                    mergeSortSequentialAnim(array, start, end);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            int mid = start + (end - start) / 2;
            ParallelMergeSortTaskAnim left = new ParallelMergeSortTaskAnim(array, start, mid, threshold);
            ParallelMergeSortTaskAnim right = new ParallelMergeSortTaskAnim(array, mid + 1, end, threshold);
            invokeAll(left, right);
            try {
                mergeAndAnimate(array, start, mid, end);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void mergeSortSequentialAnim(int[] arr, int start, int end) throws InterruptedException {
            if (start >= end) return;
            int mid = start + (end - start) / 2;
            mergeSortSequentialAnim(arr, start, mid);
            mergeSortSequentialAnim(arr, mid + 1, end);
            mergeAndAnimate(arr, start, mid, end);
        }
    }
}
