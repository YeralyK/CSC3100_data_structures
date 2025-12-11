import java.util.*;

public class Main {
    static class MinHeap {
        int capacity;
        int curr_size;
        HeapNode[] Arr;
        int[] pos;

        public MinHeap(int capacity) {
            this.capacity = capacity;
            Arr = new HeapNode[capacity];
            pos = new int[capacity];
            curr_size = 0;
        }

        static class HeapNode {
            int vertex;
            int key;

            public HeapNode(int v, int k) {
                this.vertex = v;
                this.key = k;
            }
        }

        void swap(int i, int j) {
            HeapNode temp = Arr[i];
            Arr[i] = Arr[j];
            Arr[j] = temp;

            pos[Arr[i].vertex] = i;
            pos[Arr[j].vertex] = j;
        }

        void minHeapify(int idx) {
            int smallest = idx;
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;

            if (left < curr_size && Arr[left].key < Arr[smallest].key)
                smallest = left;

            if (right < curr_size && Arr[right].key < Arr[smallest].key)
                smallest = right;

            if (smallest != idx) {
                swap(idx, smallest);
                minHeapify(smallest);
            }
        }

        HeapNode extractMin() {
            if (curr_size == 0) return null;

            HeapNode root = Arr[0];
            HeapNode last = Arr[curr_size - 1];

            Arr[0] = last;
            pos[last.vertex] = 0;

            curr_size--;
            minHeapify(0);

            return root;
        }

        void decreaseKey(int vertex, int newKey) {
            int i = pos[vertex];
            Arr[i].key = newKey;

            while (i > 0 && Arr[i].key < Arr[(i - 1) / 2].key) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }

        boolean isInMinHeap(int vertex) {
            return pos[vertex] < curr_size;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 

        int n = sc.nextInt();
        if (n <= 1) {
            System.out.println(0);
            return;
        }

        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }

        int[] key = new int[n];
        int[] parent = new int[n];
        boolean[] inMST = new boolean[n];

        MinHeap heap = new MinHeap(n);

        for (int i = 0; i < n; i++) {
            key[i] = Integer.MAX_VALUE;
            parent[i] = -1;
            heap.Arr[i] = new MinHeap.HeapNode(i, key[i]);
            heap.pos[i] = i;
        }

        key[0] = 0;
        heap.decreaseKey(0, 0);
        heap.curr_size = n;

        int total = 0;

        while (heap.curr_size > 0) {
            MinHeap.HeapNode minNode = heap.extractMin();
            int u = minNode.vertex;
            
            inMST[u] = true; 
            total += minNode.key;

            for (int v = 0; v < n; v++) {
                if (!inMST[v]) {
                    int d = Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);

                    if (d < key[v]) {
                        key[v] = d;
                        parent[v] = u;
                        heap.decreaseKey(v, d);
                    }
                }
            }
        }

        System.out.println(total);
        sc.close();
    }
}
