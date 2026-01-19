import benchmark.SortBenchmark;

public class Main {
    public static void main(String[] args) {

        new gui.MergeSortVisualizer().setVisible(true);

        System.out.println("Running Benchmark ...\n\n");

        System.out.println("Enter array sizes to test (comma separated): ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        String[] sizeStrings = input.split(",");
        int[] SIZES = new int[sizeStrings.length];
        for (int i = 0; i < sizeStrings.length; i++) {
            SIZES[i] = Integer.parseInt(sizeStrings[i].trim());
        }

        System.out.println("\nEnter number of runs for each test: ");
        int runs = scanner.nextInt();
        scanner.close();

        System.out.println("\nStarting benchmarks for sizes: " + java.util.Arrays.toString(SIZES) + " with " + runs
                + " runs each.\n\n");

        SortBenchmark.testProgram(SIZES, runs);

    }
}
