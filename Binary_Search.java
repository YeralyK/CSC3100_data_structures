import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        int size = sc.nextInt();
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = sc.nextInt();
        }

        int[][] number_position = new int[size][2];
        for (int i = 0; i < size; i++) {
            number_position[i][0] = numbers[i];
            number_position[i][1] = i; 
        }

        int[][] storage = new int[size][2];

        long[] lefts = new long[size];
        int[][] left_arr = New(number_position);
        leftMergeSort(left_arr, lefts, 0, size, storage);

        long[] rights = new long[size];
        int[][] right_arr = New(number_position);
        rightMergeSort(right_arr, rights, 0, size, storage);

        long triplets = 0;
        for (int i = 0; i < size; i++) {
            triplets += lefts[i] * rights[i];
        }
        System.out.println(triplets);

    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        public FastReader() { br = new BufferedReader(new InputStreamReader(System.in)); }
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try { st = new StringTokenizer(br.readLine()); } 
                catch (IOException e) { e.printStackTrace(); }
            }
            return st.nextToken();
        }
        int nextInt() { return Integer.parseInt(next()); }
        long nextLong() { return Long.parseLong(next()); }
    }

    private static int[][] New(int[][] original) {
        int[][] new_arr = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            new_arr[i] = original[i].clone();
        }
        return new_arr;
    }

    private static void leftMergeSort(int[][] array, long[] sums, int start, int end, int[][] storage) {
        if (end - start <= 1) return;
        int mid = start + (end - start) / 2;

        leftMergeSort(array, sums, start, mid, storage);
        leftMergeSort(array, sums, mid, end, storage);
        leftMerge(array, sums, start, mid, end, storage);
    }

    private static void leftMerge(int[][] array, long[] sums, int start, int mid, int end, int[][] storage) {
        int i = start, j = mid, pointer = start;
        while (i < mid && j < end) {
            if (array[i][0] < array[j][0]) {
                storage[pointer++] = array[i++];
            } 
            else {
                sums[array[j][1]] += (i - start);
                storage[pointer++] = array[j++];
            }
        }
        while (i < mid) {
            storage[pointer++] = array[i++];
        }
        while (j < end) {
            sums[array[j][1]] += (mid - start);
            storage[pointer++] = array[j++];
        }

        System.arraycopy(storage, start, array, start, end - start);
    }

    private static void rightMergeSort(int[][] array, long[] sums, int start, int end, int[][] storage) {
        if (end - start <= 1) return;
        int mid = start + (end - start) / 2;

        rightMergeSort(array, sums, start, mid, storage);
        rightMergeSort(array, sums, mid, end, storage);
        rightMerge(array, sums, start, mid, end, storage);
    }

    private static void rightMerge(int[][] array, long[] sums, int start, int mid, int end, int[][] storage) {
        int point = mid;
        for (int k = start; k < mid; k++) {
            while (point < end && array[point][0] <= array[k][0]) {
                point++;
            }
            sums[array[k][1]] += (end - point);
        }
        int i = start, j = mid, pointer = start;
        while (i < mid && j < end) {
            if (array[i][0] <= array[j][0]) {
                storage[pointer++] = array[i++];
            } else {
                storage[pointer++] = array[j++];
            }
        }
        while (i < mid) {
            storage[pointer++] = array[i++];
        }
        while (j < end) {
            storage[pointer++] = array[j++];
        }

        System.arraycopy(storage, start, array, start, end - start);
    }
}