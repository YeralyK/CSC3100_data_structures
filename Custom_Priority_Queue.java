import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) {
        FastReader scanner = new FastReader();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        long n = scanner.nextLong();
        long m = scanner.nextLong();
        long q = scanner.nextLong();
        long u = scanner.nextLong();
        long v = scanner.nextLong();
        int t = scanner.nextInt();

        CellsHeap cells = new CellsHeap((int)(n + m));

        for (int i = 0; i < n; i++){
            long cell = scanner.nextLong();
            cells.insert(cell);
        }

        long q_after_m = 0L;

        for (int i = 0; i < m; i++) {
            long large = cells.popMax();
            long real_large = large + q_after_m;

            int m2 = i + 1;  
            if (m2 % t == 0) {
                if (m2 == t) { 
                    out.print(real_large);
                } 
                else {
                    out.print(' ');
                    out.print(real_large);
                }
            }

            long divided1 = (real_large * u) / v;
            long divided2 = real_large - divided1;

            q_after_m += q;

            if (divided1 > 0) {
                cells.insert(divided1 - q_after_m);
            }
            if (divided2 > 0) {
                cells.insert(divided2 - q_after_m);
            }
        }

        out.println();

        int count = 1;
        while (!cells.isEmpty()) {
            long size = cells.popMax();
            long real_size = size + q_after_m;
            if (count % t == 0) {
                if (count == t) {
                    out.print(real_size);
                } 
                else {
                    out.print(' ');
                    out.print(real_size);
                }
            }
            count++;
        }
        out.println();
        out.close();
    }

    static class CellsHeap {
        private long[] arr;
        private int size;

        public CellsHeap(int capacity) {
            arr = new long[capacity + 1];
            size = 0;
        }

        public void insert(long x) {
            int hole = ++size;
            while (hole > 1 && x > arr[hole / 2]) {
                arr[hole] = arr[hole / 2];
                hole /= 2;
            }
            arr[hole] = x;
        }

        public long popMax() {
            long max = arr[1];
            arr[1] = arr[size--];
            percolateDown(1);
            return max;
        }

        private void percolateDown(int hole) {
            int child;
            long tmp = arr[hole];
            while (hole * 2 <= size) {
                child = hole * 2;
                if (child != size && arr[child + 1] > arr[child])
                    child++;
                if (arr[child] > tmp)
                    arr[hole] = arr[child];
                else
                    break;
                hole = child;
            }
            arr[hole] = tmp;
        }

        public boolean isEmpty() { 
            return size == 0; 
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
